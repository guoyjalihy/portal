package com.common.portal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "privilege")
@Getter
@Setter
@ToString
public class Privilege {
	@Id
	@GeneratedValue
	private Long id;
	private Long roleId;
	private Long menuId;
}
