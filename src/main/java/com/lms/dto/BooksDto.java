package com.lms.dto;

import java.time.LocalDate;
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
public class BooksDto {
	
	private Long ISBN;
	private String title;
	private String author;
	private String publisher;
	private LocalDate publicationDate;
	private Long totalQuantity;
	private Long availableQuantity;

}
