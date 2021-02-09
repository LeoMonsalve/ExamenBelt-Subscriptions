<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div class="mb-3">
	<h1>Welcome <c:out value="${currentUser.getFirst_name()}" /> <c:out value="${currentUser.getLast_name()}" />!</h1>
    <p><b>Current Subscription: </b> <c:out value="${currentUser.getSubscription().getName()}"></c:out></p>
  
    <p><b>Amount Due ($): </b> <c:out value="${currentUser.getSubscription().getPrice()}0"></c:out></p>
   
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    
    </div>


</body>
</html>