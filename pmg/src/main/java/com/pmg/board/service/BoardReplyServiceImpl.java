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
import com.pmg.board.repository.BoardRepository;
import com.pmg.user.entity.User;
import com.pmg.user.repository.UserRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class BoardReplyServiceImpl implements BoardReplyService{
	@Autowired
	BoardReplyRepository boardReplyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BoardRepository boardRepository;
	
	@Override
	public BoardReply createReply(BoardReplyDto boardReplyDto) {
		User user = userRepository.findByUserId(boardReplyDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
		Board board = null;
		if (boardReplyDto.getBoardId() != null) {
			board = boardRepository.findById(boardReplyDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));
		}
		BoardReply reply = BoardReply.builder()
										.boardReplyId(boardReplyDto.getBoardReplyId())
										.boardReplyContent(boardReplyDto.getBoardReplyContent())
										.board(board)
										.user(user)
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

	@Override
	public String findUserIdByBoardReplyId(Long boardReplyId) {
		Optional<BoardReply> optionalReply = boardReplyRepository.findById(boardReplyId);
		if(optionalReply.isPresent()) {
			BoardReply reply = optionalReply.get();
			return reply.getUser().getUserId();
		}
		return null;
	}

}
