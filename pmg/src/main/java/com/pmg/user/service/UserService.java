package com.pmg.user.service;


import com.pmg.user.dto.UserDto;
import com.pmg.user.entity.User;

public interface UserService {
	public User createUser(UserDto userDto);
	public void deleteUser(Long id);
	public User updateUser(String userId, UserDto userDto);
	public User findUserByUserId(String userId);
	public User findUserById(Long id);
	public User loginUser(String userId, String userPassword);
	public boolean isUserIdDuplicate(String userId);
	
	
}
