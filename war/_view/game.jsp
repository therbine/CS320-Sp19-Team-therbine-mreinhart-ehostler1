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
		
		td.label {
			text-align: right;
		}
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
		<p id="history"></p>
		<script>
			var i;
			var len = ${gameinfo.getHistorySize()}
			var text =null;
			for(i = 0; i < len; i++){
				text += ${gameinfo.getHistory(i)} + "<br>";
			}
			document.getElementById("history").innerHTML = text;
		
		</script>
		
		<form action="${pageContext.servletContext.contextPath}/Game" method="post">
			<table>
				<tr>
					<td class="Command">Command:</td>
						<td><input type="text" name="userInput" size="12" value="" /></td>
				</tr>
			</table>
		</form>
	</body>
</html>