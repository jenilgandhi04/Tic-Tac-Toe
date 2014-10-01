<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="./main.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TicTakToe</title>
</head>
<p style="margin-left: 40px;"><strong>Tic Tac Toe</strong></p>
<body>
<input type="submit" name="button" value="Logout" onclick="javascript:Logout()">
 <div id="players">
  <div id="X" class="">You : Player O</div>
  <div id="O" class="">AI : Player X</div>
  <input type="submit" name="button" value="New Game" onclick="javascript:newGame()">
  <input type="button" name="button" value="Save Game" onclick="javascript:saveGame()">
</div>
<p style="margin-left: 40px;">Please make your move:</p>

<div style="margin-left: 40px;">
<table border="1" cellpadding="2" cellspacing="2">
	<tbody>
		<tr>
			<td><a id="0" href="javascript:mark(0,0,this)">_</a></td>
			<td><a id="1" href="javascript:mark(1,0,this)">_</a></td>
			<td><a id="2" href="javascript:mark(2,0,this)">_</a></td>
		</tr>
		<tr>
			<td><a id="3" href="javascript:mark(3,0,this)">_</a></td>
			<td><a id="4" href="javascript:mark(4,0,this)">_</a></td>
			<td><a id="5" href="javascript:mark(5,0,this)">_</a></td>
		</tr>
		<tr>
			<td><a id="6" href="javascript:mark(6,0,this)">_</a></td>
			<td><a id="7" href="javascript:mark(7,0,this)">_</a></td>
			<td><a id="8" href="javascript:mark(8,0,this)">_</a></td>
		</tr>
	</tbody>
</table>
</div>
<br/>
<input type="submit" name="button" value="View Saved Games" onclick="javascript:viewSavedGames()">
<input type="submit" name="button" value="View History" onclick="javascript:viewHistory()">
<br/>
<br/>
<input type="submit" name="button" value="Host 2-Player Game" onclick="javascript:hostGame()">
<input type="submit" name="button" value="Join 2-Player Game" onclick="javascript:joinHostedGame()">
<form name="newGameForm" action="./newGame.html" method="post">
<input type="hidden" id="save" name="save" value="NO">
</form>
<script type="text/javascript">
var cells = new Array(-1,-1,-1,-1,-1,-1,-1,-1,-1);
var isGameOver = false;
var isSaved = true;
function mark(indexCell,sign,place){
	if(isGameOver){
		alert('Game Over! Start New Game');
		return;
	}
	if(cells[indexCell] == -1){
		isSaved=false;
		cells[indexCell] = sign;
		if(sign==0){
			document.getElementById(indexCell).innerHTML='O';
			markSignServerSide(indexCell);
		}
		else
			place.innerHTML='X';
	}
}
var xmlhttp = new XMLHttpRequest();
xmlhttp.onreadystatechange=function()
{
if (xmlhttp.readyState==4 && xmlhttp.status==200)
  {
   var result =xmlhttp.responseText;
   var results= result.split('~');
   var index = results[0];
   var msg = results[1];
   mark(index,1,document.getElementById(index));
   if(msg!=null && msg !=''){
	   isGameOver= true;
	   isSaved=true;
	   alert(msg);
   }
  }
};
function markSignServerSide(index){

xmlhttp.open("POST","./playGame.html?cellIndex="+index,false);
xmlhttp.send();
}
function newGame(){

	if(!isGameOver && !isSaved && confirm('This Game is Not Saved,Do want to Save?')){
		document.getElementById("save").value='YES';
	}
	document.newGameForm.submit();	
} 

function  loadGame(){
	var strVale='<%=request.getAttribute("gameBoard")%>';
	 var intValArray=strVale.split(',');
	 for(var i=0;i<intValArray.length;i++){
	     intValArray[i]=parseInt(intValArray[i]);
	     cells[i]=intValArray[i];
	     if(cells[i]==0)
				document.getElementById(i).innerHTML='O';
		 else if(cells[i]==1)
			 document.getElementById(i).innerHTML='X';
	}
}
function Logout(){
	document.newGameForm.action='./logout.html';
	document.newGameForm.submit();
}
var xmlhttpsave = new XMLHttpRequest();
xmlhttpsave.onreadystatechange=function()
{
if (xmlhttpsave.readyState==4 && xmlhttpsave.status==200)
  {
   	  isSaved=true;
	  alert("Game Saved !");
  }
};
function saveGame(){
	if(!isGameOver && !isSaved){
	xmlhttpsave.open("POST","./saveGame.html",false);
	xmlhttpsave.send();
	}
	else{
		alert("Game is already Saved !");
	}
}
function viewSavedGames(){
	document.newGameForm.action='./viewSavedGames.html';
	document.newGameForm.submit();
}
function viewHistory(){
	document.newGameForm.action='./viewHistory.html';
	document.newGameForm.submit();
}
function hostGame(){
	document.newGameForm.action='./newHumanGame.html';
	document.newGameForm.submit();
}
function  joinHostedGame(){
	document.newGameForm.action='./joinHostedGame.html';
	document.newGameForm.submit();
}
<%if(request.getAttribute("gameBoard")!=null){ %>
loadGame();
<%}%>
<%if(request.getAttribute("errMsg")!=null){ %>
alert(request.getAttribute("errMsg"));
<%}%>
</script> 
</html>