<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">
<title>Insert title here</title>
</head>
<body>
	 <h1>Welcome <c:out value="${currentUser.first_name}"></c:out>!</h1>
    <p>This must be your first time or you must be switching subscriptions, since we do not have a subscription registered under your information</p>
    <p>Please choose a subscription and a due date.
    
    <form id="setSubForm" method="POST" action="/signup">
        <label>Due Date:<br>
        	<input name="due" type="number" min="1" max="31"/>
        </label><br>
        <label>Subscription:<br>
        <select name="subid">
        	<c:forEach items="${allsub}" var="sub">
        		<c:if test="${sub.isAvailability()}">
        			<option value ="${sub.getId()}">
        				<c:out value="${sub.getName()}"/>(<c:out value="${sub.getPrice()}"/>)
        			</option>
        		</c:if>
        		
        	</c:forEach>
        </select>
        
     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit"/>
    </form>
</body>
</html>