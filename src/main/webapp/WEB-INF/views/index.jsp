<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="parts/head.jsp" %>
  <body id="page-top">
<%@include file="parts/navbar.jsp" %>   
<c:if test="${!empty content}">
  <c:if test="${content!='@policy'}">
    <%@include file="parts/banner.jsp"  %>    
  </c:if>
  <c:choose>
    <c:when test="${content=='@home'}">
	  <%@include file="parts/home/content.jsp"  %>    
    </c:when>    
    <c:when test="${content=='@places'}">
	  <%@include file="parts/places/content.jsp" %>    
    </c:when>    
    <c:when test="${content=='@policy'}">
	  <%@include file="parts/policy/content.jsp" %>    
    </c:when>    
	<c:otherwise>
	  ${content}
	</c:otherwise>
  </c:choose>  	
</c:if>   
<%@include file="parts/footer.jsp" %>
  </body>
</html>
