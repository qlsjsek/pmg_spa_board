package com.pmg.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmg.board.entity.BoardImage;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long>{
	Optional<BoardImage> findByBoardBoardId(Long boardId);
}
