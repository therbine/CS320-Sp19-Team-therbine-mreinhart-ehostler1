<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Proceed with Caution</title>
		<link rel="stylesheet" type = "text/ css" href="signInPagestyle.css">
		<style type="text/css">
		.error {
			color: red;
		}
		
		form {
			position: absolute;
			top: 10%;
    		left: 50%;
			transform: translate(-50%,0%);
			
		}
		h1 {
			position: absolute;
			
    		left: 50%;
			transform: translate(-50%,0%);
			
		}
		</style>
		
		
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
		<div id="Sub_head">
		<h1> Please Sign in or Create a new Account</h1>
		</div>
		
		<form action="${pageContext.servletContext.contextPath}/SignIn" method="post">
		
			<table>
				<tr>
					<td class="label">Username:</td>
					<td><input type="text" name="username" size="12" value="${userinfo.givenUsername}" /></td>
				</tr>
				
				<tr>
					<td class="label">Password:</td>
					<td><input type="password" name="password" size="12" value="${userinfo.givenPassword}" /></td>
				</tr>
			</table>
			<input type="submit" name="signInRequest" value="Sign In">
			<input type="submit" name="createAccountRequest" value="Create Account">
			<input type="submit" name="deleteAccountRequest" value="Delete Account">
			
			
		</form>
	</body>
</html>