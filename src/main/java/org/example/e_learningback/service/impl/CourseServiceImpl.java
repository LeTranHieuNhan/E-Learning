package org.example.e_learningback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CategoryDto;
import org.example.e_learningback.dto.CourseDto;
import org.example.e_learningback.dto.UserDto;
import org.example.e_learningback.entity.*;
import org.example.e_learningback.exception.CategoryNotFoundException;
import org.example.e_learningback.exception.CourseNotFoundException;
import org.example.e_learningback.exception.UserNotFoundException;
import org.example.e_learningback.repository.*;
import org.example.e_learningback.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRatingRepository courseRatingRepository;
    private final FileRepository fileRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;

    @Override
    public List<CourseDto> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        courses.forEach(course -> {
            List<CourseRating> courseRatings = courseRatingRepository.findAllByCourseId(course.getId());
            double averageRating = courseRatings.stream()
                    .mapToDouble(CourseRating::getRating)
                    .average()
                    .orElse(0.0);
            course.setAverageRating(averageRating);
            course.setTotalReviews((long) courseRatings.size());
        });

        return courses.stream()
                .map(this::mapToCourseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto findCourseById(Long id) {
        return courseRepository.findById(id)
                .map(course -> {
                    List<CourseRating> courseRatings = courseRatingRepository.findAllByCourseId(course.getId());
                    double averageRating = courseRatings.stream()
                            .mapToDouble(CourseRating::getRating)
                            .average()
                            .orElse(0.0);
                    course.setAverageRating(averageRating);
                    course.setTotalReviews((long) courseRatings.size());
                    return mapToCourseDto(course);
                })
                .orElseThrow(() -> new CourseNotFoundException("Course does not exist with ID: " + id));
    }

    @Override
    @Transactional
    public CourseDto createCourse(CourseDto newCourseDTO, Long userID, Long categoryID) {
        Course newCourse = new Course();
        newCourse.setTitle(newCourseDTO.getTitle());
        newCourse.setDescription(newCourseDTO.getDescription());
        newCourse.setCourse_duration(newCourseDTO.getCourse_duration());

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with ID: " + userID));
        newCourse.setUser(user);

        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist with ID: " + categoryID));
        newCourse.setCategory(category);

        newCourse.setImages(newCourseDTO.getImages().stream()
                .map(fileDto -> {
                    File file = new File();
                    file.setSource(fileDto);
                    file.setCourse(newCourse);
                    return file;
                })
                .collect(Collectors.toList())
        );

        Course savedCourse = courseRepository.save(newCourse);

        return mapToCourseDto(savedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        } else {
            throw new CourseNotFoundException("Course does not exist with ID: " + id);
        }
    }

    @Override
    @Transactional
    public CourseDto updateCourse(Long id, CourseDto newCourseDTO) {
        return courseRepository.findById(id)
                .map(existingCourse -> {
                    // Update basic fields
                    existingCourse.setTitle(newCourseDTO.getTitle());
                    existingCourse.setDescription(newCourseDTO.getDescription());
                    existingCourse.setCourse_duration(newCourseDTO.getCourse_duration());

                    // Handle images
                    // Remove all existing images individually to enable orphan removal
                    List<File> currentImages = existingCourse.getImages();
                    if (currentImages != null) {
                        Iterator<File> iterator = currentImages.iterator();
                        while (iterator.hasNext()) {
                            File file = iterator.next();
                            iterator.remove();
                        }
                    }

                    // Add new images
                    List<File> newImages = newCourseDTO.getImages().stream()
                            .map(fileDto -> {
                                File file = new File();
                                file.setSource(fileDto);
                                file.setCourse(existingCourse);
                                return file;
                            })
                            .collect(Collectors.toList());

                    existingCourse.getImages().addAll(newImages);

                    // Save and return updated course
                    Course savedCourse = courseRepository.save(existingCourse);
                    return mapToCourseDto(savedCourse);
                })
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + id + " not found"));
    }


    @Override
    public List<CourseDto> searchCourses(String keyword) {
        List<Course> foundCourses = courseRepository.searchByTitleOrText(keyword);
        return foundCourses.stream()
                .map(this::mapToCourseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getTeacherCourse(Long teacherId) {
        Course course = courseRepository.findByUserId(teacherId).orElseThrow(() -> new UserNotFoundException("Teacher does not exist with ID: " + teacherId));

        return courseRepository.findAllByUserId(teacherId).stream()
                .map(this::mapToCourseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getStudentCourse(Long studentId) {

        return courseEnrollmentRepository.findAllByUserId(studentId).stream()
                .map(CourseEnrollment::getCourse)
                .map(this::mapToCourseDto)
                .collect(Collectors.toList());

    }

    // Helper method to map Course entity to CourseDto
    private CourseDto mapToCourseDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setTitle(course.getTitle());
        courseDto.setDescription(course.getDescription());
        courseDto.setCourse_duration(course.getCourse_duration());
        courseDto.setAverageRating(course.getAverageRating());
        courseDto.setTotalReviews(course.getTotalReviews());

        courseDto.setImages(course.getImages().stream()
                .map(File::getSource)
                .collect(Collectors.toList()));

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(course.getCategory().getId());
        categoryDto.setName(course.getCategory().getName());
        courseDto.setCategory(categoryDto);

        UserDto userDto = new UserDto();
        userDto.setId(course.getUser().getId());
        userDto.setName(course.getUser().getUsername());
        userDto.setEmail(course.getUser().getEmail());
        userDto.setAvatar(course.getUser().getAvatar());
        userDto.setBio(course.getUser().getBio());
        courseDto.setUser(userDto);

        return courseDto;
    }
}
