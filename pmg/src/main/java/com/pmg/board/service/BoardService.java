package com.pmg.board.service;

import java.util.List;

import com.pmg.board.dto.BoardDto;
import com.pmg.board.entity.Board;
import com.pmg.board.entity.BoardCategory;

public interface BoardService {
	//게시판 카테고리 조회
	public List<BoardCategory> findCategories();
	
	//게시글 작성
	public Board createBoard(BoardDto boardDto);
	//게시글 삭제
	public void deleteBoard(Long boardId);
	//게시글 수정
	public Board updateBoard(Long boardId, BoardDto boardDto);
	//게시글 조회
	public List<Board> findBoardList();
	//게시글 한개 조회
	public Board findBoardByBoardId(Long boardId);
}
