<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
	<!--comment -->
		<title>Proceed with Caution</title>
		<style type="text/css">
		.error {
			color: red;
		}
		body{
		overflow: hidden;
		}
		.gamepage {
		color: white;
		background-color: #000000;
  		width: 100%;
  		height: 90%;
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
		.otherlocations{
		color: #000000;
		border: 1px solid black;
		background-color: #E6E6FA;
		width: 100px;
		height: 100px;
		}
		.locationofplayer{
		color: #000000;
		border: 1px solid black;
		background-color: #FF0000;
		width: 100px;
		height: 100px;
		}
		
		
		</style> 
	</head>
	<body>
		
		<div class = "gamepage">
		
		<!-- the display of user input and server output -->
		<div class = "container">${gameinfo.getGameDisplay()}</div>
		
		<form action="${pageContext.servletContext.contextPath}/Game" method="post">
			
					<div class="Comand">>  
						 <input type="text" name="userInput" size="12" value="" autofocus/></div>
			
		</form>
		</div>
		<!-- 3X3 map which WILL show the players position  -->
		<div class = "map">
			<%
			Integer location_x = (Integer)request.getAttribute("player_x");
			Integer location_y = (Integer)request.getAttribute("player_y");
			%>
			<div class="btn-group">
  			<button name = "move" type = "submit" value = "north">North</button>
  			<button name = "move" type = "submit" value = "south">South</button>
  			<button name = "move" type = "submit" value = "east">East</button>
  			<button name = "move" type = "submit" value = "west">West</button>
			</div>
			
			<c:if test="${! empty map}">
				<table>
					
					<%
					for(Integer y = 2; y >= 0; y--){
						%><tr><%
						for(Integer x = 0; x <= 2; x++){
							if(location_x == x && location_y == y){
							%>
								<td class = "locationofplayer"> </td>
							<%
							}
							else{
							%>
								<td class = "otherlocations"> </td>
								
							<%
						}
					}
					%></tr><%
				}%>
			
			</table>
			</c:if>
		</div>
	</body>
	
</html>