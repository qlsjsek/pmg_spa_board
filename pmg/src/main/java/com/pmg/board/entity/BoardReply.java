package com.pmg.board.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pmg.board.dto.BoardReplyDto;
import com.pmg.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardReply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardReplyId;
	@Column(columnDefinition = "CLOB")
	private String boardReplyContent;
	
	@CreationTimestamp
	private LocalDateTime createdTime;
	
	public static BoardReply toEntity(BoardReplyDto dto) {
		return BoardReply.builder()
							.boardReplyContent(dto.getBoardReplyContent())
							.build();
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	@JsonIgnore
	@ToString.Exclude
	private Board board;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	@ToString.Exclude
	private User user;
	
}
