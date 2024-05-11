package org.example.e_learningback.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CourseDto;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.entity.Category;
import org.example.e_learningback.entity.Course;
import org.example.e_learningback.entity.User;
import org.example.e_learningback.repository.CategoryRepository;
import org.example.e_learningback.repository.CourseRepository;
import org.example.e_learningback.repository.UserRepository;
import org.example.e_learningback.service.CourseService;
import org.example.e_learningback.utils.GenericMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final GenericMapper genericMapper;

    @Override
    public List<CourseDto> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return genericMapper.mapList(courses, CourseDto.class);
    }

    @Override
    public CourseDto findCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (!course.isPresent()) {
            throw new RuntimeException("Course does not exist");
        }

        return genericMapper.map(course.get(), CourseDto.class);
    }

    @Override
    @Transactional
    public CourseDto createCourse(CourseDto newCourseDTO, Long userID, Long categoryID) {
        Course newCourse = genericMapper.map(newCourseDTO, Course.class);

        Optional<User> userOptional = userRepository.findById(userID);
        Optional<Category> categoryOptional = categoryRepository.findById(categoryID);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User does not exist");
        }
        if (categoryOptional.isEmpty()) {
            throw new RuntimeException("Category does not exist");
        }

        User user = userOptional.get();
        Category category = categoryOptional.get();

//        newCourse.setUser(user);
//        newCourse.setCategory(category);

        Course savedCourse = courseRepository.save(newCourse);
        CourseDto saveCourseDto = genericMapper.map(savedCourse, CourseDto.class);
//        saveCourseDto.setUser(genericMapper.map(user, UserDto.class));

        return saveCourseDto;
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        boolean isExist = courseRepository.existsById(id);

        if(isExist) {
            Course course = courseRepository.findById(id).get();
//            commentRepository.deleteAll(course.getComments());
            courseRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public CourseDto updateCourse(Long id, CourseDto newCourseDTO) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()) {
            Course existingCourse = courseOptional.get();

            existingCourse.setTitle(newCourseDTO.getTitle());
            existingCourse.setDescription(newCourseDTO.getDescription());
            existingCourse.setCourse_duration(newCourseDTO.getCourse_duration());



            Course savedCourse = courseRepository.save(existingCourse);

            return genericMapper.map(savedCourse, CourseDto.class);
        } else {
            throw new RuntimeException("Course with id " + id + " not found");
        }
    }
}
