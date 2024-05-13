package org.example.e_learningback.service;

import org.example.e_learningback.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategories();
    CategoryDto findCategoryById (Long id);
    CategoryDto createCategory(String name);
    void deleteCategory(Long id);
    CategoryDto udpateCategory(Long id, String name);
}
