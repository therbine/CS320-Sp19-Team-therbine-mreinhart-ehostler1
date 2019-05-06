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
		.PopUp{
		font-size: 200%;
		background-color: #999999;
		width: 500px;
  		height: 300px;
		position: absolute;
  		top: 30%;
 		left: 35%;
  		transform: translate;
  		-ms-transform: translate;
		}
		.gamepage {
		color: white;
		background-color: #000000;
  		width: 100%;
  		height: 87%;
  		max-width: 400px;
  		padding: 3em;
  		overflow: auto;
  		position: absolute;
   		top:  50%;
    	left: 50%;
    	transform: translate(-50%,-50%);
		}
		.score{
		
		}
		.map{
		position: absolute;
  		top: 5%;
 		right: 5%;
  		transform: translate;
  		-ms-transform: translate;
  		
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
		.game_buttons{
		position: absolute;
  		top: 5%;
 		left: 5%;
  		transform: translate;
  		-ms-transform: translate;
		}
		.start_button{
		position: absolute;
  		top: 50%;
 		left: 50%;
  		transform: translate(-50%, -50%);
  		-ms-transform: translate(-50%, -50%);
		}
		
		}
		
		
		</style> 
	</head>
	<body>
		
		<img src="http://public.media.smithsonianmag.com/legacy_blog/ships-mermaid-99.85.jpg" alt="backround" width="100%" height = "75%">
		
		<c:if test="${empty Start}">
			<div class = "start_button">
			
				<form action="${pageContext.servletContext.contextPath}/Game" method="post">
					<button name = "Start" type = "submit">Begin/Continue Game</button>
				</form>
				
			</div>
		</c:if>
		
		<c:if test="${!empty Start}">
			
			<div class = "game_buttons">
				<c:if test="${empty Restart}">
					<form action="${pageContext.servletContext.contextPath}/Game" method="post">
						<button name = "Save" type = "submit" value = "saved">Save Game</button>
					</form>
					<form action="${pageContext.servletContext.contextPath}/Game" method="post">  
						<button name = "Restart" type = "submit"> Restart</button>
					
					</form>
				</c:if>
			</div>
			
			
			
			<div class = "gamepage">
			
			<div class = "score"> Score:${score}</div>
			
			
			
			<!-- the display of user input and server output -->
			<div class = "container">${gameinfo.getGameDisplay()}</div>
				
				<form action="${pageContext.servletContext.contextPath}/Game" method="post">
					<div class="Comand">>	
						<input type="text" name="userInput" size="12" value="" autofocus/>
					</div>
				</form>
			</div>
			<div class = "map">
			<c:if test="${! empty map}">
				
				<form action="${pageContext.servletContext.contextPath}/Game" method="post">
  					<button name = "move" type = "submit" value = "north">North</button>
  					<button name = "move" type = "submit" value = "south">South</button>
  					<button name = "move" type = "submit" value = "east">East</button>
  					<button name = "move" type = "submit" value = "west">West</button>
  				</form>
				
				<!-- 3X3 map which WILL show the players position  -->
			
			<%
			Integer location_x = (Integer)request.getAttribute("player_x");
			Integer location_y = (Integer)request.getAttribute("player_y");
			%>
			
			
			
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
		</c:if>
		<form action="${pageContext.servletContext.contextPath}/Game" method="post">
			<c:if test="${!empty Restart}">
				<div class = PopUp>Are you sure you want to restart?
				<br>
				<br>
				<br>
				<br>
					<div><button name = "Yes" type = "submit" style="height:100px;width:200px;float:left">Yes</button>
					
					<button name = "No" type = "submit" style="height:100px;width:200px;float:right">No</button></div>
				</div>
			</c:if>
		</form>	 
	</body>
	
</html>