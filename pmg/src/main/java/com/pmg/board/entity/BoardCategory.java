package com.pmg.board.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pmg.board.dto.BoardCategoryDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class BoardCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	private String categoryName;
	
	public static BoardCategory toEntity(BoardCategoryDto dto) {
		return BoardCategory.builder()
								.categoryName(dto.getCategoryName())
								.build();
	}
	
	@OneToMany(mappedBy = "boardCategory",cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@JsonIgnore
	private List<Board> boards;
	
}
