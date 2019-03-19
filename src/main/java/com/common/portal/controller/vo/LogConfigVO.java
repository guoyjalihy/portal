package com.common.portal.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LogConfigVO{
	/**
	 * 保留时间，默认 7天
	 */
	private String retentionTime = "7";

	/**
	 * 允许日志占用的空间容量，默认1024M
	 * 单位：MByte
	 */
	private String  spaceCapacity = "1024";
	/**
	 * 备份日志本地保存路径,默认“/omc/log/bak/”
	 */
	private String logPath = "/omc/log/bak/";
	/**
	 * 远程日志服务器IP,默认localhost
	 */
	private String originIP = "localhost";
	/**
	 * 远程服务器登录用户名，默认root
	 */
	private String originLoginName = "root";
	/**
	 * 远程服务器登录密码
	 */
	private String originPassword;
	/**
	 * 远程服务器日志备份地址，默认“/omc/log/bak/”
	 */
	private String originLogPath = "/omc/log/bak/";
}
