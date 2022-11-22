package com.neosoft.springboot.security.jwt.mysql.service;

import com.neosoft.springboot.security.jwt.mysql.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
	
	Integer saveUser(User user);
	Optional<User> findByUsername(String username);

	public List<User> findAllUser();

}
