package com.pmg.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmg.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	List<Board> findByBoardCategoryCategoryId(Long categoryId);
	List<Board> findByBoardTitleContaining(String keyword);
}
