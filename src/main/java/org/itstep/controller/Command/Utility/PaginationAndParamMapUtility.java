package org.itstep.controller.Command.Utility;

import org.itstep.model.dao.Pageable;
import org.itstep.model.entity.Course;
import org.itstep.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaginationAndParamMapUtility {

    public static Map refactorParamMap(Map parameterMap) {
        Map<String,String> paramMap = new HashMap<>();
        Map m=parameterMap;
        Set s = m.entrySet();
        Iterator it = s.iterator();
        while(it.hasNext()) {

            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

            String key = entry.getKey();
            String[] valuear = entry.getValue();
            String value = "";
            for (int i = 0; i < valuear.length; i++) {
                value = valuear[i].toString();
            }
            paramMap.put(key, value);
        }
        return paramMap;
    }



    public static List<Object> makeUrlAndCheckFilter(String path, Map<String, String> paramMap) {
        List<Object> res = new ArrayList<>();
        StringBuilder url = new StringBuilder(path);
        boolean filterOff = true;
        paramMap.remove("page");
        paramMap.remove("size");
        Iterator it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            char first = ((String)pair.getKey()).charAt(0);
            if(first=='f'){
                String key = (String)pair.getKey();
                if (url.toString().contains("?")){
                    url.append("&");
                } else {
                    url.append("?");
                }
                url.append(key);
                url.append("=");
                url.append(pair.getValue());
                if ((String)pair.getValue()!=null&&(String)pair.getValue()!=""){
                    filterOff = false;
                }
            }
        }
        res.add(url.toString());
        res.add(filterOff);
        return res;
    }

    public static Pageable makePageable(Map<String, String> paramMap) {
        Pageable pageable = new Pageable();
        if(paramMap.get("size")!=null){
            pageable.setSize(Integer.parseInt(paramMap.get("size")));
        }
        if(paramMap.get("page")!=null){
            pageable.setPage(Integer.parseInt(paramMap.get("page")));
        }
        if(paramMap.get("totRows")!=null&&Integer.parseInt(paramMap.get("totRows"))
                <pageable.getSize()* pageable.getPage()){
            int totRows = Integer.parseInt(paramMap.get("totRows"));
            if (totRows/ pageable.getSize()==0){
                pageable.setPage(1);
            } else {
                pageable.setPage(totRows/ pageable.getSize());
            }
        }
        return pageable;
    }



}
