package com.common.portal.controller;

import com.common.portal.aop.LogType;
import com.common.portal.aop.OperationLog;
import com.common.portal.aop.OperationType;
import com.common.portal.controller.vo.LogConfigVO;
import com.common.portal.controller.vo.LogVO;
import com.common.portal.rpc.zabbix.ZabbixApi;
import com.common.portal.rpc.zabbix.vo.EventVO;
import com.common.portal.service.LogService;
import com.common.portal.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/log")
public class LogController {
    @Autowired
    LogService logService;
    @Autowired
    ZabbixApi zabbixApi;

    @RequestMapping("security/list_{pageCurrent}_{pageSize}_{pageCount}")
    public String securityList(LogVO logVO, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount, Model model) {

        //判断
        if (pageSize == 0) pageSize = 10;
        if (pageCurrent == 0) pageCurrent = 1;
        int rows = logService.countByType(LogType.SECURITY.getType());
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;

        //查询

        PageRequest pageRequest = PageRequest.of((pageCurrent - 1) * pageSize, pageSize);
        List<LogVO> logs = logService.list(LogType.SECURITY.getType(), pageRequest);


        model.addAttribute("logs", logs);
        String pageHTML = PageUtil.getPageContent("/log/security/list_{pageCurrent}_{pageSize}_{pageCount}?content=" + logVO.getContent(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("logVO", logVO);
        model.addAttribute("logConfig", logService.getLogConfigVO());

        return "log/securityList";
    }


    @RequestMapping("operation/list_{pageCurrent}_{pageSize}_{pageCount}")
    public String operationList(LogVO logVO, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount, Model model) {

        //判断
        if (pageSize == 0) pageSize = 10;
        if (pageCurrent == 0) pageCurrent = 1;
        int rows = logService.countByType(LogType.OPERATION.getType());
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;

        //查询
        PageRequest pageRequest = PageRequest.of((pageCurrent - 1) * pageSize, pageSize);
        List<LogVO> logs = logService.list(LogType.OPERATION.getType(), pageRequest);


        model.addAttribute("logs", logs);
        String pageHTML = PageUtil.getPageContent("/log/operation/list_{pageCurrent}_{pageSize}_{pageCount}?content=" + logVO.getContent(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("logVO", logVO);
        model.addAttribute("logConfig", logService.getLogConfigVO());

        return "log/operationList";
    }

    @RequestMapping("event/search_{pageCurrent}_{pageSize}_{pageCount}")
    public String eventSearch(EventVO eventVO, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount, Model model) {
        List<EventVO> eventVOS = zabbixApi.eventGetByQuery(eventVO);
        model.addAttribute("events", eventVOS);
        model.addAttribute("hosts", zabbixApi.hostGetAll());
        model.addAttribute("eventVO",eventVO);
        model.addAttribute("logConfig", logService.getLogConfigVO());
        return "log/eventList";
    }

    @RequestMapping("event/list_{pageCurrent}_{pageSize}_{pageCount}")
    public String eventList(EventVO eventVO, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount, Model model) {
        List<EventVO> eventVOS = zabbixApi.eventGetByQuery(eventVO);
        model.addAttribute("events", eventVOS);
        model.addAttribute("hosts", zabbixApi.hostGetAll());
        model.addAttribute("eventVO",eventVO);
        model.addAttribute("logConfig", logService.getLogConfigVO());
        return "log/eventList";
    }

    @RequestMapping("config/view")
    public String config(Model model) {
        model.addAttribute("logConfigVO", logService.getLogConfigVO());
        return "log/logConfig";
    }

    @RequestMapping("config/modify")
    @OperationLog(operationType = OperationType.UPDATE ,content = "日志设置")
    public String configModify(LogConfigVO logConfigVO, Model model) {
        logService.setLogConfigVO(logConfigVO);
        model.addAttribute("logConfigVO", logService.getLogConfigVO());
        return "redirect:/log/config/view";
    }

}
