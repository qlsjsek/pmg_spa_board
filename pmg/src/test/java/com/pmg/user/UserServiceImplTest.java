package com.pmg.user;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.pmg.PmgApplicationTests;
import com.pmg.user.dto.UserDto;
import com.pmg.user.entity.User;
import com.pmg.user.service.UserService;

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
	//@Disabled
	@Rollback(false)
	void updateUser() {
		UserDto user = new UserDto();
		user.setUserPassword("6666");
		user.setUserAddress("서울시 관악구");
		user.setUserPhone("010-1234-1234");
		user.setUserEmail("test9@naver.com");
		User updateUser = userService.updateUser("zx", user);
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
	@Disabled
	@Rollback(false)
	void loginUser() {
		User user = userService.loginUser("test2", "6666");
		System.out.println("로그인 확인 --> " + user);
		
	}
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void findUserId() {
		String userName = "123";
		String userPhone = "123";
		String userId  = userService.findUserIdByUserNameAndUserPhone(userName, userPhone);
		System.out.println("찾은 아이디 : " + userId);
	}
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void findUserPassword() {
		String userId = "1111";
		String userPhone = "123";
		String userPassword  = userService.findUserPasswordByUserIdAndUserPhone(userId, userPhone);
		System.out.println("찾은 비밀번호 : " + userPassword);
	}
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void resetUserPassword() {
		String userId = "11";
		String userPhone = "11";
		String newPassword = "44";
		userService.resetUserPasswordByUserIdAndUserPhone(userId, userPhone, newPassword);
	}
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void passwordConfirm() {
		String userId = "zx";
		String userPassword = "zx";
		boolean password = userService.isPasswordConfirmByUserId(userId, userPassword);
		System.out.println("일치여부 확인 : " + password);
	}
	
}	
