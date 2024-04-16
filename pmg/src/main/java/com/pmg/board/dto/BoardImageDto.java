package com.pmg.board.dto;


import com.pmg.board.entity.BoardImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardImageDto {
	private String imageName;
	
	private Long boardId;
	
	public static BoardImageDto toDto(BoardImage entity) {
		return BoardImageDto.builder()
								.imageName(entity.getImageName())
								.boardId(entity.getBoard().getBoardId())
								.build();
	}
}
