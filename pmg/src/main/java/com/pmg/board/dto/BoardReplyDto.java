package com.pmg.board.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.pmg.board.entity.BoardReply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardReplyDto {
	private Long boardReplyId;
	private String boardReplyContent;
	
	@CreationTimestamp
	private LocalDateTime createdTime;
	
	private String userId;
	private Long boardId;
	
	public static BoardReplyDto toDto(BoardReply entity) {
		return BoardReplyDto.builder()
								.boardReplyContent(entity.getBoardReplyContent())
								.build();
	}

}
