package com.common.portal.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
public class MenusVO {
	private Long id;
	private String url;
	private String image;
	private String name;
	private int level;
	private Long parent;
	private List<MenusVO> subMenus;
}
