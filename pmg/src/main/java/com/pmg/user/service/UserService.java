package com.pmg.user.service;


import com.pmg.user.dto.UserDto;
import com.pmg.user.entity.User;

public interface UserService {
	User createUser(UserDto userDto);
	void deleteUser(Long id);
	User updateUser(String userId, UserDto userDto);
	User findUser(String userId);
	User loginUser(String userId, String userPassword);
	
	
}
