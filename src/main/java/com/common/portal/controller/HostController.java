package com.common.portal.controller;

import com.common.portal.rpc.zabbix.ZabbixApi;
import com.common.portal.rpc.zabbix.vo.HostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/host")
public class HostController {
    @Autowired
    ZabbixApi zabbixApi;

    @Value("${zabbix.ip}")
    String zabbixIp;
    @Value("${host.loginname}")
    public String loginName;
    @Value("${host.passwd}")
    public String passwd;

    @RequestMapping("/list")
    public String list(Model model){
        List<HostVO> hosts = zabbixApi.hostGetAll();
        model.addAttribute("hosts",hosts);
        model.addAttribute("zabbixIp",zabbixIp);
        model.addAttribute("loginName",loginName);
        model.addAttribute("passwd",passwd);
        return "host/hostList";
    }

    @RequestMapping("/search")
    public String search(Model model){
        return list(model);
    }

    @RequestMapping("/del")
    public String del(){
        return "redirect:/host/list";
    }
}
