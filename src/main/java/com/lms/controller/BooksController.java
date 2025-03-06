package com.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.BooksDto;
import com.lms.exception.ResourceNotFound;
import com.lms.services.BooksServices;

@RestController
@RequestMapping("/books")
public class BooksController  {
	
	private BooksServices bookService;
	
	private static final Logger logger=LoggerFactory.getLogger(BooksController.class);

	public  BooksController(BooksServices bookService) {
		this.bookService=bookService;
	}

	@PostMapping
	public ResponseEntity<BooksDto> addBook(@RequestBody BooksDto bookDto) {
		logger.info("Recived request to add books: {}"+bookDto.getTitle());
		
		try {
			BooksDto savedBook = bookService.addBook(bookDto);
			logger.info("Book added successfully with ISBN:{}"+savedBook.getISBN());
			return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
		}catch(IllegalArgumentException e) {
			logger.warn("Invalid book data: {}",e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}catch(Exception e) {
			logger.error("Error while adding book :{}",e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PutMapping("/{ISBN}")
	public ResponseEntity<BooksDto> updateBook(@PathVariable("ISBN") Long isbn,@RequestBody BooksDto bookDto) {
		logger.info("Received request to update book with ISBN: {}", isbn);

        try {
            bookDto.setISBN(isbn); // Ensure the ISBN from path matches the request body
            BooksDto updatedBook = bookService.updateBook(bookDto);
            logger.info("Book updated successfully with ISBN: {}", updatedBook.getISBN());
            return ResponseEntity.ok(updatedBook);
        } catch (ResourceNotFound e) {
            logger.warn("Book not found for update: ISBN {}", isbn);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error while updating book: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
	}

	 @GetMapping("/{ISBN}")
	    public ResponseEntity<BooksDto> getBookById(@PathVariable("ISBN") Long isbn) {
	        logger.info("Received request to fetch book with ISBN: {}", isbn);

	        try {
	            BooksDto bookDto = bookService.getBookById(isbn);
	            logger.info("Book retrieved successfully: {}", bookDto.getTitle());
	            return ResponseEntity.ok(bookDto);
	        } catch (ResourceNotFound e) {
	            logger.warn("Book not found with ISBN: {}", isbn);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        } catch (Exception e) {
	            logger.error("Error while fetching book: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	 // Get all books
	    @GetMapping
	    public ResponseEntity<List<BooksDto>> getAllBooks() {
	        logger.info("Received request to fetch all books.");

	        try {
	            List<BooksDto> books = bookService.getAllBooks();
	            if (books.isEmpty()) {
	                logger.warn("No books found in the database.");
	                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(books);
	            }
	            logger.info("Retrieved {} books from the database.", books.size());
	            return ResponseEntity.ok(books);
	        } catch (Exception e) {
	            logger.error("Error while fetching books: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

}