<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<title>Insert title here</title>
</head>
<body>
<div style="    margin: 0px auto;
    width: 1200px;
    text-align: center;
    padding-top: 100px;">
	<h1 class="h3 mb-3 font-weight-normal">
		Edit
		<c:out value="${sub.getName()}" />
	</h1>

	<p>
		<form:errors path="idea.*" />
	</p>
	<form:form method="POST" action="/subscriptions/${sub.getId()}/edit"
		modelAttribute="sub">
		<p>
			<form:label path="price">Price:</form:label>
			<form:input type="number" path="price" class="form-control" placeholder="${sub.getPrice()}" />
		</p>
		<input type="submit" value="Update" class="btn btn-sm btn-primary " /> <br><br>
	</form:form>
		<c:if test="${sub.getUsers().size()==0}">
		<form:form method="POST" action="/sub/delete/${sub.getId()}">
		
		<input type="submit" value="Delete" class="btn btn-sm btn-primary " />
	</form:form>
		</c:if>
	
	
</body>
</html>