package com.lamichhane.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lamichhane.users.shared.UserDto;

public interface UserService extends UserDetailsService{
	
	public UserDto createUser(UserDto userDetails);
	public UserDto getUserDetailByEmail(String email);
	public UserDto getUserByUserId(String userId);
	

}
