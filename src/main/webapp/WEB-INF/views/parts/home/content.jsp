<%@ page pageEncoding="UTF-8" %>
<c:forEach items="${info}" var="entry">
    ${entry.key}: ${entry.value}<br />
</c:forEach>