package com.pmg.board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pmg.board.dto.BoardDto;
import com.pmg.board.dto.BoardImageDto;
import com.pmg.board.entity.Board;
import com.pmg.board.entity.BoardCategory;

public interface BoardService {
	//게시판 카테고리 조회
	public List<BoardCategory> findCategories();
	
	//게시글 작성
	public Board createBoard(BoardDto boardDto, BoardImageDto boardImageDto);
	//게시글 삭제
	public void deleteBoard(Long boardId);
	//게시글 수정
	public Board updateBoard(Long boardId, BoardDto boardDto);
	//게시글 조회(최신순)
	public List<Board> findBoardListByDesc();
	//게시글 조회(오래된순)
	public List<Board> findBoardListByAsc();
	//게시글 한개 조회
	public Board findBoardByBoardId(Long boardId);
	//카테고리별 게시글 조회(최신순)
	public List<Board> findBoardByCategoryIdByDesc(Long categoryId);
	//제목으로 게시글 검색
	public List<Board> searchByTitle(String keyword);
	//게시글 페이징
	public Page<Board> getBoardList(Pageable pageable);
	//게시글 조회수 증가
	public void increaseReadCount(Long boardId);
	//게시글 추천
	public void updateRecommendCount(Long boardId, int boardRecommend);
	
	
}
