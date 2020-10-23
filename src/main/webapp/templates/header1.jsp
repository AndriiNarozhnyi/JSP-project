<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ page import="java.util.*, java.text.*" %>
<%@ page import="org.itstep.model.entity.Role" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="res"/>
<fmt:setLocale value="${param.lang}"/>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    </head>
    <body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="/">MiniEdx</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/"><fmt:message key="Home" /></a>
                </li>
                <% if(request.getSession().getAttribute("role")!=null&&request.getSession().getAttribute("role")!= Role.UNKNOWN){%>
                    <li class="nav-item">
                        <a class="nav-link" href="/courses"><fmt:message key="Courses" /></a>
                    </li>
                <%}%>
                    <% if(request.getSession().getAttribute("role")!=null&&request.getSession().getAttribute("role")!= Role.UNKNOWN){%>
                    <li class="nav-item">
                        <a class="nav-link" href="/cabinet" ><fmt:message key="Cabinet" /></a>
                    </li>
                <%}%>
                <% if (request.getSession().getAttribute("role")==Role.TEACHER){%>
                    <li class="nav-item">
                        <a class="nav-link" href="/teacher" ><fmt:message key="teacherCabinet" /></a>
                    </li>
                <%}%>
                    <% if (request.getSession().getAttribute("role")==Role.ADMIN){%>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/create"><fmt:message key="CreateCourse" /></a>
                    </li>
                <%}%>
                <% if (request.getSession().getAttribute("role")==Role.ADMIN){%>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/user"><fmt:message key="UserEdit" /></a>
                    </li>
                <%}%>
            </ul>

        <a class="btn btn-primary mx-2" href="?sessionLocale=en"><fmt:message key="switch-en" /></a>
        <a class="btn btn-primary mx-2" href="?sessionLocale=uk"><fmt:message key="switch-uk" /></a>
            <% if(request.getSession().getAttribute("role")==null
                    ||request.getSession().getAttribute("role")==Role.UNKNOWN){%>
                    <a class="btn btn-success mx-2" href="/login"><fmt:message key="login" /></a>
            <%}%>
            <% if(request.getSession().getAttribute("role")==null
                    ||request.getSession().getAttribute("role")==Role.UNKNOWN){%>
            <a class="btn btn-success mx-2" href="/registration" ><fmt:message key="registration" /></a>
            <%}%>
            <% if(request.getSession().getAttribute("role")!=null&&request.getSession().getAttribute("role")!= Role.UNKNOWN){%>
                    <form action="/logout" method="post">
<%--                        <input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
                        <input type="submit" class="btn btn-warning mx-2 mt-3" value=<fmt:message key="signout" />>
                    </form>
            <%}%>

            </div>
        </div>
    </nav>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>

    </body>
</html>
