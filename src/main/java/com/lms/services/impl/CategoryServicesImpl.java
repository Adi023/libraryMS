package com.lms.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dto.CategoryDto;
import com.lms.entity.Category;
import com.lms.exception.ResourceNotFound;
import com.lms.repository.CategoryRepository;
import com.lms.services.CategoryServices;

@Service
public class CategoryServicesImpl implements CategoryServices {

	private static final Logger logger = LoggerFactory.getLogger(CategoryServicesImpl.class);

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		if (categoryDto == null) {
			throw new IllegalArgumentException("Category DTO cannot be null");
		}
		try {
			Category category = dtoToEntity(categoryDto);
			Category c = categoryRepo.save(category);
			return entityToDto(c);
			
		} catch (Exception e) {
			e.printStackTrace();
			// Log the exception instead of printing the stack trace
			logger.error("Error while saving Category: {}", e.getMessage(), e);
			throw new IllegalArgumentException("Category DTO cannot be null");
		}

	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto) {
		
		if (categoryDto == null) {
			throw new IllegalArgumentException("Category DTO cannot be null");
		}
		try {
			Category category = categoryRepo.findById(categoryDto.getCategoryId())
					.orElseThrow(() -> new ResourceNotFound("Category not found with ISBN: " + categoryDto.getCategoryId()));
			
			category.setCategoryName(categoryDto.getCategoryName());
			category.setDescription(categoryDto.getDescription());
			category.setISBN(categoryDto.getISBN());
	
			Category updatedCategory=categoryRepo.save(category);
			logger.info("Category updated successfully: CategoryId {}", updatedCategory.getISBN());
			
			return entityToDto(updatedCategory);

		} catch (ResourceNotFound e) {
	        logger.warn("Update failed - Category not found: CategoryId {}", categoryDto.getISBN());
	        throw e; // Re-throw the ResourceNotFound exception

	    } catch (Exception e) {
	        logger.error("Unexpected error while updating Category: {}", e.getMessage(), e);
	        throw new RuntimeException("An error occurred while updating the Category", e);
	    }
	}

	@Override
	public CategoryDto getCategoryById(Long categoryId) {
		if (categoryId == null) {
			throw new IllegalArgumentException("ISBN no is null");
		}

		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category not found with this ID"));
		logger.info("Category found : ISBN {}", categoryId);
		return entityToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		try {
			List<Category> category=categoryRepo.findAll();
			
			if(category.isEmpty()) {
				logger.warn("No category found in the database");
				throw new ResourceNotFound("No category available.");
			}
			
			logger.info("Retrived {} category from database.",category.size());
			return category.stream().map(this::entityToDto).collect(Collectors.toList());
			
		}catch(Exception e) {
			logger.error("Error while fetching category: {}", e.getMessage(),e);
			throw new RuntimeException("An error occurred while retrieving category", e);
		}
	}

	@Override
	public String deleteCategory(Long categoryId) {
		
		return null;
	}
	
	public CategoryDto entityToDto(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}
	
	public Category dtoToEntity(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Category.class);
	}

}
