package com.pmg.board.entity;

import com.pmg.board.dto.BoardImageDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class BoardImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardImageId;
	private String imageName;

	public static BoardImage toEntity(BoardImageDto dto) {
		BoardImage boardImage = BoardImage.builder().imageName(dto.getImageName()).build();

		if (dto.getBoardId() != null) {
			boardImage.setBoard(Board.builder().boardId(dto.getBoardId()).build());
		}

		return boardImage;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "board_id")
	@ToString.Exclude
	private Board board;
}
