package com.pmg.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import com.pmg.user.dto.UserDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "userentity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private String userId;
	private String userPassword;
	private String userName;
	private String userAddress;
	private String userPhone;
	private String userEmail;
	private String userBirthDate;
	
	@CreationTimestamp
	private LocalDateTime createdTime;
	
	public static User toEntity(UserDto dto) {
		return User.builder().userId(dto.getUserId())
								.userPassword(dto.getUserPassword())
								.userName(dto.getUserName())
								.userAddress(dto.getUserAddress())
								.userPhone(dto.getUserPhone())
								.userEmail(dto.getUserEmail())
								.userBirthDate(dto.getUserBirthDate())
								.build();
	}
	
}
