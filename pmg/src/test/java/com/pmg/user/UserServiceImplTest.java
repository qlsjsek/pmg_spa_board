package com.pmg.user;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.pmg.PmgApplicationTests;
import com.pmg.user.dto.UserDto;
import com.pmg.user.entity.User;
import com.pmg.user.service.UserService;

import jakarta.transaction.Transactional;

public class UserServiceImplTest extends PmgApplicationTests {
	@Autowired
	UserService userService;
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void createUser() {
		UserDto userDto = new UserDto();
		userDto.setUserId("test1");
		userDto.setUserPassword("test1");
		userDto.setUserName("테스트");
		userDto.setUserAddress("경기도 화성시");
		userDto.setUserEmail("test1@naver.com");
		userDto.setUserPhone("010-1111-1234");
		userDto.setUserBirthDate("960101");
		User user = userService.createUser(userDto);
		System.out.println("회원가입 확인 --> " + user);
	}
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void deleteUser() {
		userService.deleteUser(1L);
	}
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void updateUser() {
		UserDto user = new UserDto();
		user.setUserPassword("6666");
		user.setUserAddress("서울시 관악구");
		user.setUserPhone("010-1234-1234");
		user.setUserEmail("test9@naver.com");
		User updateUser = userService.updateUser("test1", user);
		System.out.println("업데이트 확인 --> "+ updateUser);
	}
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void findUser() {
		User user = userService.findUserByUserId("test1");
		System.out.println("유저 정보 조회 --> " + user);
	}
	
	@Test
	@Transactional
	//@Disabled
	@Rollback(false)
	void loginUser() {
		User user = userService.loginUser("test2", "6666");
		System.out.println("로그인 확인 --> " + user);
		
	}
	
}
