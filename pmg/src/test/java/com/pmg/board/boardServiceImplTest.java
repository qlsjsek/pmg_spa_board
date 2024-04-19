package com.pmg.board;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.pmg.PmgApplicationTests;
import com.pmg.board.dto.BoardDto;
import com.pmg.board.dto.BoardImageDto;
import com.pmg.board.entity.Board;
import com.pmg.board.entity.BoardCategory;
import com.pmg.board.entity.BoardImage;
import com.pmg.board.service.BoardImageService;
import com.pmg.board.service.BoardService;

import jakarta.transaction.Transactional;

public class boardServiceImplTest extends PmgApplicationTests {
	@Autowired
	BoardService boardService;
	@Autowired
	BoardImageService boardImageService;
	
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
		BoardImageDto image = new BoardImageDto();
		String userId = "test1";
		image.setBoardId(1L);
		image.setImageName("imageTest1");
		Board board = boardService.createBoard(dto,image,userId);
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
	
	/*
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @Disabled
	 * 
	 * @Rollback(false) void findBoardList() { List<Board> boardList =
	 * boardService.findBoardListByDesc();
	 * System.out.println("게시글 리스트 확인 -->"+boardList); }
	 */
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void findBoardByBoardId() {
		Long boardId = 1L;
		Board board = boardService.findBoardByBoardId(boardId);
		System.out.println("게시글 조회 --> " + board);
		if (board != null && board.getUser() != null) {
		    System.out.println("게시글 작성자 --> " + board.getUser().getUserId());
		} else {
		    System.out.println("게시글 작성자 정보 없음");
		}
	}
	
	@Test
	@Transactional
	@Disabled
	@Rollback(false)
	void findImage() {
		Long boardId = 18L;
		BoardImage image = boardImageService.boardImageByBoardId(boardId);
		System.out.println("이미지 확인 -->" + image);
	}
	
	@Test
	@Transactional
	//@Disabled
	@Rollback(false)
	void userId() {
		Long boardId = 5L;
		String userId =boardService.findUserIdByBoardId(boardId);
		System.out.println("유저 확인 -->" + userId);
	}
	
}
