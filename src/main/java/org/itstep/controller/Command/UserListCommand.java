package org.itstep.controller.Command;

import org.itstep.model.dao.Pageable;
import org.itstep.model.dao.UserPage;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class UserListCommand implements Command{
    private UserService userService;
    public UserListCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = "/admin/user";
        Map<String, String> paramMap = CommandUtility.refactorParamMap(request.getParameterMap());
        Pageable pageable = CommandUtility.makePageable(paramMap);
        String url = CommandUtility.makeUrl(path, paramMap);

        UserPage page = new UserPage();

        if ((paramMap.get("fusername")==null||paramMap.get("fusername")=="")
            &&(paramMap.get("fusernameukr")==null||paramMap.get("fusernameukr")=="")){
            page = userService.findAllUsers(pageable);
        } else {
            page = userService.findUsersByFilter(paramMap.get("fusername"),
                    paramMap.get("fusernameukr"), pageable);
        }

        request.setAttribute("page", page);
        request.setAttribute("url", url);
        paramMap.forEach((k,v)-> request.setAttribute(k,v));

        return "/admin/userlist.jsp";
    }
}
