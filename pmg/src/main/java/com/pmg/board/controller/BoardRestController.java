package com.pmg.board.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pmg.board.dto.BoardDto;
import com.pmg.board.dto.BoardImageDto;
import com.pmg.board.entity.Board;
import com.pmg.board.entity.BoardImage;
import com.pmg.board.service.BoardImageService;
import com.pmg.board.service.BoardService;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private BoardImageService boardImageService;

	@PostMapping("/create")
	public ResponseEntity<String> createBoard(@RequestParam(value = "boardImage", required = false) MultipartFile file,
			@RequestParam("categoryId") Long categoryId, @RequestParam("boardTitle") String boardTitle,
			@RequestParam("boardContent") String boardContent, @RequestParam("userId") String userId) {
		BoardDto boardDto = new BoardDto();
		boardDto.setCategoryId(categoryId);
		boardDto.setBoardTitle(boardTitle);
		boardDto.setBoardContent(boardContent);

		BoardImageDto boardImageDto = null;
		if (file != null && !file.isEmpty()) {
			try {
				String uploadDir = "src/main/resources/static/images";
				Path uploadPath = Paths.get(uploadDir);
				Files.createDirectories(uploadPath);
				String originalFilename = file.getOriginalFilename();
				Path filePath = uploadPath.resolve(originalFilename);
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				boardImageDto = new BoardImageDto();
				boardImageDto.setImageName(originalFilename);

			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 중 오류가 발생했습니다.");
			}
		}
		boardService.createBoard(boardDto, boardImageDto, userId);
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

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Board>> boardByCategoryId(@PathVariable("categoryId") Long categoryId) {
		List<Board> boards = boardService.findBoardByCategoryIdByDesc(categoryId);
		return new ResponseEntity<>(boards, HttpStatus.OK);
	}

	@GetMapping("/searchByTitle")
	public ResponseEntity<List<Board>> searchByTitle(@RequestParam String keyword) {
		List<Board> searchBoard = boardService.searchByTitle(keyword);
		return new ResponseEntity<>(searchBoard, HttpStatus.OK);
	}

	@GetMapping("/boards")
	public Page<Board> getBoardList(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		PageRequest pageable = PageRequest.of(page, size);
		return boardService.getBoardList(pageable);
	}

	@GetMapping("/image/{boardId}")
	public ResponseEntity<BoardImage> getBoardImageByBoardId(@PathVariable("boardId") Long boardId) {
		BoardImage boardImage = boardImageService.boardImageByBoardId(boardId);
		return ResponseEntity.ok(boardImage);
	}

	@GetMapping("/readCount/{boardId}")
	public ResponseEntity<String> increaseReadCount(@PathVariable("boardId") Long boardId) {
		boardService.increaseReadCount(boardId);
		return ResponseEntity.ok("조회수 증가 성공");
	}

	@PutMapping("/recommend/{boardId}")
	public ResponseEntity<String> updateRecommendCount(@PathVariable("boardId") Long boardId,
			@RequestParam("boardRecommend") int boardRecommend) {
		boardService.updateRecommendCount(boardId, boardRecommend);
		return ResponseEntity.ok("게시글 추천 성공~");
	}

	@GetMapping("/api/board/sorting")
	public List<Board> getBoards(@RequestParam("sorting") String sorting) {
		if ("createdTimeDesc".equals(sorting)) {
			return boardService.findBoardListByDesc();
		} else if ("createdTimeAsc".equals(sorting)) {
			return boardService.findBoardListByAsc();
		} else {
			return boardService.findBoardListByDesc();
		}
	}
	
    @GetMapping("/userId/{boardId}")
    public ResponseEntity<String> findUserIdByBoardId(@PathVariable("boardId") Long boardId) {
        String userId = boardService.findUserIdByBoardId(boardId);
        
        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 아이디를 찾을 수 없습니다: " + boardId);
        }
    }

}