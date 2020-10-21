<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ page import="java.util.*, java.text.*" %>
<%@ page import="org.itstep.model.entity.User" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="res"/>
<fmt:setLocale value="${param.lang}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="userList" /></title>
</head>
<body>
<%@ include file="/templates/header1.jsp"%>
<div class="container">
<h3><fmt:message key="listUsr" /></h3>
<%--    <form action="/user/filter" method="get">--%>
<%--        <div class="row justify-content-md-left">--%>
<%--            <div class="col col-lg-1">--%>
<%--                <label><fmt:message key="username" /></label>--%>
<%--            </div>--%>
<%--            <div class="col col-lg-2">--%>
<%--                <input type="text" name="fusername" th:value="${fusername}?${fusername}:''">--%>
<%--            </div>--%>
<%--            <div class="col col-lg-1">--%>
<%--                <label><fmt:message key="usernameUkr" /></label>--%>
<%--            </div>--%>
<%--            <div class="col col-lg-2">--%>
<%--                <input type="text" name="fusernameukr" th:value="${fusernameukr}?${fusernameukr}:''">--%>
<%--            </div>--%>
<%--            <div class="col col-lg-1">--%>
<%--                <button class="btn btn-primary" type="submit"><fmt:message key="filter" /></button>--%>
<%--            </div>--%>
<%--            <div class="col col-lg-2">--%>
<%--            <a href="/user" class="btn btn-warning"><fmt:message key="resetFilter" /></a>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </form>--%>
<table>
    <thead>
    <tr class="text-center">
        <th><fmt:message key="idc" /></th><th><fmt:message key="usrname" /></th><th><fmt:message key="usernameUkr" /></th><th><fmt:message key="email" /></th><th><fmt:message key="active" /></th><th><fmt:message key="roles" /></th>
    </tr>
    </thead>
    <tbody>
    <% List<User> users = (List)request.getAttribute("users");%>
    <% if(users.size()==0){%>
    <tr>
        <td colspan="2"><fmt:message key="noUsAv" /></td>
    </tr>
    <%}%>
    <% for (User user: users){%>
    <tr class="text-center">
        <td><span><% out.print(user.getId());%></span></td>
        <td><span><% out.print(user.getUsername());%></span></td>
        <td><span><% out.print(user.getUsernameukr());%></span></td>
        <td><span><% out.print(user.getEmail());%></span></td>
        <td><span><% out.print(user.isActive()?"YES":"NO");%></span></td>
        <td><span><% out.print(user.getRoles());%></span></td>
        <td><a href="/admin/user/edit?userId=<%=user.getId()%>"><fmt:message key="edit" /></a></td>
    </tr>
    <%}%>
    </tbody>
</table>
</form>

</div>
</body>
</html>