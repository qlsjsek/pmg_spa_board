package com.pmg.board.dto;

import com.pmg.board.entity.BoardCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCategoryDto {
	private String categoryName;
	
	public static BoardCategoryDto toDto(BoardCategory entity) {
		return BoardCategoryDto.builder()
								.categoryName(entity.getCategoryName())
								.build();
	}
}
