package org.example.e_learningback.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDto {
    private Long id;
    private String name;
    private Date uploaded_at;
    private Date updated_at;
    private Date deadline;
    private String description;
}
