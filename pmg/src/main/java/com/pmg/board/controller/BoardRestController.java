package com.pmg.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmg.board.dto.BoardDto;
import com.pmg.board.entity.Board;
import com.pmg.board.service.BoardService;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {
	@Autowired
	private BoardService boardService;

	@PostMapping("/create")
	public ResponseEntity<String> createBoard(@RequestBody BoardDto boardDto) {
		boardService.createBoard(boardDto);
		return ResponseEntity.ok("게시글 작성 성공");
	}

	@DeleteMapping("/delete/{boardId}")
	public ResponseEntity<String> deleteBoard(@PathVariable("boardId") Long boardId) {
		Board board = boardService.findBoardByBoardId(boardId);
		if (board == null) {
			return ResponseEntity.notFound().build();
		} else {
			boardService.deleteBoard(boardId);
			return ResponseEntity.ok("게시글 삭제 완료!");
		}
	}

	@PutMapping("/update/{boardId}")
	public ResponseEntity<String> updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardDto boardDto) {
		Board board = boardService.findBoardByBoardId(boardId);
		if (board == null) {
			return ResponseEntity.notFound().build();
		} else {
			board.setBoardTitle(boardDto.getBoardTitle());
			board.setBoardContent(boardDto.getBoardContent());
			boardService.updateBoard(boardId, BoardDto.toDto(board));
			return ResponseEntity.ok("게시글 업데이트 성공!");
		}
	}

	@GetMapping("/detail/{boardId}")
	public ResponseEntity<Board> boardDetail(@PathVariable("boardId") Long boardId) {
		Board board = boardService.findBoardByBoardId(boardId);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}