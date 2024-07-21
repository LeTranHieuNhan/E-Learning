package org.example.e_learningback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.e_learningback.entity.Assignment;
import org.example.e_learningback.entity.File;
import org.example.e_learningback.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSubmissionDto {
    private Long id;

    private AssignmentDto assignment;
    private UserDto user;
    private List<FileDto> files;
}
