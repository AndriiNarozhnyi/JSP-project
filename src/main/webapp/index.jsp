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

<%!
String getFormattedDate(){

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
    return sdf.format(new Date());
}
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="hello" /></title>
</head>
    <body>
    <%@ include file="/templates/header1.jsp"%>
    <div class="container">
        <h2>
           <span><fmt:message key="welcome" /></span>
            <i><%= getFormattedDate() %></i>
        </h2>
    </div>
    </body>
</html>
