package org.itstep.controller.Command.Utility;

import org.itstep.model.entity.Course;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationAndLocaleUtility {

    public static ResourceBundle setBundle(HttpServletRequest request){
        ResourceBundle bundle = ResourceBundle.getBundle("res", new Locale(
                request.getSession().getAttribute("lang").toString()
        ));
        return bundle;
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
            answer.put("incname", setBundle(request).getString("incname"));
            check = false;
        }
        if (checkNameEmpty(form.get("nameukr"))){
            answer.put("incnameukr", setBundle(request).getString("incnameukr"));
            check = false;
        }
        if (checkNameEmpty(form.get("topic"))){
            answer.put("inctopic", setBundle(request).getString("inctopic"));
            check = false;
        }
        if (checkNameEmpty(form.get("topicukr"))){
            answer.put("inctopicukr", setBundle(request).getString("inctopicukr"));
            check = false;
        }
        if (checkDate(form.get("startDate"))){
            if(course.getStartDate()==null||course.isNotStarted()) {
                answer.put("incStartDate", setBundle(request).getString("incStartDate"));
                check = false;
            }
        }
        if (checkDate(form.get("endDate"))){
            answer.put("incEndDate", setBundle(request).getString("incEndDate"));
            check = false;
        }
        if ((!(answer.containsKey("incStartDate")||answer.containsKey("incendtDate")))&&
                (LocalDate.parse(form.get("startDate")).isAfter(LocalDate.parse(form.get("endDate"))))){
            answer.put("incendBeforeStart", setBundle(request).getString("incendBeforeStart"));
            check=false;
        }
        res.add(answer);
        res.add(check);
        return res;

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

    private static boolean emailValid(String email) {
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
