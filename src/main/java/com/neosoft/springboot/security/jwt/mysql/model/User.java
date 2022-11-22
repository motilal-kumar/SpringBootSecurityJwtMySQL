package com.neosoft.springboot.security.jwt.mysql.model;

import java.util.Set;

import javax.persistence.*;

import lombok.Data;


@Data
@Entity
@Table(name = "usertab")
public class User {
	
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String username;
	private String password;
	private String email;
	 
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "rolestab",
			joinColumns = @JoinColumn(name = "id")
			)
	@Column(name="role")
	 private Set<String> roles;


}
