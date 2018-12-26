package com.common.portal.controller.vo;


import com.common.portal.entity.BaseObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@ToString
public class NewsVO extends BaseObject {

	private Long id;
	private String title;
	private String content;
	private String description;
	private String category;
	private Date addDate;
	private String image;
}
