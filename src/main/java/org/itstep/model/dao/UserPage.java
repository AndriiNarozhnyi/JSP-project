package org.itstep.model.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.itstep.model.entity.User;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class UserPage {
    private List<User> entities = new ArrayList<>();
    private int pageNumber;
    private int totalPages;
    private int size;
    private int totalRows;

    public UserPage() {
    }
}
