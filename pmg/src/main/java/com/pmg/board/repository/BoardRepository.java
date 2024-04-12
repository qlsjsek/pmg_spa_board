package com.pmg.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmg.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
