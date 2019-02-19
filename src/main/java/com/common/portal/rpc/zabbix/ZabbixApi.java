package com.common.portal.rpc.zabbix;

import com.alibaba.fastjson.JSONObject;
import com.common.portal.rpc.zabbix.vo.EventVO;
import com.common.portal.rpc.zabbix.vo.HostVO;

import java.text.ParseException;
import java.util.List;

public interface ZabbixApi {

    List<HostVO> hostGetAll();

    List<EventVO> eventGetAll();

    JSONObject call(Request request);

    void destroy();

    List<EventVO> eventGetByQuery(EventVO eventVO) throws ParseException;
}
