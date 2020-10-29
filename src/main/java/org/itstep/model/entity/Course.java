package org.itstep.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
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
    Set<Long> enrolledStudents = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isStarted(){
        return LocalDate.now().isAfter(startDate)&&LocalDate.now().isBefore(endDate);
    }

    public boolean isFinished(){
        return LocalDate.now().isAfter(endDate);
    }

    public boolean isNotStarted(){
        return LocalDate.now().isBefore(startDate);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameukr='" + nameukr + '\'' +
                ", topic='" + topic + '\'' +
                ", topicukr='" + topicukr + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", teacher=" + teacher +
                '}';
    }
}
