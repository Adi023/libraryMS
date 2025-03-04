package com.lms.services;

import java.util.List;
import com.lms.dto.BooksDto;

public interface BooksServices {

	public BooksDto addBook(BooksDto bookDto);
	public BooksDto updateBook(BooksDto bookDto);
	public BooksDto getBookById(Long isbn);
	public List<BooksDto> getAllBooks();
	
}
