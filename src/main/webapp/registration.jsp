<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ page import="java.util.*, java.text.*" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="res"/>
<fmt:setLocale value="${param.lang}"/>

<head>
    <meta charset="UTF-8">
    <title><fmt:message key="registration" /></title>
</head>
<body>
<%@ include file="/templates/header1.jsp"%>
<div class="container">
    <% if (request.getAttribute("messageUserPresent")!=null){%>
    <div class="alert alert-danger" role="alert">
        <%out.println(request.getAttribute("messageUserPresent"));%>
    </div><%}%>
    <% if (request.getAttribute("incusername")!=null){%>
    <div class="alert alert-danger" role="alert">
        <%out.println(request.getAttribute("incusername"));%>
    </div><%}%>
    <% if (request.getAttribute("incusernameukr")!=null){%>
    <div class="alert alert-danger" role="alert">
        <%out.println(request.getAttribute("incusernameukr"));%>
    </div><%}%>
    <% if (request.getAttribute("emailIncorrect")!=null){%>
    <div class="alert alert-danger" role="alert">
        <%out.println(request.getAttribute("emailIncorrect"));%>
    </div><%}%>
    <% if (request.getAttribute("incpassword")!=null){%>
    <div class="alert alert-danger" role="alert">
        <%out.println(request.getAttribute("incpassword"));%>
    </div><%}%>

    <br>
    <form action="/registration" method="post">
        <div class="row justify-content-md-left">
            <div class="col col-lg-2">
                <label><fmt:message key="username"/></label>
            </div>
            <div class="col col-lg-3">
                <input type="text" name="username"
                       value="<%out.print(request.getAttribute("username")==null?"":request.getAttribute("username"));%>"
                />
            </div>
        </div>
        <div class="row justify-content-md-left">
            <div class="col col-lg-2">
                <label><fmt:message key="usernameUkr" /></label>
            </div>
            <div class="col col-lg-3">
                <input type="text" name="usernameukr"
                       value="<%out.print(request.getAttribute("usernameukr")==null?"":request.getAttribute("usernameukr"));%>"
                />
            </div>
        </div>
        <div class="row justify-content-md-left">
            <div class="col col-lg-2">
                <label><fmt:message key="email" /></label>
            </div>
            <div class="col col-lg-3">
                <input type="text" name="email"
                       value="<%out.print(request.getAttribute("email")==null?"":request.getAttribute("email"));%>"
                />
            </div>
        </div>
        <div class="row justify-content-md-left">
            <div class="col col-lg-2">
                <label><fmt:message key="password" /></label>
            </div>
            <div class="col col-lg-3">
                <input type="password" name="password"
                       value="<%out.print(request.getAttribute("password")==null?"":request.getAttribute("password"));%>"
                />
            </div>
        </div>
<%--        <input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
        <div class="row justify-content-md-left">
            <div class="col col-lg-2">
                <button type="submit"><fmt:message key="add" /></button>
            </div>
        </div>
    </form>
</div>

</body>
</html>
