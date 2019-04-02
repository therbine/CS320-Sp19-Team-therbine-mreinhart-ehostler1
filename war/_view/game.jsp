<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
		.gamepage {
		color: white;
		background-color: #000000;
  		width: 100%;
 		height: 100%;
  		max-width: 400px;
 		max-height: 400px;
  		padding: 3em;
  		overflow: auto;
		}
		.input{
		color: red;
		}
		td.label {
			text-align: right;
		}
		</style>
	</head>
	<body>
		<div class = "gamepage">
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
		
		<div class = "container">${gameinfo.getGameDisplay()}</div>
		
		<form action="${pageContext.servletContext.contextPath}/Game" method="post">
			
					<div class="Comand">>  
						 <input type="text" name="userInput" size="12" value="" autofocus/></div>
			
		</form>
		</div>
	</body>
</html>