<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Proceed with Caution</title>
		<style type="text/css">
		.error {
			color: red;
		}
		
		td.label {
			text-align: right;
		}
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
	
		<form action="${pageContext.servletContext.contextPath}/SignInPage" method="post">
			<table>
				<tr>
					<td class="label">Username:</td>
					<td><input type="text" name="first" size="12" value="${username}" /></td>
				</tr>
				<tr>
					<td class="label">Password:</td>
					<td><input type="text" name="second" size="12" value="${password}" /></td>
				</tr>
			</table>
			<input type="Submit" name="submit" value="Sign In">
			<input type="Submit" name="submit" value="Creat Account">
			<input type="Submit" name="submit" value="Delete Account">
		</form>
	</body>
</html>