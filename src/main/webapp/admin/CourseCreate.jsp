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
<% List<User> teachers = (List)request.getAttribute("teachers");%>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<%@ include file="/templates/header1.jsp"%>
<div class="container">
    <h3><fmt:message key="createCourse" /></h3>

    <form action="/admin/save" method="post">
        <div class="container">
            <div class="row justify-content-md-left">
                <div class="col col-lg-2">
                    <label><fmt:message key="CourseName" /></label>
                </div>
                <div class="col col-lg-3">
                    <input type="text" name="name" value=<%out.print(request.getAttribute("name")==null?"":request.getAttribute("name"));%>>
                </div>
                <% if (request.getAttribute("incname")!=null){%>
                <div class="alert alert-danger" role="alert">
                    <%out.println(request.getAttribute("incname"));%>
                </div><%}%>
            </div>
            <div class="row justify-content-md-left">
                <div class="col col-lg-2">
                    <label><fmt:message key="CourseNameUkr" /></label>
                </div>
                <div class="col col-lg-3">
                    <input type="text" name="nameukr" value=<%out.print(request.getAttribute("nameukr")==null?"":request.getAttribute("nameukr"));%>>
                </div>
                <% if (request.getAttribute("incnameukr")!=null){%>
                <div class="alert alert-danger" role="alert">
                    <%out.println(request.getAttribute("incnameukr"));%>
                </div><%}%>
            </div>
            <div class="row justify-content-md-left">
                <div class="col col-lg-2">
                    <label><fmt:message key="Topic" /></label>
                </div>
                <div class="col col-lg-3">
                    <input type="text" name="topic" value=<%out.print(request.getAttribute("topic")==null?"":request.getAttribute("topic"));%>>
                </div>
                <% if (request.getAttribute("inctopic")!=null){%>
                <div class="alert alert-danger" role="alert">
                    <%out.println(request.getAttribute("inctopic"));%>
                </div><%}%>
            </div>
            <div class="row justify-content-md-left">
                <div class="col col-lg-2">
                    <label><fmt:message key="Topicukr" /></label>
                </div>
                <div class="col col-lg-3">
                    <input type="text" name="topicukr" value=<%out.print(request.getAttribute("topicukr")==null?"":request.getAttribute("topicukr"));%>>
                </div>
                <% if (request.getAttribute("inctopicukr")!=null){%>
                <div class="alert alert-danger" role="alert">
                    <%out.println(request.getAttribute("inctopicukr"));%>
                </div><%}%>
            </div>
            <div class="row justify-content-md-left">
                <div class="col col-lg-2">
                    <label><fmt:message key="startDate" /></label>
                </div>
                <div class="col col-lg-3">
                    <input type="date" name="startDate" value=<%out.print(request.getAttribute("startDate")==null?"":request.getAttribute("startDate"));%>>
                </div>
                <% if (request.getAttribute("incStartDate")!=null){%>
                <div class="alert alert-danger" role="alert">
                    <%out.println(request.getAttribute("incStartDate"));%>
                </div><%}%>
            </div>
            <div class="row justify-content-md-left">
                <div class="col col-lg-2">
                    <label><fmt:message key="endDate" /></label>
                </div>
                <div class="col col-lg-3">
                    <input type="date" name="endDate" value=<%out.print(request.getAttribute("endDate")==null?"":request.getAttribute("startDate"));%>>
                </div>
                <% if (request.getAttribute("incEndDate")!=null){%>
                <div class="alert alert-danger" role="alert">
                    <%out.println(request.getAttribute("incEndDate"));%>
                </div><%}%>
                <% if (request.getAttribute("incendBeforeStart")!=null){%>
                <div class="alert alert-danger" role="alert">
                    <%out.println(request.getAttribute("incendBeforeStart"));%>
                </div><%}%>
            </div>
            <div class="row justify-content-md-left">
                <div class="col col-lg-2">
                    <Label><fmt:message key="selectTeacher" /></Label>
                </div>
                <div class="col col-lg-2">
                    <select name="teacherId" class="form-control">
                        <option value="0"></option>
                        <c:forEach items="${teachers}" var="teacher">
                            <option value="${teacher.id}"
                                    <c:if test="${teacher.id eq selectedTeacher}">selected="selected"</c:if>>
                                    ${teacher.username}
                            </option>
                        </c:forEach>
                    </select>
<%--                    <div class="alert alert-danger" role="alert">--%>
<%--                        This is a danger alert—check it out!--%>
<%--                    </div>--%>
                </div>
            </div>

<%--            <input type="hidden" name="_csrf" th:value="${_csrf.token}"/>--%>
            <div class="row justify-content-md-left">
                <div class="col col-lg-2">
                    <button class="btn btn-secondary" type="submit"><fmt:message key="save" /></button>
                </div>
            </div>
        </div>

    </form>
</div>
</body>
</html>