package com.pmg.board.service;

import java.util.List;
import java.util.Optional;

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

@Service
@Transactional
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardCategoryRepository boardCategoryRepository;
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	BoardImageService boardImageService;
	
	@Override
	public List<BoardCategory> findCategories() {
		return boardCategoryRepository.findAll();
	}

	@Override
    public Board createBoard(BoardDto boardDto, BoardImageDto boardImageDto) {
        Board board = Board.builder()
                .boardCategory(BoardCategory.builder().categoryId(boardDto.getCategoryId()).build())
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
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
	public List<Board> findBoardList() {
		return boardRepository.findAll();
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
	public List<Board> findBoardByCategoryId(Long categoryId) {
		return boardRepository.findByBoardCategoryCategoryId(categoryId);
	}

	@Override
	public List<Board> searchByTitle(String keyword) {
		return boardRepository.findByBoardTitleContaining(keyword);
	}

	@Override
	public Page<Board> getBoardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

}
