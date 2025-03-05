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

import com.lms.dto.CategoryDto;
import com.lms.exception.ResourceNotFound;
import com.lms.services.CategoryServices;

@RestController
@RequestMapping("/category")
public class CategoryController  {

	
	private static final Logger logger=LoggerFactory.getLogger(CategoryController.class);
	    private final CategoryServices categoryService;

	    public CategoryController(CategoryServices categoryService) {
	        this.categoryService = categoryService;
	    }

	    // Add a new category
	    @PostMapping
	    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
	        logger.info("Received request to add category: {}", categoryDto.getCategoryName());

	        try {
	            CategoryDto savedCategory = categoryService.addCategory(categoryDto);
	            logger.info("Category added successfully: {}", savedCategory.getCategoryName());
	            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	        } catch (IllegalArgumentException e) {
	            logger.warn("Invalid category data: {}", e.getMessage());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        } catch (Exception e) {
	            logger.error("Error while adding category: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	    // Update an existing category
	    @PutMapping("/{categoryId}")
	    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody CategoryDto categoryDto) {
	        logger.info("Received request to update category with ID: {}", categoryId);

	        try {
	            categoryDto.setCategoryId(categoryId); // Ensure category ID matches the path
	            CategoryDto updatedCategory = categoryService.updateCategory(categoryDto);
	            logger.info("Category updated successfully: {}", updatedCategory.getCategoryName());
	            return ResponseEntity.ok(updatedCategory);
	        } catch (ResourceNotFound e) {
	            logger.warn("Category not found: ID {}", categoryId);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        } catch (Exception e) {
	            logger.error("Error while updating category: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	    // Get a category by ID
	    @GetMapping("/{categoryId}")
	    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Long categoryId) {
	        logger.info("Received request to fetch category with ID: {}", categoryId);

	        try {
	            CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
	            logger.info("Category retrieved successfully: {}", categoryDto.getCategoryName());
	            return ResponseEntity.ok(categoryDto);
	        } catch (ResourceNotFound e) {
	            logger.warn("Category not found: ID {}", categoryId);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        } catch (Exception e) {
	            logger.error("Error while fetching category: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	    // Get all categories
	    @GetMapping
	    public ResponseEntity<List<CategoryDto>> getAllCategories() {
	        logger.info("Received request to fetch all categories.");

	        try {
	            List<CategoryDto> categories = categoryService.getAllCategories();
	            if (categories.isEmpty()) {
	                logger.warn("No categories found in the database.");
	                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categories);
	            }
	            logger.info("Retrieved {} categories from the database.", categories.size());
	            return ResponseEntity.ok(categories);
	        } catch (Exception e) {
	            logger.error("Error while fetching categories: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }
	}
