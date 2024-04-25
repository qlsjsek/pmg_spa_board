package com.pmg.board;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.pmg.PmgApplicationTests;
import com.pmg.board.dto.BoardReplyDto;
import com.pmg.board.entity.BoardReply;
import com.pmg.board.service.BoardReplyService;

import jakarta.transaction.Transactional;

public class BoardReplyServiceTest extends PmgApplicationTests{
	@Autowired
	BoardReplyService boardReplyService;
	
	@Test
	@Transactional
	//@Disabled
	@Rollback(false)
	void createReply() {
		BoardReplyDto replyDto = new BoardReplyDto();
		replyDto.setBoardId(1L);
		replyDto.setUserId("1");
		replyDto.setBoardReplyContent("test1");
		BoardReply reply = boardReplyService.createReply(replyDto);
		System.out.println("댓글생성확인:"+reply);
	}
}
