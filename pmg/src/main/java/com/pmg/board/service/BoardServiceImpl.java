package com.pmg.board.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import com.pmg.board.dto.BoardDto;
import com.pmg.board.dto.BoardImageDto;
import com.pmg.board.entity.Board;
import com.pmg.board.entity.BoardCategory;
import com.pmg.board.repository.BoardCategoryRepository;
import com.pmg.board.repository.BoardRepository;
import com.pmg.user.entity.User;
import com.pmg.user.repository.UserRepository;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardCategoryRepository boardCategoryRepository;
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	BoardImageService boardImageService;
	@Autowired
	UserRepository userRepository;

	@Override
	public List<BoardCategory> findCategories() {
		return boardCategoryRepository.findAll();
	}

	@Override
	public Board createBoard(BoardDto boardDto, BoardImageDto boardImageDto, String userId) {
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if (optionalUser.isEmpty()) {
			throw new RuntimeException("사용자를 찾을 수 없습니다:" + userId);
		}
		Board board = Board.builder()
				.boardCategory(BoardCategory.builder().categoryId(boardDto.getCategoryId()).build())
				.boardTitle(boardDto.getBoardTitle()).boardContent(boardDto.getBoardContent()).user(optionalUser.get())
				.build();
		board = boardRepository.save(board);

		if (boardImageDto != null) {
			boardImageService.uploadImage(board.getBoardId(), boardImageDto);
		}

		return board;
	}

	@Override
	public void deleteBoard(Long boardId) {
		boardRepository.deleteById(boardId);
	}

	@Override
	public Board updateBoard(Long boardId, BoardDto boardDto) {
		Optional<Board> optionalBoard = boardRepository.findById(boardId);
		if (optionalBoard.isPresent()) {
			Board updateBoard = optionalBoard.get();
			updateBoard.setBoardTitle(boardDto.getBoardTitle());
			updateBoard.setBoardContent(boardDto.getBoardContent());
			return boardRepository.save(updateBoard);
		} else {
			throw new NotFoundException("해당 ID에 해당하는 게시글을 찾을 수 없습니다: " + boardId);
		}
	}

	@Override
	public List<Board> findBoardListByDesc() {
		return boardRepository.findAllByOrderByCreatedTimeDesc();
	}

	@Override
	public List<Board> findBoardListByAsc() {
		return boardRepository.findAllByOrderByCreatedTimeAsc();
	}

	@Override
	public Board findBoardByBoardId(Long boardId) {
		Optional<Board> optionalBoard = boardRepository.findById(boardId);
		if (optionalBoard.isPresent()) {
			return optionalBoard.get();
		} else {
			throw new NotFoundException("해당하는 게시글을 찾을 수 없습니다");
		}
	}

	@Override
	public List<Board> findBoardByCategoryIdByDesc(Long categoryId) {
		return boardRepository.findByBoardCategoryCategoryIdOrderByCreatedTimeDesc(categoryId);
	}

	@Override
	public List<Board> searchByTitle(String keyword) {
		return boardRepository.findByBoardTitleContaining(keyword);
	}

	@Override
	public Page<Board> getBoardList(Pageable pageable) {
		return boardRepository.findAllByOrderByCreatedTimeDesc(pageable);
	}

	@Override
	public void increaseReadCount(Long boardId) {
		Optional<Board> optionalBoard = boardRepository.findById(boardId);
		if (optionalBoard.isPresent()) {
			Board board = optionalBoard.get();
			board.setBoardReadCount(board.getBoardReadCount() + 1);
			boardRepository.save(board);
		}
	}

	@Override
	public void updateRecommendCount(Long boardId, int boardRecommend) {
		Optional<Board> optionalBoard = boardRepository.findById(boardId);
		if (optionalBoard.isPresent()) {
			Board board = optionalBoard.get();
			board.setBoardRecommend(boardRecommend);
			boardRepository.save(board);
		}
	}

	@Override
	public String findUserIdByBoardId(Long boardId) {
		Optional<Board> optionalBoard = boardRepository.findById(boardId);
		if (optionalBoard.isPresent()) {
			Board board = optionalBoard.get();
			if (board.getUser() != null) {
				return board.getUser().getUserId();
			}
		}
		return null;
	}

	@Override
	public String findCategoryNameByBoardId(Long boardId) {
		Optional<Board> optionalBoard = boardRepository.findById(boardId);
		if (optionalBoard.isPresent()) {
			Board board = optionalBoard.get();
			if (board.getBoardCategory() != null) {
				return board.getBoardCategory().getCategoryName();
			}
		}
		return null;
	}

	@Override
	public int findRecommendCountByBoardId(Long boardId) {
		Optional<Board> optionalBoard = boardRepository.findById(boardId);
		if (optionalBoard.isPresent()) {
			Board board = optionalBoard.get();
			return board.getBoardRecommend();
		}
		return 0;
	}

	@Override
	public Page<Board> getBoardsByCategoryId(Long categoryId, Pageable pageable) {
		return boardRepository.findAllByBoardCategoryCategoryIdOrderByCreatedTimeDesc(categoryId, pageable);
	}

	@Override
	public void softDeleteBoard(Long boardId) {
		Optional<Board> optionalBoard = boardRepository.findById(boardId);
		if(optionalBoard.isPresent()) {
			Board board = optionalBoard.get();
			board.setDeleted(true);
			boardRepository.save(board);
		} else {
			throw new NotFoundException("해당 게시물을 찾을 수 없습니다:"+boardId);
		}
	}

}
