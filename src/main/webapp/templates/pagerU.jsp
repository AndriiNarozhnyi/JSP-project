<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ page import="java.util.*, java.text.*" %>
<%@ page import="org.itstep.model.entity.Role" %>
<%@ page import="org.itstep.model.dao.UserPage" %>
<%@ page import="org.itstep.model.dao.CoursePage" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="res"/>
<fmt:setLocale value="${param.lang}"/>
<%UserPage pager = (UserPage) request.getAttribute("page");%>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
    <div class="mt-3">
        <div class="row justify-content-md-left">
            <div class="col col-lg-3">
                <ul class="pagination">
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1"><fmt:message key="Pages" /></a>
                    </li>
                    <% for(int i=1; i<=pager.getTotalPages();i++){%>
                            <% if(i==pager.getPageNumber()) {%>
                            <li class="page-item disabled">
                                <a class="page-link" href="#" tabindex="-1"><%=i%></a>
                            </li>
                            <%}%>
                        <% if(i!=pager.getPageNumber()) {%>
                            <li class="page-item active">
                                <% if (String.valueOf(request.getAttribute("url")).contains("?")){%>
                                <a class="page-link" href=<%=request.getAttribute("url") + "&page="+i+"&size="+pager.getSize()%> tabindex="-1"><%=i%></a>
                                <%} else {%>
                                <a class="page-link" href=<%=request.getAttribute("url") + "?page="+i+"&size="+pager.getSize()%> tabindex="-1"><%=i%></a>
                                <%}%>
                            </li>
                        <%}%>
                    <%}%>
                </ul>
            </div>
            <div class="col col-lg-4">
                <ul class="pagination">
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1"><fmt:message key="ElPage" /></a>
                    </li>
                    <% for (int c=3; c<=12;c=c+3){%>
                    <% if(c==pager.getSize()){%>
                            <li class="page-item disabled">
                                <a class="page-link" href="#" tabindex="-1" ><%=c%></a>
                            </li>
                    <%}%>
                    <% if(c!=pager.getSize()){%>
                            <li class="page-item active">
                                <% if (String.valueOf(request.getAttribute("url")).contains("?")){%>
                                <a class="page-link" href=<%=request.getAttribute("url") + "&page="+pager.getPageNumber()+"&size="+c+"&totRows="+pager.getTotalRows()%> tabindex="-1" ><%=c%></a>
                                <%} else {%>
                                <a class="page-link" href=<%=request.getAttribute("url") + "?page="+pager.getPageNumber()+"&size="+c+"&totRows="+pager.getTotalRows()%> tabindex="-1" ><%=c%></a>
                                <%}%>
                            </li>
                    <%}%>
                    <%}%>
                </ul>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>

</body>
</html>