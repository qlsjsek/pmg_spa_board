package com.pmg.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.pmg.board.entity.Board;
import com.pmg.board.entity.BoardReply;
import com.pmg.user.dto.UserDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Board> boards;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BoardReply> boardReplies;
}
