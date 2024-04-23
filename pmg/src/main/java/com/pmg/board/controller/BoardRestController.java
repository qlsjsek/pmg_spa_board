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
import org.springframework.http.CacheControl;
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

	// 게시글 작성
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

	// 게시글 삭제
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

	// 게시글 수정
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

	// 게시글 상세조회
	@GetMapping("/detail/{boardId}")
	public ResponseEntity<Board> boardDetail(@PathVariable("boardId") Long boardId) {
		Board board = boardService.findBoardByBoardId(boardId);
		if (board != null) {
			return ResponseEntity.ok(board);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 카테고리별 게시글
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Board>> boardByCategoryId(@PathVariable("categoryId") Long categoryId) {
		List<Board> boards = boardService.findBoardByCategoryIdByDesc(categoryId);
		return new ResponseEntity<>(boards, HttpStatus.OK);
	}

	// 게시글 검색
	@GetMapping("/searchByTitle")
	public ResponseEntity<List<Board>> searchByTitle(@RequestParam String keyword) {
		List<Board> searchBoard = boardService.searchByTitle(keyword);
		return new ResponseEntity<>(searchBoard, HttpStatus.OK);
	}

	// 페이징
	@GetMapping("/boards")
	public ResponseEntity<Page<Board>> getBoardList(@RequestParam(defaultValue = "0", name = "page") int page,
			@RequestParam(defaultValue = "5", name = "size") int size) {
		PageRequest pageable = PageRequest.of(page, size);
		Page<Board> boardPage = boardService.getBoardList(pageable);
		return ResponseEntity.ok().cacheControl(CacheControl.noCache()).body(boardPage);
	}

	// 이미지조회
	@GetMapping("/image/{boardId}")
	public ResponseEntity<BoardImage> getBoardImageByBoardId(@PathVariable("boardId") Long boardId) {
		BoardImage boardImage = boardImageService.boardImageByBoardId(boardId);
		return ResponseEntity.ok(boardImage);
	}

	// 조회수
	@GetMapping("/readCount/{boardId}")
	public ResponseEntity<String> increaseReadCount(@PathVariable("boardId") Long boardId) {
		boardService.increaseReadCount(boardId);
		return ResponseEntity.ok("조회수 증가 성공");
	}

	// 추천
	@PutMapping("/recommend/{boardId}")
	public ResponseEntity<String> updateRecommendCount(@PathVariable("boardId") Long boardId,
			@RequestParam("boardRecommend") int boardRecommend) {
		boardService.updateRecommendCount(boardId, boardRecommend);
		return ResponseEntity.ok("게시글 추천 성공~");
	}

	// 정렬
	@GetMapping("/sorting")
	public List<Board> getBoards(@RequestParam("sorting") String sorting) {
		if ("createdTimeDesc".equals(sorting)) {
			return boardService.findBoardListByDesc();
		} else if ("createdTimeAsc".equals(sorting)) {
			return boardService.findBoardListByAsc();
		} else {
			return boardService.findBoardListByDesc();
		}
	}

	// userId찾기
	@GetMapping("/userId/{boardId}")
	public ResponseEntity<String> findUserIdByBoardId(@PathVariable("boardId") Long boardId) {
		String userId = boardService.findUserIdByBoardId(boardId);

		if (userId != null) {
			return ResponseEntity.ok(userId);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 아이디를 찾을 수 없습니다: " + boardId);
		}
	}

	// categoryName찾기
	@GetMapping("/categoryName/{boardId}")
	public ResponseEntity<String> findBoardCategoryNameByBoardId(@PathVariable("boardId") Long boardId) {
		String categoryName = boardService.findCategoryNameByBoardId(boardId);
		if (categoryName != null) {
			return ResponseEntity.ok(categoryName);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 카테고리를 찾을 수 없습니다: " + boardId);
		}
	}

	// 추천수 찾기
	@GetMapping("/recommendCount/{boardId}")
	public ResponseEntity<Integer> findBoardRecommendCountByBoardId(@PathVariable("boardId") Long boardId) {
		int recommendCount = boardService.findRecommendCountByBoardId(boardId);
		return ResponseEntity.ok(recommendCount);
	}

	// 카테고리id로 게시판 리스트 페이징
	@GetMapping("/page/category")
	public Page<Board> getBoardsByCategory(@RequestParam("categoryId") Long categoryId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
		PageRequest pageable = PageRequest.of(page, size);
		return boardService.getBoardsByCategoryId(categoryId, pageable);
	}

}