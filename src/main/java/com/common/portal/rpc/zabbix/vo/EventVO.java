package com.common.portal.rpc.zabbix.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@ToString
public class EventVO {
    /**
     * 严重性 1-信息 2-警告 3-一般严重 4-严重 5-灾难
     */
    String severity;
    /**
     * 事件的ID
     */
    @JSONField(name = "eventid")
    String eventId;
    /**
     *  生成OK事件的问题事件ID
     */
    @JSONField(name = "c_eventid")
    String ceventId ;
    /**
     * 恢复事件ID
     */
    @JSONField(name = "r_eventid")
    String reventId ;
    /**
     * 事件是否被确认
     */
    String acknowledged;
    /**
     * 事件创建时间
     */
    Long clock;

    String formatClock;
    /**
     * 事件创建时的纳秒
     */
    Integer ns;
    /**
     * 事件的类型
     * 0 - event created by a trigger; 由触发器创建的事件
     * 1 - event created by a discovery rule;由发现规则创建的事件;
     * 2 - event created by active agent auto-registration;活动代理自动注册创建的事件;
     * 3 - internal event.内部事件
     */
    Integer source;
    /**
     * 事件被手动关闭时的用户ID
     */
    String userId;
    /**
     * 事件内容
     */
    String name;

    /**
     * 主机名
     */
    String host;

    String hostId;
    /**
     * 与主机表host的关联关系
     * 0 - trigger.触发器 表关联：events.objectid=functions.triggerid functions.itemid=items.itemid items.hostid=hosts.hostid
     * 1 - discovered host;发现主机
     * 2 - discovered service. 发现服务
     * 3 - auto-registered host. 自动注册的主机
     * 4 - item; 项 表关联：events.objectid=items.itemid items.hostid=hosts.hostid
     * 5 - LLD rule.LLD规则。
     */
    Integer object;

    Long objectId;

    String recent;

    /**
     * 事件创建开始时间
     */
    String timeFrom;
    /**
     * 事件创建结束时间
     */
    String timeTill;

    public String getFormatClock(){
        Instant instant = Instant.ofEpochMilli(clock*1000);
        return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai")).toString();
    }
}
