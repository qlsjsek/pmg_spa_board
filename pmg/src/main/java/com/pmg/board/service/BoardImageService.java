package com.pmg.board.service;


import com.pmg.board.dto.BoardImageDto;

public interface BoardImageService {
	
	public void uploadImage(Long boardId, BoardImageDto boardImageDto);
		
}
