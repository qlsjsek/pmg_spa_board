package com.pmg.board;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.pmg.PmgApplicationTests;
import com.pmg.board.dto.BoardDto;
import com.pmg.board.entity.Board;
import com.pmg.board.entity.BoardCategory;
import com.pmg.board.service.BoardService;

import jakarta.transaction.Transactional;

public class boardServiceImplTest extends PmgApplicationTests {
	@Autowired
	BoardService boardService;
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void categories() {
		List<BoardCategory> categories = boardService.findCategories();
		System.out.println("카테고리 확인 --> " + categories);
	}
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void createBoard() {
		BoardDto dto = BoardDto.builder().categoryId(1L).boardTitle("test1").boardContent("test1Content").build();
		Board board = boardService.createBoard(dto);
		System.out.println("게시글 작성 확인 --> :" + board);
	}
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void deleteBoard() {
		boardService.deleteBoard(1L);
	}
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void updateBoard() {
		Long boardId = 2L;
		BoardDto dto = BoardDto.builder().boardTitle("updataBoardTitle").boardContent("updateBoardContent").build();
		Board board = boardService.updateBoard(boardId, dto);
		System.out.println("게시글 수정 확인 -->" + board);
	}
	
	@Test
	@Transactional
	//@Disabled
	@Rollback(false)
	void findBoardList() {
		List<Board> boardList = boardService.findBoardList();
		System.out.println("게시글 리스트 확인 -->"+boardList);
	}
	
}
