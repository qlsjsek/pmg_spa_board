package com.pmg.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pmg.exception.PasswordMismatchException;
import com.pmg.exception.UserNotFoundException;
import com.pmg.user.dto.UserDto;
import com.pmg.user.entity.User;
import com.pmg.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User createUser(UserDto userDto) {
		User user = User.builder()
							.userId(userDto.getUserId())
							.userPassword(userDto.getUserPassword())
							.userName(userDto.getUserName())
							.userAddress(userDto.getUserAddress())
							.userPhone(userDto.getUserPhone())
							.userBirthDate(userDto.getUserBirthDate())
							.build();
		return user;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User updateUser(String userId, UserDto userDto) {
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if (optionalUser.isPresent()) {
			User updateUser = optionalUser.get();
			updateUser.setUserPassword(userDto.getUserPassword());
			updateUser.setUserPhone(userDto.getUserPhone());
			updateUser.setUserEmail(userDto.getUserEmail());
			return userRepository.save(updateUser);
		} else {
			throw new EntityNotFoundException("해당 아이디를 찾을 수 없습니다.");
		}
	}

	@Override
	public User findUser(String userId) {
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new UserNotFoundException(userId + " 는 존재하지 않는 아이디입니다.");
		}
	}

	@Override
	public User loginUser(String userId, String userPassword) {
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user  == null) {
				UserNotFoundException exception = new UserNotFoundException(userId + " 는 존재하지 않는 아이디입니다.");
				exception.setData(User.builder().userId(userId).userPassword(userPassword).build());
				throw exception;
			}
			String userPw = user.getUserPassword();
			if (!userPw.equals(userPassword)) {
				PasswordMismatchException exception = new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
				exception.setData(User.builder().userId(userId).userPassword(userPassword).build());
				throw exception;
			}
			return user;
		}else {
			throw new UserNotFoundException(userId + " 는 존재하지 않는 아이디입니다.");
		}
	}

}
