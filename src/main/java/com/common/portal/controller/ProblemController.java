package com.common.portal.controller;

import com.common.portal.rpc.zabbix.ZabbixApi;
import com.common.portal.rpc.zabbix.vo.EventVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/event")
public class ProblemController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ZabbixApi zabbixApi;

    @Value("${zabbix.ip}")
    String zabbixIp;

    @RequestMapping("/list")
    public String list(Model model){
        List<EventVO> events = zabbixApi.problemGetAll();
        model.addAttribute("events",events);
        model.addAttribute("zabbixIp",zabbixIp);
        model.addAttribute("hosts",zabbixApi.hostGetAll());
        return "event/eventList";
    }

    @RequestMapping("/search")
    public String search(Model model,EventVO eventVO){
        List<EventVO> events = null;
        try {
            events = zabbixApi.problemGetByQuery(eventVO);
        } catch (Exception e) {
            logger.error("EventController search error.",e);
        }
        model.addAttribute("events",events);
        model.addAttribute("zabbixIp",zabbixIp);
        model.addAttribute("hosts",zabbixApi.hostGetAll());
        model.addAttribute("eventVO",eventVO);
        return "event/eventList";
    }

    @RequestMapping("/del")
    public String del(){
        return "redirect:/event/eventList";
    }
}
