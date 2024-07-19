package org.example.e_learningback.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.e_learningback.dto.CourseSessionDto;
import org.example.e_learningback.entity.CourseSession;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenericMapper {

    private final ModelMapper modelMapper;

    public <S, T> T map(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return modelMapper.map(source, targetClass);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        if (source == null) {
            return Collections.emptyList();
        }
        return source.stream()
                .map(element -> map(element, targetClass))
                .collect(Collectors.toList());
    }


}
