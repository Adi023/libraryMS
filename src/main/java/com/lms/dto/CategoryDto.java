package com.lms.dto;

import java.util.List;

import com.lms.entity.Books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

	private Long categoryId;
	private String categoryName;
	private Long ISBN;
	private List<Books> books;
	private String description;
}
