package com.pmg.board.service;

import java.util.List;

import com.pmg.board.dto.BoardReplyDto;
import com.pmg.board.entity.BoardReply;

public interface BoardReplyService {
	// 게시글 댓글 작성
	public BoardReply createReply(BoardReplyDto boardReplyDto);

	// 게시글 댓글 삭제
	public void deleteReply(Long boardReplyId);

	// 게시글 댓글 수정
	public BoardReply updateReply(BoardReplyDto boardReplyDto, Long boardReplyId);

	// 게시글 댓글 리스트
	List<BoardReply> findReplyByBoardId(Long boardId);

	// 게시글 댓글 작성한 userId 찾기
	public String findUserIdByBoardReplyId(Long boardReplyId);
}
