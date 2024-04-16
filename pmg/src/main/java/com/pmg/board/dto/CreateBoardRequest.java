package com.pmg.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardRequest {
	private BoardDto boardDto;
	private BoardImageDto boardImageDto;
}
