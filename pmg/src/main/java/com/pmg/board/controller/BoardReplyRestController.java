package com.pmg.board.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmg.board.dto.BoardReplyDto;
import com.pmg.board.entity.BoardReply;
import com.pmg.board.repository.BoardReplyRepository;
import com.pmg.board.service.BoardReplyService;

@RestController
@RequestMapping("/api/boardReply")
public class BoardReplyRestController {
	@Autowired
	private BoardReplyService boardReplyService;
	@Autowired
	private BoardReplyRepository boardReplyRepository;
	
	//댓글 작성
	@PostMapping("/create")
	public ResponseEntity<String> createBoardReply(@RequestBody BoardReplyDto boardReplyDto) {
		boardReplyService.createReply(boardReplyDto);
		return ResponseEntity.ok("댓글 작성 성공");
	}
	
	//댓글 삭제
	@DeleteMapping("/delete/{boardReplyId}")
	public ResponseEntity<String> deleteReply(@PathVariable("boardReplyId") Long boardReplyId) {
		Optional<BoardReply> optionalReply = boardReplyRepository.findById(boardReplyId);
		if (optionalReply.isPresent()) {
			boardReplyService.deleteReply(boardReplyId);
			return ResponseEntity.ok("댓글 삭제 완료");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//댓글 수정
	@PutMapping("/update/{boardReplyId}")
	public ResponseEntity<String> updateBoardReply(@PathVariable("boardReplyId") Long boardReplyId, @RequestBody BoardReplyDto boardReplyDto) {
		Optional<BoardReply> optionalReply = boardReplyRepository.findById(boardReplyId);
		if (optionalReply.isPresent()) {
			BoardReply reply = optionalReply.get();
			reply.setBoardReplyContent(boardReplyDto.getBoardReplyContent());
			boardReplyService.updateReply(boardReplyDto.toDto(reply), boardReplyId);
			return ResponseEntity.ok("업데이트 성공");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//해당 게시글 댓글 리스트 조회
	@GetMapping("/replyList/{boardId}")
	ResponseEntity<List<BoardReply>> boardReplyByBoardId(@PathVariable("boardId") Long boardId) {
		List<BoardReply> replies = boardReplyService.findReplyByBoardId(boardId);
		return new ResponseEntity<>(replies,HttpStatus.OK);
	}
	
	//게시글 댓글 작성한 userId
	@GetMapping("/userId/{boardReplyId}")
	public ResponseEntity<String> findUserIdByBoardReplyId(@PathVariable("boardReplyId") Long boardReplyId) {
		String userId = boardReplyService.findUserIdByBoardReplyId(boardReplyId);
		return ResponseEntity.ok(userId);
	}
	
	
}
