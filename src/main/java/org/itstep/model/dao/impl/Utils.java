package org.itstep.model.dao.impl;

import org.itstep.model.dao.Pageable;

import java.util.List;

public class Utils {
    public static String queryBuilder(List<Long> ids, String sqlTemplate, Pageable pageable) {
        StringBuilder sb = new StringBuilder(sqlTemplate);
        sb.append("(");
        for (int i=0; i<ids.size();i++){
            sb.append(ids.get(i));
            if(i!=ids.size()-1){
                sb.append(",");
            }
        }
        sb.append(") ");
        sb.append("order by ");
        sb.append(pageable.getSort());
        return sb.toString();
    }
}
