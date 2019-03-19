package com.common.portal.controller;

import com.common.portal.aop.LogType;
import com.common.portal.aop.OperationLog;
import com.common.portal.aop.OperationType;
import com.common.portal.controller.vo.LogConfigVO;
import com.common.portal.controller.vo.LogVO;
import com.common.portal.rpc.zabbix.ZabbixApi;
import com.common.portal.service.LogService;
import com.common.portal.util.FtpParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/log")
public class LogJSONController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LogService logService;
    @Autowired
    ZabbixApi zabbixApi;

    @RequestMapping("bak/{type}")
    @OperationLog(operationType = OperationType.BAK ,content = "日志")
    public String bak(@PathVariable String type) {
        logger.info("log bak.type:{}",type);
        try {
            if (type.equals(LogType.SECURITY.toString().toLowerCase())){
                logService.bakSecurityLog();
            }else if (type.equals(LogType.OPERATION.toString().toLowerCase())){
                logService.bakOperationLog();
            }else if (type.equals(LogType.EVENT.toString().toLowerCase())){
                logService.bakEventLog();
            }else {
                logger.error("log bak error.unknown type:{}",type);
                return "ERROR";
            }
        }catch (Exception e){
            logger.error("bak log error.type:{}",type,e);
            return "ERROR";
        }
        return "SUCCESS";
    }


    @RequestMapping("remote/{type}")
    @OperationLog(operationType = OperationType.REMOTE ,content = "日志")
    public String remote(@PathVariable String type , LogConfigVO logConfigVO) {
        logger.info("log remote.type:{}",type);
        try {
            if (type.equals(LogType.SECURITY.toString().toLowerCase())){
                logService.remoteSecurityLog(logConfigVO);
            }else if (type.equals(LogType.OPERATION.toString().toLowerCase())){
                logService.remoteOperationLog(logConfigVO);
            }else if (type.equals(LogType.EVENT.toString().toLowerCase())){
                logService.remoteEventLog(logConfigVO);
            }else {
                logger.error("log remote error.unknown type:{}",type);
                return "ERROR";
            }
        }catch (Exception e){
            logger.error("remote log error.type:{}",type,e);
            return "ERROR";
        }
        return "SUCCESS";
    }
}
