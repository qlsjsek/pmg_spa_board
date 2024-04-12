package com.pmg.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmg.user.dto.UserDto;
import com.pmg.user.entity.User;
import com.pmg.user.service.UserService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/user")
public class UserRestController {
	@Autowired
	private UserService userService;
		
	@PostMapping("/signup")
	public ResponseEntity<String> signUpUser(@RequestBody UserDto userDto) {
		userService.createUser(userDto);
		return ResponseEntity.ok("회원가입 성공");
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto, HttpSession session){
		User loginUser = userService.loginUser(userDto.getUserId(), userDto.getUserPassword());
		if (loginUser != null) {
			session.setAttribute("loginUser", loginUser);
			UserDto dto = UserDto.toDto(loginUser);
			return ResponseEntity.ok(dto);
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long id, HttpSession session) {
		User user = userService.findUserById(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		} else {
			userService.deleteUser(id);
			session.invalidate();
			return ResponseEntity.ok("계정 탈퇴 완료!");
		}
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable("userId") String userId, @RequestBody UserDto userDto) {
		User user = userService.findUserByUserId(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		} else {
			user.setUserPassword(userDto.getUserPassword());
			user.setUserAddress(userDto.getUserAddress());
			user.setUserEmail(userDto.getUserEmail());
			user.setUserPhone(userDto.getUserPhone());
			userService.updateUser(userId, UserDto.toDto(user));
			return ResponseEntity.ok("회원 정보 업데이트 완료!");
		}
	}
	
	@GetMapping("/duplicate/{userId}")
	public ResponseEntity<Boolean> checkDuplicationUserId(@PathVariable("userId") String userId) {
		boolean duplicate = userService.isUserIdDuplicate(userId);
		return ResponseEntity.ok(duplicate);
	}
	
	@GetMapping("/confirm/{userId}/{userPassword}")
	public ResponseEntity<Boolean> isPasswordConfirm(@PathVariable("userId") String userId, @PathVariable("userPassword") String userPassword) {
		boolean isPasswordMatch = userService.isPasswordConfirmByUserId(userId, userPassword);
		return ResponseEntity.ok(isPasswordMatch);
	}
	
	@GetMapping("/find/userId")
	public ResponseEntity<String> findUserIdByUserNameAndUserPhone(@RequestParam("userName") String userName, @RequestParam("userPhone") String userPhone) {
		String userId = userService.findUserIdByUserNameAndUserPhone(userName, userPhone);
		if (userId != null && !userId.isEmpty()) {
			return ResponseEntity.ok(userId);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
		}
	}
	
	@GetMapping("/find/userPassword")
	public ResponseEntity<String> findUserPasswordByUserIdAndUserPhone(@RequestParam("userId") String userId, @RequestParam("userPhone") String userPhone) {
		String userPassword = userService.findUserPasswordByUserIdAndUserPhone(userId, userPhone);
		if (userPassword != null && !userPassword.isEmpty()) {
			return ResponseEntity.ok(userPassword);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
		}
	}
	
	
	
}
