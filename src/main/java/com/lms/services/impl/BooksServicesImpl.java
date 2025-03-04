package com.lms.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lms.dto.BooksDto;
import com.lms.entity.Books;
import com.lms.exception.ResourceNotFound;
import com.lms.repository.BooksRepository;
import com.lms.services.BooksServices;

@Service
public class BooksServicesImpl implements BooksServices {

	private static final Logger logger = LoggerFactory.getLogger(BooksServicesImpl.class);

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BooksRepository booksRepo;

	@Override
	public BooksDto addBook(BooksDto bookDto) {

		if (bookDto == null) {
			throw new IllegalArgumentException("Book DTO cannot be null");
		}
		try {
			Books book = dtoToEntity(bookDto);
			Books b = booksRepo.save(book);
			return entityToDto(b);

		} catch (Exception e) {
			e.printStackTrace();
			// Log the exception instead of printing the stack trace
			logger.error("Error while saving book: {}", e.getMessage(), e);
			throw new IllegalArgumentException("Book DTO cannot be null");
		}

	}

	@Override
	public BooksDto updateBook(BooksDto bookDto) {

		if (bookDto == null) {
			throw new IllegalArgumentException("Book DTO cannot be null");
		}
		try {
			Books book = booksRepo.findById(bookDto.getISBN())
					.orElseThrow(() -> new ResourceNotFound("Book not found with ISBN: " + bookDto.getISBN()));
			book.setAuthor(bookDto.getAuthor());
			book.setAvailableQuantity(bookDto.getAvailableQuantity());
			book.setPublicationDate(bookDto.getPublicationDate());
			book.setPublisher(bookDto.getPublisher());
			book.setTitle(bookDto.getTitle());
			book.setTotalQuantity(bookDto.getTotalQuantity());
			Books updatedBook = booksRepo.save(book);
			
			logger.info("Book updated successfully: ISBN {}", updatedBook.getISBN());
			
			return entityToDto(updatedBook);

		} catch (ResourceNotFound e) {
	        logger.warn("Update failed - book not found: ISBN {}", bookDto.getISBN());
	        throw e; // Re-throw the ResourceNotFound exception

	    } catch (Exception e) {
	        logger.error("Unexpected error while updating book: {}", e.getMessage(), e);
	        throw new RuntimeException("An error occurred while updating the book", e);
	    }
	}

	@Override
	public BooksDto getBookById(Long isbn) {
		if (isbn == null) {
			throw new IllegalArgumentException("ISBN no is null");
		}

		Books book = booksRepo.findById(isbn).orElseThrow(() -> new ResourceNotFound("Book not found with this ISBN"));
		logger.info("Book found : ISBN {}", isbn);
		return entityToDto(book);
	}

	@Override
	public List<BooksDto> getAllBooks() {
		try {
			List<Books> book=booksRepo.findAll();
			
			if(book.isEmpty()) {
				logger.warn("No books found in the database");
				throw new ResourceNotFound("No books available.");
			}
			
			logger.info("Retrived {} books from database.",book.size());
			return book.stream().map(this::entityToDto).collect(Collectors.toList());
			
		}catch(Exception e) {
			logger.error("Error while fetching books: {}", e.getMessage(),e);
			throw new RuntimeException("An error occurred while retrieving books", e);
		}
	}
	
	public BooksDto entityToDto(Books book) {
		return modelMapper.map(book, BooksDto.class);
	}

	public Books dtoToEntity(BooksDto booksDto) {
		return modelMapper.map(booksDto, Books.class);
	}

}