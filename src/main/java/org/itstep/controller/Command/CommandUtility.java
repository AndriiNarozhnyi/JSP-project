package org.itstep.controller.Command;

import org.itstep.model.dao.Pageable;
import org.itstep.model.entity.Course;
import org.itstep.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommandUtility {
    static ResourceBundle setBundle(HttpServletRequest request){
        ResourceBundle bundle = ResourceBundle.getBundle("res", new Locale(
                request.getSession().getAttribute("lang").toString()
        ));
        return bundle;
    }
    static void setUserRole(HttpServletRequest request, Role role, Long userId) {
        HttpSession session = request.getSession();
        session.setAttribute("role", role);
        session.setAttribute("userId", userId);
    }

    static boolean checkUserIsLogged(HttpServletRequest request, Long userId){
        HashSet<Long> loggedUsers = (HashSet<Long>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(userId::equals)){
            return true;
        }
        loggedUsers.add(userId);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

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

    public static boolean checkNameEmpty(String name) {
        if (name==null||name.length()==0){
            return true;
        }
        return false;
    }

    private static boolean checkDate(String date) {
        if (date==null||date.length()==0||
                LocalDate.parse(date).isBefore(LocalDate.now())){
            return true;
        }
        return false;
    }

    public static List checkCourseIncorrect(Map<String, String> form, HttpServletRequest request, Course course) {
        List<Object> res = new ArrayList<>();
        Map<String, String> answer = new HashMap<>();
        boolean check = true;
        if (checkNameEmpty(form.get("name"))){
            answer.put("incname", CommandUtility.setBundle(request).getString("incname"));
            check = false;
        }
        if (checkNameEmpty(form.get("nameukr"))){
            answer.put("incnameukr", CommandUtility.setBundle(request).getString("incnameukr"));
            check = false;
        }
        if (checkNameEmpty(form.get("topic"))){
            answer.put("inctopic", CommandUtility.setBundle(request).getString("inctopic"));
            check = false;
        }
        if (checkNameEmpty(form.get("topicukr"))){
            answer.put("inctopicukr", CommandUtility.setBundle(request).getString("inctopicukr"));
            check = false;
        }
        if (checkDate(form.get("startDate"))){
            if(course.getStartDate()==null||course.isNotStarted()) {
                answer.put("incStartDate", CommandUtility.setBundle(request).getString("incStartDate"));
                check = false;
            }
        }
        if (checkDate(form.get("endDate"))){
            answer.put("incEndDate", CommandUtility.setBundle(request).getString("incEndDate"));
            check = false;
        }
        if ((!(answer.containsKey("incStartDate")||answer.containsKey("incendtDate")))&&
                (LocalDate.parse(form.get("startDate")).isAfter(LocalDate.parse(form.get("endDate"))))){
            answer.put("incendBeforeStart", CommandUtility.setBundle(request).getString("incendBeforeStart"));
            check=false;
        }
        res.add(answer);
        res.add(check);
        return res;

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

    public static List checkUserIncorrect(Map<String, String> form, HttpServletRequest request) {
        List<Object> res = new ArrayList<>();
        Map<String, String> answer = new HashMap<>();
        boolean check = false;
        if (checkNameEmpty(form.get("username"))) {
            answer.put("incusername", setBundle(request).getString("incusername"));
            check = true;
        }
        if (checkNameEmpty(form.get("usernameukr"))) {
            answer.put("incusernameukr", setBundle(request).getString("incusernameukr"));
            check = true;
        }
        if (!emailValid(form.get("email"))) {
            answer.put("emailIncorrect", setBundle(request).getString("emailIncorrect"));
            check = true;
        }
        if (form.get("pasword")!=null) {
            if (passwordCheckIncorrect(form.get("password"))) {
                answer.put("incpassword", setBundle(request).getString("incpassword"));
                check = true;
            }
        }
        res.add(answer);
        res.add(check);
        return res;
    }

    static boolean emailValid(String email) {
        final Pattern mailRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = mailRegex.matcher(email);
        return email!=null&&matcher.find();
    }

    private static boolean passwordCheckIncorrect(String password) {
        final Pattern passwordRegex =
                Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,32}$");
        Matcher matcher = passwordRegex.matcher(password);
        return !matcher.find();
    }

}
