package com.common.portal.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.File;

@Getter
@Setter
@ToString
public class UserVO {
	private Long id;
	private String username;
	private String password;
	private Long roleId;
	private String roleName;
	private String image;
	private String ip;
}
