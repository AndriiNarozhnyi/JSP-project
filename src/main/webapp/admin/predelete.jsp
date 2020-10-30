<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ page import="org.itstep.model.entity.Course" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="res"/>
<fmt:setLocale value="${param.lang}"/>
<% Course course = (Course)request.getAttribute("course"); %>
<%DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); %>
<html>
<head>
    <title><fmt:message key="deleteCourse" /></title>
</head>
<body>
<%@ include file="/templates/header1.jsp"%>
<div class="container">
    <table>
    <%@ include file="/templates/courseThead.jsp"%>
<tbody>
<tr>
    <span><%@ include file="/templates/courseDataFields.jsp"%></span>
</tr>
</tbody>
    </table>
        <br>
        <div class="alert alert-danger" role="alert">
        <fmt:message key="deleteCourseNote" />
        </div>
        <br>
        <a href="/user/courses"><fmt:message key="cancelDelete" /></a>
        <br>
        <br>
        <br>


        <form action="/admin/delete" method="post">
            <input type="hidden" name="courseId" value=<%=course.getId()%>>
            <%--<input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
            <button class="btn btn-xs btn-danger" type="submit" style="height: 30px;"><fmt:message key="delete" /></button>
        </form>
</div>
</body>
</html>
