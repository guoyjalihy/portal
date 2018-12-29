package com.common.portal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@Setter
@ToString
@Where(clause = "status=1")
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String url;
	@Column
	private String image;
	@Column
	private String name;
	@Column
	private int level;
	@Column
	private Long parent;
	@Column
	private int status;
	@OneToMany
	@JoinColumn(name = "parent")
	private List<Menu> subMenus;
}
