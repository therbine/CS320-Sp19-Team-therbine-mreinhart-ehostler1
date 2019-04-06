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
  		padding: 3em;
  		overflow: auto;
  		position: absolute;
   		top:  50%;
    	left: 50%;
    	transform: translate(-50%,-50%);
		}
		.map{
		
		float: right;
		position: relative;
		}
		table, th, td{
		color: #000000;
		border: 1px solid black;
		background-color: #E6E6FA;
		width: 400px;
		height: 200px;
		}
		
		
		</style> 
	</head>
	<body>
	`	
		<div class = "gamepage">
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
		<!-- the display of user input and server output -->
		<div class = "container">${gameinfo.getGameDisplay()}</div>
		
		<form action="${pageContext.servletContext.contextPath}/Game" method="post">
			
					<div class="Comand">>  
						 <input type="text" name="userInput" size="12" value="" autofocus/></div>
			
		</form>
		</div>
		<!-- 3X3 map which WILL show the players position  -->
		<div class = "map">
			<div class = "player_location">${gameinfo.getplayerlocation()}</div>
			<table>
				
				
				<tr>
					<%if (request.getParameter("player_location") == null) {%>	
					<%if(1 > 0){%>
						<td class = "location_topleft"> </td>
					<%}
					else{%>
						<td class = "location_topleft"> </td>
					<%}%>
					<%if(1 > 0){%>
						<td class = "location_topleft"> </td>
					<%}
					else{%>
						<td class = "location_topleft"> </td>
					<%}
					if(1 > 0){%>
						<td class = "location_topleft"> </td>
					<%}
					else{%>
						<td class = "location_topleft"> </td>
					<%}%>
					<!--  
					<td class = "location_topleft"> </td>
					<td class = "location_topcenter"> </td>
					<td class = "location_topright"> </td>
					 -->
				</tr>
				<tr>
					<%if(1 > 0){%>
						<td class = "location_topleft"> </td>
					<%}
					else{%>
						<td class = "location_topleft"> </td>
					<%}%>
					<%if(1 > 0){%>
						<td class = "location_topleft"> </td>
					<%}
					else{%>
						<td class = "location_topleft"> </td>
					<%}%>
					<%if(1 > 0){%>
						<td class = "location_topleft"> </td>
					<%}
					else{%>
						<td class = "location_topleft"> </td>
					<%}%>
					<!--  
					<td class = "location_midleft"> </td>
					<td class = "location_midcenter"> </td>
					<td class = "location_midright"> </td>
					-->
				</tr>
				<tr>
					<%if(1 > 0){%>
						<td class = "location_topleft"> </td>
					<%}
					else{%>
						<td class = "location_topleft"> </td>
					<%}%>
					<%if(1 > 0){%>
						<td class = "location_topleft"> </td>
					<%}
					else{%>
						<td class = "location_topleft"> </td>
					<%}%>
					<%if(1 > 0){%>
						<td class = "location_topleft"> </td>
					<%}
					else{%>
						<td class = "location_topleft"> </td>
					<%}
					}%>
					<!--  
					<td class = "location_bottomleft"> </td>
					<td class = "location_bottomcenter"> </td>
					<td class = "location_bottomright"> </td>
					-->
				</tr>
			
			</table>
		
		</div>
	</body>
	
</html>