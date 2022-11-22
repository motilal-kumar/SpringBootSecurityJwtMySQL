package com.neosoft.springboot.security.jwt.mysql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.neosoft.springboot.security.jwt.mysql.model.User;
import com.neosoft.springboot.security.jwt.mysql.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository  userRepository;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;


	//Save Method
	@Override
	public Integer saveUser(User user) {

		user.setPassword(
				pwdEncoder.encode(
				user.getPassword())
		);
		return userRepository.save(user).getId();
	}


	@Override
	public List<User> findAllUser() {

		List<User>  userList = userRepository.findAll();

		System.out.println("userList: "+userList);

		if(userList.size()>0){

			return userList;

		}else{

			return new ArrayList<User>();
		}

	}


	//getUser by Username
	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	//-------------------------------------------------------------------//

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("loadUserByUsername");
		Optional<User> opt =findByUsername(username);
		System.out.println("optopt:"+opt);
		System.out.println("optopt:"+opt.get());
		if (opt.isEmpty())
			throw new UsernameNotFoundException("User not exists!");

		// Read user from DB
		User user = opt.get();
		System.out.println("user.getPassword():"+user.getPassword());
		return new org.springframework.security.core.userdetails.User(
				username,
				user.getPassword(),
				user.getRoles().stream()
						.map(role -> new  SimpleGrantedAuthority(role))
						.collect(Collectors.toList())
				);
	}
}
