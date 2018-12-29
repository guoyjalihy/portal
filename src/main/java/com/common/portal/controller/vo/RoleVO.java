package com.common.portal.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
public class RoleVO {
	private Long id;
	private String name;
}
