package org.example.e_learningback.utils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenericMapper {

    private final ModelMapper modelMapper;

//    @Autowired
//    public GenericMapper(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }

    public <S, T> T map(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }

        T target = modelMapper.map(source, targetClass);

        return target;
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
