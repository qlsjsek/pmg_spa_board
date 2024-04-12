package com.pmg.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmg.board.entity.BoardCategory;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long>{

}
