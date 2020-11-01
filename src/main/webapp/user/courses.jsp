<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ page import="java.util.*, java.text.*" %>
<%@ page import="org.itstep.model.entity.Course" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="res"/>
<fmt:setLocale value="${param.lang}"/>
<% List<String> statusList = Arrays.asList(new String[]{"In Progress", "Finished", "Not Started"});%>
<%DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); %>

<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="courses" /></title>
</head>
<body>
<%@ include file="/templates/header1.jsp"%>

<div class="container">
<h3><fmt:message key="CourseFilter" /></h3>
<form action="<%=request.getAttribute("filterLink")%>" method="get">
    <table>
            <thead>
            <tr class="text-center">
                <th><fmt:message key="name"/></th><th><fmt:message key="Nameukr"/></th><th><fmt:message key="Topic"/></th><th><fmt:message key="Topicukr"/></th><th><fmt:message key="startDate"/></th><th><fmt:message key="status"/></th>
            </tr>
            </thead>
        <tbody>
        <tr>
    <td class="text-center"><input type="text" name="fname" value="<%out.print(request.getAttribute("fname")==null?"":request.getAttribute("fname"));%>"/></td>
    <td class="text-center"><input type="text" name="fnameukr" value="<%out.print(request.getAttribute("fnameukr")==null?"":request.getAttribute("fnameukr"));%>"/></td>
    <td class="text-center"><input type="text" name="ftopic" value="<%out.print(request.getAttribute("ftopic")==null?"":request.getAttribute("ftopic"));%>"/></td>
    <td class="text-center"><input type="text" name="ftopicukr" value="<%out.print(request.getAttribute("ftopicukr")==null?"":request.getAttribute("ftopicukr"));%>"/></td>
    <td class="text-center"><input type="date" name="fstartDate" value="<%out.print(request.getAttribute("fstartDate")==null?"":request.getAttribute("fstartDate"));%>"/></td>
    <td class="text-center">
        <select name="fstatus" class="form-control">
            <option value="0"></option>
            <% for (String status:statusList){%>
            <option value="<%=status%>"
                    <% if(status.equals(request.getAttribute("selectedStatus"))){%> selected="selected" <%}%>>
                <%=status%>
            </option>
            <%}%>
        </select>
    </td>
        </tr>
        </tbody>
        <thead>
        <tr class="text-center">
            <th><fmt:message key="durmin" /></th><th><fmt:message key="durmax" /></th><th><fmt:message key="endDate" /></th><th><fmt:message key="teacher" /></th>
        </tr>
        </thead>
        <tbody>
       <tr>
    <td class="text-center"><input type="number" min="0" name="fdurationMin" value="<%out.print(request.getAttribute("fdurationMin")==null?"":request.getAttribute("fdurationMin"));%>"/></td>
    <td class="text-center"><input type="number" min="0" name="fdurationMax" value="<%out.print(request.getAttribute("fdurationMax")==null?"":request.getAttribute("fdurationMax"));%>"/></td>
    <td class="text-center"><input type="date" name="fendDate" value="<%out.print(request.getAttribute("fendDate")==null?"":request.getAttribute("fendDate"));%>"/></td>
    <td class="text-center"><input type="text" name="fteacher" value="<%out.print(request.getAttribute("fteacher")==null?"":request.getAttribute("fteacher"));%>"/></td>
    <td><button class="btn btn-primary" type="submit" ><fmt:message key="filter" /></button></td>
           <td><a href="<%=request.getAttribute("filterLink")%>" class="btn btn-warning"><fmt:message key="resetFilter"/></a> </td>
       </tr>
        </tbody>
    </table>
</form>
</div>
<div class="container">
    <span><%@ include file="/templates/pagerC.jsp"%></span>
<table>
    <span><%@ include file="/templates/courseThead.jsp"%></span>
    <tbody>
    <% List<Course> courses = pager.getEntities();%>
    <% if(courses.size()==0){%>
    <tr>
        <td colspan="3"><fmt:message key="no_cour_avail" /></td>
    </tr>
    <%}%>
    <tr>
        <% for (Course course: courses){%>
        <span><%@ include file="/templates/courseDataFields.jsp"%></span>

        <td>
            <% if(course.isNotStarted()&&course.getTeacher().getId()!=session.getAttribute("userId")){%>
            <% if(!course.getEnrolledStudents().contains(session.getAttribute("userId"))){%>
                <form action="/user/enroll" method="post">
                    <input type="hidden" name="courseId" value="<%=course.getId()%>">
                    <input type="hidden" name="path" value=<%=request.getAttribute("filterLink")%>>
<%--                    <input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
                    <button class="btn btn-success btn-xs" type="submit" style="height: 30px;"><fmt:message key="enroll" /></button>
                </form>
            <%}%>
            <%}%>
        </td>
        <td>
            <% if(course.isNotStarted()&&course.getTeacher().getId()!=session.getAttribute("userId")){%>
            <% if(course.getEnrolledStudents().contains(session.getAttribute("userId"))){%>
            <form action="/user/unenroll" method="post">
                <input type="hidden" name="courseId" value=<%=course.getId()%>>
                <input type="hidden" name="path" value=<%=request.getAttribute("filterLink")%>>
                <%--                    <input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
                <button class="btn btn-xs btn-warning btn-xs" type="submit" style="height: 30px;"><fmt:message key="unenroll" /></button>
            </form>
            <%}%>
            <%}%>
        </td>
        <td>
            <% if(session.getAttribute("role")==Role.ADMIN&&(!course.isFinished())){%>
            <form action="/admin/edit" method="post">
                <input type="hidden" name="courseId" value=<%=course.getId()%>>
                <input type="hidden" name="path" value=<%=request.getAttribute("filterLink")%>>
                <%--                    <input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
            <button class="btn btn-xs btn-dark" type="submit" style="height: 30px;"><fmt:message key="edit" /></button>
            </form>
            <%}%>
        </td>
        <td>
            <% if(course.isNotStarted()){%>
            <% if(session.getAttribute("role")==Role.ADMIN){%>
            <form action="/admin/predelete" method="post">
                <input type="hidden" name="courseId" value=<%=course.getId()%>>
                <%--<input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
                <button class="btn btn-xs btn-danger" type="submit" style="height: 30px;"><fmt:message key="delete" /></button>
            </form>
            <%}%>
            <%}%>
        </td>
    </tr>
    <%}%>
    </tbody>
</table>
</div>
</body>
</html>