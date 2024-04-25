package com.pmg.board.dto;

import java.time.LocalDateTime;


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
	private LocalDateTime createdTime;
	private String userId;
	private Long boardId;
	
	public static BoardReplyDto toDto(BoardReply entity) {
		return BoardReplyDto.builder()
								.boardReplyId(entity.getBoardReplyId())
								.boardReplyContent(entity.getBoardReplyContent())
								.createdTime(entity.getCreatedTime())
								.userId(entity.getUser().getUserId())
								.boardId(entity.getBoard().getBoardId())
								.build();
	}

}
