<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ page import="java.util.*, java.text.*" %>
<%@ page import="org.itstep.model.entity.User" %>
<%@ page import="java.util.stream.Collectors" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="res"/>
<fmt:setLocale value="${param.lang}"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="useredit" /></title>
</head>
<body>
<%@ include file="/templates/header1.jsp"%>
<% User user = (User)request.getAttribute("user");%>
<% Set<Role> roles = (Set<Role>)request.getAttribute("roles");
Set<String> rolest = (Set<String>)request.getAttribute("rolest");%>
<div class="container">
<%--        <div th:each="err : ${error}">--%>
<%--            <span th:text="${err.getValue()}"></span>--%>
<%--        </div>--%>

<form action="admin/user/save" method="post">
    <div class="row justify-content-md-left">
        <div class="col col-lg-2">
            <label><fmt:message key="username" /></label>
        </div>
        <div class="col col-lg-3">
            <input type="text" name="username" value="<%=user.getUsername()%>">
        </div>
    </div>
    <div class="row justify-content-md-left">
        <div class="col col-lg-2">
            <label><fmt:message key="usernameUkr" /></label>
        </div>
        <div class="col col-lg-3">
            <input type="text" name="usernameukr" value="<%=user.getUsernameukr()%>">
        </div>
    </div>
    <div class="row justify-content-md-left">
        <div class="col col-lg-2">
            <label><fmt:message key="email" /></label>
        </div>
        <div class="col col-lg-3">
            <input type="text" name="email" value="<%=user.getEmail()%>">
        </div>
    </div>
    <h3><fmt:message key="userStatus" /></h3>
    <label ><fmt:message key="active" /></label><input type="checkbox" name="isActive" value="<%user.isActive();%>">
    <h3><fmt:message key="userAuth" /></h3>
    <table>
        <tbody>
        <% for (Role role:roles){%>
        <tr>
            <td><label><%=role.toString()%></label><input type="checkbox" name="<%=role.toString()%>"
                                                          value=<%=rolest.contains(role.toString())%>></td>
        </tr>
        <%}%>
        </tbody>
    </table>
    <input type="hidden" value="<%=user.getId()%>" name="userId">
<%--    <input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
    <input type="submit" value="<fmt:message key="save"/>">
</form>
</div>
</body>
</html>