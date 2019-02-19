package com.common.portal.rpc.zabbix.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HostVO {
    @JSONField(name="hostid")
    String hostId;
    String host;
    String interfaces;
    Integer status;
}
