package com.pmg.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmg.board.dto.BoardImageDto;
import com.pmg.board.entity.Board;
import com.pmg.board.entity.BoardImage;
import com.pmg.board.repository.BoardImageRepository;
import com.pmg.board.repository.BoardRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoardImageServiceImpl implements BoardImageService{
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	BoardImageRepository boardImageRepository;
	
	@Override
	public void uploadImage(Long boardId, BoardImageDto boardImageDto) {
		Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));
		
		BoardImage boardImage = new BoardImage();
		boardImage.setImageName(boardImageDto.getImageName());
		boardImage.setBoard(board);
		boardImageRepository.save(boardImage);
	}

}
