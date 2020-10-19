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
<%--    <div><span th:text="${message_pres}?${message_pres}:''"></span></div>--%>
<%--    <div><span th:text="${emailIncorrect}?${emailIncorrect}:''"></span></div>--%>
<%--    <div><span th:text="${incusername}?${incusername}:''"></span></div>--%>
<%--    <div><span th:text="${incusernameukr}?${incusernameukr}:''"></span></div>--%>
<%--    <div><span th:text="${incpassword}?${incpassword}:''"></span></div>--%>
    <br>
    <form action="/registration" method="post">
        <div class="row justify-content-md-left">
            <div class="col col-lg-2">
                <label><fmt:message key="username"/></label>
            </div>
            <div class="col col-lg-3">
                <input type="text" name="username"
<%--                       value="${username}?${username}:''" th:placeholder="#{username}"--%>
                />
            </div>
        </div>
        <div class="row justify-content-md-left">
            <div class="col col-lg-2">
                <label><fmt:message key="usernameUkr" /></label>
            </div>
            <div class="col col-lg-3">
                <input type="text" name="usernameukr"
<%--                       th:value="${usernameukr}?${usernameukr}:''" th:placeholder="#{usernameUkr}" --%>
                />
            </div>
        </div>
        <div class="row justify-content-md-left">
            <div class="col col-lg-2">
                <label><fmt:message key="email" /></label>
            </div>
            <div class="col col-lg-3">
                <input type="text" name="email"
<%--                       th:value="${email}?${email}:''" th:placeholder="#{email}"--%>
                />
            </div>
        </div>
        <div class="row justify-content-md-left">
            <div class="col col-lg-2">
                <label><fmt:message key="password" /></label>
            </div>
            <div class="col col-lg-3">
                <input type="password" name="password"
<%--                       th:placeholder="#{password}" --%>
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
