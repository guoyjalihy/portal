package com.common.portal.service;

import com.alibaba.fastjson.JSON;
import com.common.portal.aop.LogType;
import com.common.portal.controller.vo.LogConfigVO;
import com.common.portal.controller.vo.LogVO;
import com.common.portal.controller.vo.UserVO;
import com.common.portal.dao.LogRepository;
import com.common.portal.dao.UserRepository;
import com.common.portal.entity.Log;
import com.common.portal.entity.User;
import com.common.portal.rpc.zabbix.ZabbixApi;
import com.common.portal.rpc.zabbix.vo.EventVO;
import com.common.portal.util.FtpParam;
import com.common.portal.util.FtpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	LogRepository logRepository;
	@Autowired
	UserService userService;
	@Autowired
	ZabbixApi zabbixApi;
	@Autowired
	FtpUtils ftpUtils;

	private LogConfigVO logConfigVO = new LogConfigVO();

	public List<LogVO> list(int type, Pageable pageable ) {
		List<Log> logs = logRepository.findByType(type,pageable);
		List<LogVO> result = new ArrayList<>();
		if (CollectionUtils.isEmpty(logs)){
			return result;
		}

		logs.forEach(log -> {
			result.add(buildVO(log));
		});
		return result;
	}

	private LogVO buildVO(Log log) {
		LogVO result = new LogVO();
		result.setContent(log.getContent());
		result.setCreateTime(log.getCreateTime());
		result.setId(log.getId());
		result.setResult(log.getResult());
		result.setType(log.getType());
		result.setDetail(log.getDetail());
		result.setOperationType(log.getOperationType());
		result.setUserId(log.getUserId());
		UserVO user = userService.findById(log.getUserId());
		result.setUser(user);
		return result;
	}

	public int countByType(int type) {
		return logRepository.countByType(type);
	}

	public void save(Log log) {
		logRepository.save(log);
	}

	public LogConfigVO getLogConfigVO() {
		if (logConfigVO == null){
			return new LogConfigVO();
		}
		return logConfigVO;
	}

	public void setLogConfigVO(LogConfigVO logConfigVO) {
		this.logConfigVO = logConfigVO;
	}

	public String bakSecurityLog() throws IOException {
		int rows = countByType(LogType.SECURITY.getType());
		PageRequest pageRequest = PageRequest.of(0, rows);
		List<LogVO> logs = list(LogType.SECURITY.getType(), pageRequest);
		if (CollectionUtils.isEmpty(logs)){
			return null;
		}
		String content = JSON.toJSONString(logs);
		return writeLogToFile(LogType.SECURITY.toString(),content);
	}

	public String bakOperationLog() throws IOException {
		int rows = countByType(LogType.OPERATION.getType());
		PageRequest pageRequest = PageRequest.of(0, rows);
		List<LogVO> logs = list(LogType.OPERATION.getType(), pageRequest);
		if (CollectionUtils.isEmpty(logs)){
			return null;
		}
		String content = JSON.toJSONString(logs);
		return writeLogToFile(LogType.OPERATION.toString(),content);
	}

	public String bakEventLog() throws IOException {
		List<EventVO> eventVOS = zabbixApi.eventGetAll();
		if (CollectionUtils.isEmpty(eventVOS)){
			return null;
		}
		String content = JSON.toJSONString(eventVOS);
		return writeLogToFile(LogType.EVENT.toString(),content);
	}

	private String writeLogToFile(String type,String content) throws IOException {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String time = df.format(LocalDateTime.now());
		String fileDirectory = logConfigVO.getLogPath() + type.toLowerCase() + "/";
		String fileName = type.toLowerCase() + time + ".log";
		File file = new File(fileDirectory);
		if (!file.exists()){
			file.mkdirs();
		}
		Writer writer = new FileWriter(fileDirectory + fileName,true);
		BufferedWriter bw = new BufferedWriter(writer);
		bw.write(content);
		bw.close();
		writer.close();
		return fileName;
	}

	public void remoteSecurityLog(LogConfigVO logConfigVO) throws IOException {
		FtpParam ftpParam = buildFtpParam(logConfigVO);
		String originPath = logConfigVO.getLogPath() + LogType.SECURITY.toString().toLowerCase() + "/";
		String originFileName = bakSecurityLog();
		String localBakPath = logConfigVO.getLogPath() + LogType.SECURITY.toString().toLowerCase() + "/";
		String localWholePath = localBakPath + originFileName;
		if (ftpUtils.initFtpClient(ftpParam)){
			ftpUtils.uploadFile(originPath,originFileName,localWholePath);
		}
	}

	private FtpParam buildFtpParam(LogConfigVO logConfigVO) {
		FtpParam ftpParam = new FtpParam();
		ftpParam.setOriginIP(logConfigVO.getOriginIP());
		ftpParam.setOriginLogPath(logConfigVO.getOriginLogPath());
		ftpParam.setUsername(logConfigVO.getOriginLoginName());
		ftpParam.setPassword(logConfigVO.getOriginPassword());
		return ftpParam;
	}

	public void remoteOperationLog(LogConfigVO logConfigVO) throws IOException {
		FtpParam ftpParam = buildFtpParam(logConfigVO);
		String originPath = logConfigVO.getLogPath() + LogType.OPERATION.toString().toLowerCase() + "/";
		String originFileName = bakOperationLog();
		String localBakPath = logConfigVO.getLogPath() + LogType.OPERATION.toString().toLowerCase() + "/";
		String localWholePath = localBakPath + originFileName;
		if (ftpUtils.initFtpClient(ftpParam)){
			ftpUtils.uploadFile(originPath,originFileName,localWholePath);
		}
	}

	public void remoteEventLog(LogConfigVO logConfigVO) throws IOException {
		FtpParam ftpParam = buildFtpParam(logConfigVO);
		String originPath = logConfigVO.getOriginLogPath() + LogType.EVENT.toString().toLowerCase() + "/";
		String originFileName = bakSecurityLog();
		String localBakPath = logConfigVO.getLogPath() + LogType.EVENT.toString().toLowerCase() + "/";
		String localWholePath = localBakPath + originFileName;
		if (ftpUtils.initFtpClient(ftpParam)){
			ftpUtils.uploadFile(originPath,originFileName,localWholePath);
		}
	}

	public void clearDB() {
		logRepository.deleteAll();
	}
}
