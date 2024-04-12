package com.pmg.user.service;


import com.pmg.user.dto.UserDto;
import com.pmg.user.entity.User;

public interface UserService {
	//회원가입
	public User createUser(UserDto userDto);
	//회원탈퇴
	public void deleteUser(Long id);
	//회원정보수정
	public User updateUser(String userId, UserDto userDto);
	//userId값으로 회원정보 찾기
	public User findUserByUserId(String userId);
	//id값으로 회원정보 찾기
	public User findUserById(Long id);
	//로그인
	public User loginUser(String userId, String userPassword);
	//아이디 중복체크
	public boolean isUserIdDuplicate(String userId);
	//회원정보 수정 비밀번호 확인
	public boolean isPasswordConfirmByUserId(String userId, String userPassword);
	//아이디 찾기
	public String findUserIdByUserNameAndUserPhone(String userName, String userPhone);
	//비밀번호 찾기
	public String findUserPasswordByUserIdAndUserPhone(String userId, String userPhone);
	
}
