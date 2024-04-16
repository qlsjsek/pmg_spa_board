package com.pmg.board.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pmg.board.dto.BoardDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	@Id
	@SequenceGenerator(name = "BOARD_BOARD_NO_SEQ",sequenceName = "BOARD_BOARD_NO_SEQ",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;
	private String boardTitle;
	@Column(columnDefinition = "CLOB")
	private String boardContent;
	private int boardReadCount;
	private int boardRecommend;
	
	public static Board toEntity(BoardDto dto) {
		return Board.builder()
					.boardTitle(dto.getBoardTitle())
					.boardContent(dto.getBoardContent())
					.boardReadCount(dto.getBoardReadCount())
					.boardRecommend(dto.getBoardRecommend())
					.boardCategory(BoardCategory.builder().categoryId(dto.getCategoryId()).build())
					.build();
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@ToString.Exclude
	@JsonIgnore
	private BoardCategory boardCategory;
	
	@OneToMany(mappedBy = "board",cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@JsonIgnore
	private List<BoardImage> images;
}
