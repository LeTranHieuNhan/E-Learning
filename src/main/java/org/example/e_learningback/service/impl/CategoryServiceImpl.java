package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CategoryDto;
import org.example.e_learningback.entity.Category;
import org.example.e_learningback.entity.Course;
import org.example.e_learningback.repository.CategoryRepository;
import org.example.e_learningback.repository.CourseRepository;
import org.example.e_learningback.service.CategoryService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final GenericMapper genericMapper;

    @Override
    public List<CategoryDto> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return genericMapper.mapList(categories, CategoryDto.class);
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if(!category.isPresent()) {
            throw new RuntimeException("Category does not exist");
        }
        return genericMapper.map(category, CategoryDto.class);
    }

    @Override
    @Transactional
    public CategoryDto createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        Category savedCategory = categoryRepository.save(category);

        return genericMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if(!category.isPresent()) {
            throw new RuntimeException("Category does not exist");
        }

        List<Course> courses = category.get().getCourses();

        courses.forEach(course -> course.setCategory(null));

        categoryRepository.deleteById(id);
        courseRepository.saveAll(courses);
    }

    @Override
    @Transactional
    public CategoryDto udpateCategory(Long id, String name) {
        Optional<Category> category = categoryRepository.findById(id);

        if(!category.isPresent()) {
            throw new RuntimeException("Category does not exist");
        }

        Category existCategory = category.get();
        existCategory.setName(name);

        List<Course> courses = category.get().getCourses();

        courses.forEach(course -> course.getCategory().setName(existCategory.getName()));

        Category savedCategory = categoryRepository.save(existCategory);
        courseRepository.saveAll(courses);

        return genericMapper.map(savedCategory, CategoryDto.class);
    }
}
