<html>
<body>
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
            <td><a href="<%=request.getAttribute("resetLink")%>" class="btn btn-warning"><fmt:message key="resetFilter"/></a> </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>
