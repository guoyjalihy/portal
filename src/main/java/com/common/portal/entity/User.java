package com.common.portal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String username;
	@Column
	private String password;
}
