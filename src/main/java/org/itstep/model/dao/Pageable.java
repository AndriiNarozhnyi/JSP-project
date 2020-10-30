package org.itstep.model.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pageable {
    int page = 1;
    int size = 12;
    String sort = "id";

    public Pageable() {
    }
}
