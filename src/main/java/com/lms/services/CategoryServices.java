package com.lms.services;

import java.util.List;
import com.lms.dto.CategoryDto;

public interface CategoryServices {
	
	public CategoryDto addCategory(CategoryDto category);
	public CategoryDto updateCategory(CategoryDto category);
	public CategoryDto getCategoryById(Long categoryId);
	public List<CategoryDto> getAllCategories();
//	public String deleteCategory(Long categoryId);
}