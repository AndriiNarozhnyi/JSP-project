package org.itstep.model.dao;

import lombok.Getter;
import lombok.Setter;
import org.itstep.model.entity.Course;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class CoursePage {
    private List<Course> entities = new ArrayList<>();
    private int pageNumber;
    private int totalPages;
    private int size;
    private int totalRows;

    public CoursePage() {
    }
}
