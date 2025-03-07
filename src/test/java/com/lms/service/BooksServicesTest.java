package com.lms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.lms.dto.BooksDto;
import com.lms.entity.Books;
import com.lms.exception.ResourceNotFound;
import com.lms.repository.BooksRepository;
import com.lms.services.impl.BooksServicesImpl;

@ExtendWith(MockitoExtension.class)
public class BooksServicesTest {

    @Mock
    private BooksRepository bookRepository;

    @Mock
    private ModelMapper modelMapper; 
    
    @InjectMocks
    private BooksServicesImpl booksService;

    @Test
    void testFindBookById_WhenBookDoesNotExist() {
        // Arrange: Mock repository to return empty Optional
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert: Expect ResourceNotFound exception
        assertThrows(ResourceNotFound.class, () -> booksService.getBookById(1L));

        // Verify repository interaction
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testFindBookById_WhenBookExists() {

    	Books mockBook = new Books(1L, "Effective Java", "Joshua Bloch", "publisher",null, 11L, 11L, null, null);
    	 BooksDto mockBookDto = new BooksDto(1L, "Effective Java", "Joshua Bloch", "publisher",null, 11L, 11L);
     	
        // Mock repository response
        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));

        // Mock ModelMapper behavior
        when(modelMapper.map(mockBook, BooksDto.class)).thenReturn(mockBookDto);

        // Act: Call the service method
        BooksDto result = booksService.getBookById(1L);

        // Assert: Check the returned values

        // Assert: Check values
        assertNotNull(result);
        assertEquals(1L, result.getISBN());
        assertEquals("Effective Java", result.getTitle());
        assertEquals("Joshua Bloch", result.getAuthor());

        // Verify repository interaction
        verify(bookRepository, times(1)).findById(1L);
    }
}
