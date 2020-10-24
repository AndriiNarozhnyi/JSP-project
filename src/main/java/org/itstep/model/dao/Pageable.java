package org.itstep.model.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pageable {
    int pageNumber = 1;
    int itemsPerPage = 10;
    String sort = "id";

    public Pageable() {
    }
}
