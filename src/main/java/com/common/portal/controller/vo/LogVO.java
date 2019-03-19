package com.common.portal.controller.vo;

import com.common.portal.entity.BaseObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LogVO extends BaseObject{
	private Long id;
	/**
	 * 类型：1-安全日志 2-操作日志
	 */
	private int type;

	/**
	 * OperationType枚举
	 */
	private String operationType;

    /**
     * 操作内容
	 */
	private String content;

	/**
	 * 操作详情
	 */
	private String detail;

    /**
	 * 操作用户ID
	 */
	private Long userId;

	private UserVO user;

	/**
     * 创建时间
	 */
	private LocalDateTime createTime;

    /**
     * 结果 1-成功 0-失败
	 */
	private int result;
}
