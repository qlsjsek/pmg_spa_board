package com.pmg.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.pmg.board.dto.BoardReplyDto;
import com.pmg.board.entity.Board;
import com.pmg.board.entity.BoardReply;
import com.pmg.board.repository.BoardReplyRepository;
import com.pmg.user.entity.User;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class BoardReplyServiceImpl implements BoardReplyService{
	@Autowired
	BoardReplyRepository boardReplyRepository;
	
	@Override
	public BoardReply createReply(BoardReplyDto boardReplyDto) {
		BoardReply reply = BoardReply.builder()
										.boardReplyId(boardReplyDto.getBoardReplyId())
										.boardReplyContent(boardReplyDto.getBoardReplyContent())
										.board(Board.builder().boardId(boardReplyDto.getBoardId()).build())
										.user(User.builder().userId(boardReplyDto.getUserId()).build())
										.build();
		return boardReplyRepository.save(reply);
	}

	@Override
	public void deleteReply(Long boardReplyId) {
		boardReplyRepository.deleteById(boardReplyId);
	}

	@Override
	public BoardReply updateReply(BoardReplyDto boardReplyDto, Long boardReplyId) {
		Optional<BoardReply> optionalBoardReply = boardReplyRepository.findById(boardReplyId);
		if (optionalBoardReply.isPresent()) {
			BoardReply reply = optionalBoardReply.get();
			reply.setBoardReplyContent(boardReplyDto.getBoardReplyContent());
			return boardReplyRepository.save(reply);
		} else {
			throw new NotFoundException("해당 댓글을 찾을 수 없습니다. " + boardReplyId);
		}
	}

	@Override
	public List<BoardReply> findReplyByBoardId(Long boardId) {
		return boardReplyRepository.findByBoardBoardId(boardId);
	}

}
