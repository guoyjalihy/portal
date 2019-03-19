package com.common.portal.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FtpParam {
    //ftp服务器地址
    public String originIP;
    //ftp服务器端口号默认为21
    public Integer port = 21 ;
    //ftp登录账号
    public String username;
    //ftp登录密码
    public String password;
    public String originLogPath;
}
