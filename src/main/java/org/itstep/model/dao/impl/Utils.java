package org.itstep.model.dao.impl;

import java.util.List;

public class Utils {
    public static String queryBuilder(List<Long> ids, String sqlTemplate) {
        StringBuilder sb = new StringBuilder(sqlTemplate);
        sb.append("(");
        for (int i=0; i<ids.size();i++){
            sb.append(ids.get(i));
            if(i!=ids.size()-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
