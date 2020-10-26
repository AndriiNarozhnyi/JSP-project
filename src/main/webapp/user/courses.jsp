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
    <title th:text="#{userCabinet}"><fmt:message key="userList" /></title>
</head>
<body>
<%@ include file="/templates/header1.jsp"%>

<div class="container">
<h3 th:text="#{CourseFilter}"> Courses filter & list</h3>
<form action="/courses/filter" method="get">
    <table>
            <thead>
            <tr class="text-center">
                <th th:text="#{name}">Name</th><th th:text="#{Nameukr}">Name-ukr</th><th th:text="#{Topic}">Topic</th><th th:text="#{Topicukr}">Topic_ukr</th><th th:text="#{startDate}">startDate</th><th th:text="#{status}">Status</th>
            </tr>
            </thead>
        <tbody>
        <tr>
    <td class="text-center"><input type="text" name="fname" th:value="${fname}?${fname}:''" /></td>
    <td class="text-center"><input type="text" name="fnameukr" th:value="${fnameukr}?${fnameukr}:''" /></td>
    <td class="text-center"><input type="text" name="ftopic" th:value="${ftopic}?${ftopic}:''" /></td>
    <td class="text-center"><input type="text" name="ftopicukr" th:value="${ftopicukr}?${ftopicukr}:''" /></td>
    <td class="text-center"><input type="date" name="fstartDate" th:value="${fstartDate}?${fstartDate}:''" /></td>
    <td class="text-center">
        <select class="form-control" name="fstatus" id="fstatus" th:with="statusList = ${ {'In Progress', 'Finished','Not Started'} }">
            <option value="0"></option>
            <option th:each="status : ${statusList}"
                    th:selected="${fstatus}"
                    th:value="${status}?${status}:0"
                    th:text="${status}">
            </option>
        </select>
    </td>
        </tr>
        </tbody>
        <thead>
        <tr class="text-center">
            <th th:text="#{durmin}">Duration min</th><th th:text="#{durmax}">Duration max</th><th th:text="#{endDate}">endDate</th><th th:text="#{teacher}">Teacher</th>
        </tr>
        </thead>
        <tbody>
       <tr>
    <td class="text-center"><input type="number" min="0" name="fdurationMin" th:value="${fdurationMin}?${fdurationMin}:''" /></td>
    <td class="text-center"><input type="number" min="0" name="fdurationMax" th:value="${fdurationMax}?${fdurationMax}:''" /></td>
    <td class="text-center"><input type="date" name="fendDate" th:value="${fendDate}?${fendDate}:''" /></td>
    <td class="text-center"><input type="text" name="fteacher" th:value="${fteacher}?${fteacher}:''" /></td>
    <td><button class="btn btn-primary" type="submit" th:text="#{Filter}">Filter</button></td>
           <td><a href="/courses" class="btn btn-warning" th:text="#{resfilter}">Reset filter</a> </td>
       </tr>
        </tbody>
    </table>
</form>
</div>
<div class="container">
    <div th:include="fragments/pager::pager"/>
<table>
    <thead>
    <tr class="text-center">
        <th th:text="#{idc}"> ID </th><th th:text="#{name}"> Name </th><th th:text="#{Nameukr}"> Name-ukr </th><th th:text="#{Topic}"> Topic </th><th th:text="#{Topicukr}"> Topic_ukr </th><th th:text="#{startDate}">startDate</th>
        <th th:text="#{dur}"> Duration </th><th th:text="#{endDate}"> endDate </th><th th:text="#{teacher}"> teacher </th><th th:text="#{qtystuds}"> Q-ty of students</th><th th:text="#{action}"> Action</th>

    </tr>
    </thead>
    <tbody>
    <tr th:if="${page.empty}">
        <td colspan="2" th:text="#{no_cour_avail}"> No Courses Available </td>
    </tr>
    <span th:text="${deleteMessage}?${deleteMessage}:''"/>
    <tr th:each="course : ${page}">
        <td class="text-center"><span th:text="${course.id}"> Id</span></td>
        <td class="text-center"><span th:text="${course.name}"> Name </span></td>
        <td class="text-center"><span th:text="${course.nameukr}"> Name </span></td>
        <td class="text-center"><span th:text="${course.topic}"> Name </span></td>
        <td class="text-center"><span th:text="${course.topicukr}"> Name </span></td>
        <td class="text-center"><span name="startDate" th:text="${#temporals.format(course.startDate, 'dd-MM-yyyy')}"> Name </span></td>
        <td class="text-center"><span th:text="${course.duration}"> Name </span></td>
        <td class="text-center"><span th:text="${#temporals.format(course.endDate, 'dd-MM-yyyy')}"> Name </span></td>
        <td class="text-center"><span th:text="${course.teacher.username}"> Name </span></td>
        <td class="text-center"><span th:text="${course.getEnrolledStudents().size()}"</td>
        <td class="text-center" th:if="${!course.getEnrolledStudentsId().contains(#request.userPrincipal.principal.getId())}">
            <div th:if="${course.isNotStarted()}">
                <form action="/courses/enroll" method="post">
                    <input type="hidden" name="courseId" th:value="${course.id}">
                    <input type="hidden" name="_csrf" th:value="${_csrf.token}" />
                    <button class="btn btn-success" type="submit" th:text="#{enroll}">Enroll</button>
            </form>
            </div>
        </td>
        <td class="text-center" th:if="${course.getEnrolledStudentsId().contains(#request.userPrincipal.principal.getId())}">
            <div th:if="${course.isNotStarted()}">
            <form action="/courses/unenroll" method="post">
                <input type="hidden" name="courseId" th:value="${course.id}">
                <input type="hidden" name="_csrf" th:value="${_csrf.token}" />
                <button class="btn btn-warning" type="submit" th:text="#{unenroll}">Unenroll</button>
            </form>
            </div>
        </td>

        <td th:if="${#request.userPrincipal.principal.isAdmin()}">
            <div th:if="${!course.isFinished()}">
            <a class="btn btn-dark mx-2" th:href="@{/adminupdate/{id}(id=${course.id})}" th:text="#{edit}">Edit</a>
            </div>
        </td>
        <td th:if="${#request.userPrincipal.principal.isAdmin()}">
            <div th:if="${course.isNotStarted()}">
                <form th:action="@{/adminupdate/delete/{id}(id=${course.id})}" method="post">
                    <input type="hidden" name="_csrf" th:value="${_csrf.token}" />
                    <button type="submit" class="btn btn-danger" th:text="#{delete}">Delete</button>
                </form>
            </div>
        </td>
    </tr>
    </tbody>
</table>

</div>
<div th:include="fragments/footer::footer"></div>
</body>
</html>