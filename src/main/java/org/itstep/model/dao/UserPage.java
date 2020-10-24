package org.itstep.model.dao;

import lombok.Getter;
import lombok.Setter;
import org.itstep.model.entity.User;
import java.util.List;

@Getter
@Setter
public class UserPage {
    private List<User> items;
    private int pageNumber;
    private int totalPages;
    private int size;

    public UserPage() {
    }
}
