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
<html>
<head>
    <title><fmt:message key="login" /></title>

</head>
<body>
<%@ include file="/templates/header1.jsp"%>
<div class="container">
        <h1><fmt:message key="login"/></h1><br/>
        <form method="post" action="${pageContext.request.contextPath}/login">

            <input type="text" name="name" label=<fmt:message key="username" />><br/>
            <input type="password" name="pass" label=<fmt:message key="password" />><br/><br/>
            <input class="button" type="submit" value="Войти">

        </form>
        <br/>

</div>
</body>
</html>