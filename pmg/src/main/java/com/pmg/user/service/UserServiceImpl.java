package com.pmg.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmg.exception.PasswordMismatchException;
import com.pmg.exception.UserNotFoundException;
import com.pmg.user.dto.UserDto;
import com.pmg.user.entity.User;
import com.pmg.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public User createUser(UserDto userDto) {
		Optional<User> optionalUser = userRepository.findByUserId(userDto.getUserId());
		if (optionalUser.isPresent()) {
			throw new RuntimeException("이미 존재하는 ID 입니다.");
		} else {
			String encodePw = passwordEncoder.encode(userDto.getUserPassword());
			User user = User.builder().userId(userDto.getUserId()).userPassword(encodePw)
					.userName(userDto.getUserName()).userAddress(userDto.getUserAddress())
					.userPhone(userDto.getUserPhone()).userBirthDate(userDto.getUserBirthDate())
					.userEmail(userDto.getUserEmail()).build();
			return userRepository.save(user);
		}
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
	        String newPassword = userDto.getUserPassword();
	        if (newPassword != null && !newPassword.isEmpty()) {
	            updateUser.setUserPassword(passwordEncoder.encode(newPassword));
	        }
	        String newAddress = userDto.getUserAddress();
	        if(newAddress != null && !newAddress.isEmpty()) {
	        	updateUser.setUserAddress(newAddress);
	        }
	        String newPhone = userDto.getUserPhone();
	        if (newPhone != null && !newPhone.isEmpty()) {
	            updateUser.setUserPhone(newPhone);
	        }
	        String newEmail = userDto.getUserEmail();
	        if (newEmail != null && !newEmail.isEmpty()) {
	            updateUser.setUserEmail(newEmail);
	        }
	        return userRepository.save(updateUser);
	    } else {
	        throw new EntityNotFoundException("해당 아이디를 찾을 수 없습니다.");
	    }
	}

	@Override
	public User findUserByUserId(String userId) {
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new UserNotFoundException(userId + " 는 존재하지 않는 아이디입니다.");
		}
	}

	@Override
	public User findUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new UserNotFoundException(id + " 는 존재하지않습니다.");
		}
	}

	@Override
	public User loginUser(String userId, String userPassword) {
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user == null) {
				throw new UserNotFoundException(userId + " 는 존재하지 않는 아이디입니다.");
			}
			String encryPtedPassword = user.getUserPassword();
			boolean passwordMatches = passwordEncoder.matches(userPassword, encryPtedPassword);

			if(!passwordMatches) {
				throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
			}
			return user;
		} else {
			throw new UserNotFoundException(userId + " 는 존재하지 않는 아이디입니다.");
		}
	}

	@Override
	public boolean isUserIdDuplicate(String userId) {
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		return optionalUser.isPresent();
	}

	@Override
	public boolean isPasswordConfirmByUserId(String userId, String userPassword) {
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			String encodePw = user.getUserPassword();
			boolean passwordMatches = passwordEncoder.matches(userPassword, encodePw);
			return passwordMatches;
		} else {
			throw new UserNotFoundException(userId + " 는 존재하지 않는 아이디입니다.");
		}
	}

	@Override
	public String findUserIdByUserNameAndUserPhone(String userName, String userPhone) {
		Optional<User> optionalUser = userRepository.findUserIdByUserNameAndUserPhone(userName, userPhone);
		if (optionalUser.isPresent()) {
			String userId = optionalUser.get().getUserId();
			return userId;
		} else {
			throw new RuntimeException("사용자를 찾을 수 없습니다.");
		}
	}

	@Override
	public String findUserPasswordByUserIdAndUserPhone(String userId, String userPhone) {
		Optional<User> optionalUser = userRepository.findUserPasswordByUserIdAndUserPhone(userId, userPhone);
		if (optionalUser.isPresent()) {
			String userPassword = optionalUser.get().getUserPassword();
			return userPassword;
		} else {
			throw new RuntimeException("사용자를 찾을 수 없습니다.");
		}
	}

	@Override
	public User resetUserPasswordByUserIdAndUserPhone(String userId, String userPhone, String newPassword) {
	    Optional<User> optionalUser = userRepository.findByUserIdAndUserPhone(userId, userPhone);
	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();
	        user.setUserPassword(passwordEncoder.encode(newPassword));
	        return userRepository.save(user);
	    } else {
	        throw new UserNotFoundException(userId + "는 존재하지 않는 사용자입니다");
	    }
	}

	@Override
	public void softDeleteUser(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setDeleted(true);
			userRepository.save(user);
		} else {
			throw new RuntimeException("사용자를 찾을 수 없습니다 : "+id);
		}
	}

	@Override
	public boolean isConfirmByUserIdAndUserPhone(String userId, String userPhone) {
		Optional<User> optionalUser = userRepository.findByUserIdAndUserPhone(userId, userPhone);
		return optionalUser.isPresent();
	}

}
