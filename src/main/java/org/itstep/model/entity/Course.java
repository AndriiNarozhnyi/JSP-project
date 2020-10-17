package org.itstep.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private Long id;
    private String name;
    private String nameukr;
    private String topic;
    private String topicukr;
    private LocalDate startDate;
    private LocalDate endDate;
    private long duration;
    private User teacher;
    Set<User> enrolledStudents = new HashSet<>();
}
