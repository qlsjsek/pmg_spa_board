package com.pmg.board.service;


import com.pmg.board.dto.BoardImageDto;
import com.pmg.board.entity.BoardImage;

public interface BoardImageService {
	
	public void uploadImage(Long boardId, BoardImageDto boardImageDto);
	
	public BoardImage boardImageByBoardId(Long boardId);
		
}
