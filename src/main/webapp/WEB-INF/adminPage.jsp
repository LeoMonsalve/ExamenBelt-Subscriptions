<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>
		Bienvenido
		<c:out value="${currentUser.first_name}"></c:out>
		!
	</h1>
	<h3>Clientes</h3>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Next Due Date</th>
				<th>Amount Due</th>
				<th>Subscription Type</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${all}" var="customer">
				<c:if test="${customer.getId()!= 1}">
					<tr>
						<td><c:out value="${customer.getFirst_name()}" /> <c:out
								value="${customer.getLast_name()}" /></td>
						<td><fmt:formatDate pattern = "EEEEEE,  dd 'de' MMMM"  value="${customer.getDueDate()}"/></td>
						<td><c:out value="${customer.getSubscription().getPrice()}"/></td>
						<td><c:out value="${customer.getSubscription().getName()}"/></td>						
						
					</tr>
				</c:if>

			</c:forEach>
		</tbody>


	</table >
	<br>
	<h3>Subscriptions</h3>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Cost</th>
				<th>Available</th>
				<th>Users</th>
				<th>Actions</th>
			</tr>

		</thead>
		<tbody>
			<c:forEach items="${allsubs}" var="sub">

				<tr>
					<td><c:out value="${sub.getName()}" /></td>
					<td><c:out value="${sub.getPrice()}" /></td>
					<c:if test="${sub.isAvailability()}">
						<td>Available</td>
					</c:if>
					<c:if test="${!sub.isAvailability()}">
						<td>Not Available</td>
					</c:if>
					<td><c:out value="${sub.getUsers().size()}" /></td>
					<c:if test="${sub.isAvailability()}">

						<c:if test="${sub.getUsers().size()==0}">
	
							<td><a href="/sub/deactivate/${sub.getId()}">Deactivate</a> || <a href="subscriptions/${sub.getId()}/edit">Edit</a></td>
						
						</c:if>
						<c:if test="${sub.getUsers().size()!=0}">
						<td>Desactivate|| <a href="/subscriptions/${sub.getId()}/edit">Edit</a></td>
						</c:if>
					</c:if>
					<c:if test="${!sub.isAvailability()}">
						<td><a href="/sub/activate/${sub.getId()}">Activate</a>|| <a href="/subscriptions/${sub.getId()}/edit">Edit</a></td> 
					</c:if>
				</tr>


			</c:forEach>

		</tbody>



	</table>
	<br>

	<h3>Make Subscription Package</h3>
	<form:form method="POST" modelAttribute="subscription"
		action="/createsub">
		<form:label path="name">Package Name:
    			<form:input path="name" type="text" />
		</form:label>
		<br>
		<form:label path="name">Package Cost:
    			<form:input path="price" type="number" />
		</form:label>
		<br>
		<input type="submit" />
	</form:form>
	<br>
	<form id="logoutForm" method="POST" action="/logout">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="submit" value="Logout!" />
	</form>
</body>
</html>