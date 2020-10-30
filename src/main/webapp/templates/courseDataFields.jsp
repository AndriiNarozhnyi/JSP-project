
<td class="text-center"><span><% out.print(course.getName());%></span></td>
<td class="text-center"><span><% out.print(course.getNameukr());%></span></td>
<td class="text-center"><span><% out.print(course.getTopic());%></span></td>
<td class="text-center"><span><% out.print(course.getTopicukr());%></span></td>
<td class="text-center"><span><% out.print(course.getStartDate().format(formatter));%></span></td>
<td class="text-center"><span><% out.print(course.getDuration());%></span></td>
<td class="text-center"><span><% out.print(course.getEndDate().format(formatter));%></span></td>
<td class="text-center"><span><% out.print(course.getTeacher().getUsername());%></span></td>
<td class="text-center"><span><% out.print(course.getEnrolledStudents().size());%></span></td>

