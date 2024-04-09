package com.pmg.user.dto;

import com.pmg.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
	private String userId;
	private String userPassword;
	private String userName;
	private String userAddress;
	private String userPhone;
	private String userEmail;
	private String userBirthDate;
	
	public static UserDto toDto(User entity) {
		return UserDto.builder()
						.userId(entity.getUserId())
						.userPassword(entity.getUserPassword())
						.userName(entity.getUserName())
						.userAddress(entity.getUserAddress())
						.userPhone(entity.getUserPhone())
						.userEmail(entity.getUserEmail())
						.userBirthDate(entity.getUserBirthDate())
						.build();
	}
	
}
