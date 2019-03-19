package com.common.portal.rpc;

import com.common.portal.rpc.zabbix.ZabbixApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RPCController {

    @Autowired
    ZabbixApi zabbixApi;

    @RequestMapping("/rpc")
    public String rpc(){
        System.out.printf(zabbixApi.problemGetAll().toString());
        return "success";
    }
}
