<%@ page import="org.itstep.model.entity.Course" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="res"/>
<fmt:setLocale value="${param.lang}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="courses" /></title>
</head>
<body>
<%@ include file="/templates/header1.jsp"%>

<div class="container">
<h3><fmt:message key="CourseFilter" /></h3>
<form action="/user/courses" method="get">
    <table>
            <thead>
            <tr class="text-center">
                <th><fmt:message key="name" /></th><th><fmt:message key="Nameukr" /></th><th><fmt:message key="Topic" /></th><th><fmt:message key="Topicukr" /></th><th><fmt:message key="startDate" /></th><th><fmt:message key="status" /></th>
            </tr>
            </thead>
        <tbody>
        <tr>
    <td class="text-center"><input type="text" name="fname" value=<%out.print(request.getAttribute("fname")==null?"":request.getAttribute("fname"));%>/></td>
    <td class="text-center"><input type="text" name="fnameukr" value=<%out.print(request.getAttribute("fnameukr")==null?"":request.getAttribute("fnameukr"));%>/></td>
    <td class="text-center"><input type="text" name="ftopic" value=<%out.print(request.getAttribute("ftopic")==null?"":request.getAttribute("ftopic"));%>/></td>
    <td class="text-center"><input type="text" name="ftopicukr" value="<%out.print(request.getAttribute("ftopicukr")==null?"":request.getAttribute("ftopicukr"));%>/></td>
    <td class="text-center"><input type="date" name="fstartDate" value=<%out.print(request.getAttribute("fstartDate")==null?"":request.getAttribute("fstartDate"));%>/></td>
    <td class="text-center">
<%--        <select class="form-control" name="fstatus" id="fstatus" th:with="statusList = ${ {'In Progress', 'Finished','Not Started'} }">--%>
<%--            <option value="0"></option>--%>
<%--            <option th:each="status : ${statusList}"--%>
<%--                    th:selected="${fstatus}"--%>
<%--                    th:value="${status}?${status}:0"--%>
<%--                    th:text="${status}">--%>
<%--            </option>--%>
<%--        </select>--%>
        <select name="fstatus" class="form-control">
            <option value="0"></option>
            <% String[] statusList = new String[]{"In Progress", "Finished", "Not Started"};%>
            <c:forEach items="${statusList}" var="status">
                <option value="${status}"
                        <c:if test="${status eq selectedStatus}">selected="selected"</c:if>>
                        ${status}
                </option>
            </c:forEach>
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
    <td class="text-center"><input type="number" min="0" name="fdurationMin" value=<%out.print(request.getAttribute("fdurationMin")==null?"":request.getAttribute("fdurationMin"));%>/></td>
    <td class="text-center"><input type="number" min="0" name="fdurationMax" value=<%out.print(request.getAttribute("fdurationMax")==null?"":request.getAttribute("fdurationMax"));%>/></td>
    <td class="text-center"><input type="date" name="fendDate" value=<%out.print(request.getAttribute("fendDate")==null?"":request.getAttribute("fendDate"));%>/></td>
    <td class="text-center"><input type="text" name="fteacher" value=<%out.print(request.getAttribute("fteacher")==null?"":request.getAttribute("fteacher"));%>/></td>
    <td><button class="btn btn-primary" type="submit" ><fmt:message key="filter" /></button></td>
           <td><a href="/user/courses" class="btn btn-warning"><fmt:message key="resetFilter"/></a> </td>
       </tr>
        </tbody>
    </table>
</form>
</div>
<div class="container">
    <span><%@ include file="/templates/pagerC.jsp"%></span>
<table>
    <thead>
    <tr class="text-center">
        <th><fmt:message key="idc"/></th><th><fmt:message key="name" /></th><th><fmt:message key="Nameukr" /></th><th><fmt:message key="Topic" /></th><th><fmt:message key="Topicukr" /></th><th><fmt:message key="startDate"/></th>
        <th><fmt:message key="dur"/></th><th><fmt:message key="endDate" /></th><th><fmt:message key="teacher" /></th><th><fmt:message key="qtystuds" /></th><th><fmt:message key="action" /></th>

    </tr>
    </thead>
    <tbody>
    <% List<Course> courses = pager.getEntities();%>
    <% if(courses.size()==0){%>
    <tr>
        <td colspan="2"><fmt:message key="no_cour_avail" /></td>
    </tr>
    <%}%>
    <tr>
        <% for (Course course: courses){%>
        <td class="text-center"><span><% out.print(course.getId());%></span></td>
        <td class="text-center"><span><% out.print(course.getName());%></span></td>
        <td class="text-center"><span><% out.print(course.getNameukr());%></span></td>
        <td class="text-center"><span><% out.print(course.getTopic()));%></span></td>
        <td class="text-center"><span><% out.print(course.getTopicukr());%></span></td>
        <td class="text-center"><span><% out.print(course.getStartDate());%></span></td>
        <td class="text-center"><span><% out.print(course.getDuration());%></span></td>
        <td class="text-center"><span><% out.print(course.getEndDate());%></span></td>
        <td class="text-center"><span><% out.print(course.getTeacher().getUsername());%></span></td>
        <td class="text-center"><span><% out.print(course.getEnrolledStudents().size());%></span></td>
<%--        <td class="text-center" th:if="${!course.getEnrolledStudentsId().contains(#request.userPrincipal.principal.getId())}">--%>
<%--            <div th:if="${course.isNotStarted()}">--%>
<%--                <form action="/courses/enroll" method="post">--%>
<%--                    <input type="hidden" name="courseId" th:value="${course.id}">--%>
<%--                    <input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
<%--                    <button class="btn btn-success" type="submit" th:text="#{enroll}">Enroll</button>--%>
<%--            </form>--%>
<%--            </div>--%>
<%--        </td>--%>
<%--        <td class="text-center" th:if="${course.getEnrolledStudentsId().contains(#request.userPrincipal.principal.getId())}">--%>
<%--            <div th:if="${course.isNotStarted()}">--%>
<%--            <form action="/courses/unenroll" method="post">--%>
<%--                <input type="hidden" name="courseId" th:value="${course.id}">--%>
<%--                <input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
<%--                <button class="btn btn-warning" type="submit" th:text="#{unenroll}">Unenroll</button>--%>
<%--            </form>--%>
<%--            </div>--%>
<%--        </td>--%>

<%--        <td th:if="${#request.userPrincipal.principal.isAdmin()}">--%>
<%--            <div th:if="${!course.isFinished()}">--%>
<%--            <a class="btn btn-dark mx-2" th:href="@{/adminupdate/{id}(id=${course.id})}" th:text="#{edit}">Edit</a>--%>
<%--            </div>--%>
<%--        </td>--%>
<%--        <td th:if="${#request.userPrincipal.principal.isAdmin()}">--%>
<%--            <div th:if="${course.isNotStarted()}">--%>
<%--                <form th:action="@{/adminupdate/delete/{id}(id=${course.id})}" method="post">--%>
<%--                    <input type="hidden" name="_csrf" th:value="${_csrf.token}" />--%>
<%--                    <button type="submit" class="btn btn-danger" th:text="#{delete}">Delete</button>--%>
<%--                </form>--%>
<%--            </div>--%>
<%--        </td>--%>
    </tr>
    <%}%>
    </tbody>
</table>

</div>
<div th:include="fragments/footer::footer"></div>
</body>
</html>