package com.common.portal.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news")
@Getter
@Setter
@ToString
public class News extends BaseObject {
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private String category;
	@Column
	private Date addDate;
}
