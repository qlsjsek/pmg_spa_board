package com.pmg.board.dto;

import com.pmg.board.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
	private String boardTitle;
	private String boardContent;
	private int boardReadCount;
	private int boardRecommend;
	
	private Long categoryId;
	
	public static BoardDto toDto(Board entity) {
		return BoardDto.builder()
						.boardTitle(entity.getBoardTitle())
						.boardContent(entity.getBoardContent())
						.boardReadCount(entity.getBoardReadCount())
						.boardRecommend(entity.getBoardRecommend())
						.categoryId(entity.getBoardCategory().getCategoryId())
						.build();
	}
}
