package com.common.portal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@Getter
@Setter
@ToString
public class Log{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 类型：1-安全日志 2-操作日志
	 */
	@Column
	private int type;

	@Column
	private String operationType;
	/**
	 * 操作内容
	 */
	@Column
	private String content;

	/**
	 * 操作详情
	 */
	@Column
	private String detail;

	/**
	 * 操作用户ID
	 */
	@Column
	private Long userId;

	/**
	 * 创建时间
	 */
	@Column
	private LocalDateTime createTime;

	/**
	 * 结果 1-成功 0-失败
	 */
	@Column
	private int result;
}
