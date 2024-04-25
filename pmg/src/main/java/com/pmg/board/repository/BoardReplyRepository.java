package com.pmg.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmg.board.entity.BoardReply;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long>{
	List<BoardReply> findByBoardBoardId(Long boardId);
}
