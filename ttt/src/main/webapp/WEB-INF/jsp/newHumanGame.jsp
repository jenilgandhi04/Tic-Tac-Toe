<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="./main.css" type="text/css">
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TicTakToe</title>
</head>
<p style="margin-left: 40px;"><strong>Tic Tac Toe</strong></p>
<body>
<input type="submit" name="button" value="Logout" onclick="javascript:Logout()">
 <div id="players">
 <%if(request.getAttribute("isHost")!=null){ %>
  <div id="X" class="">You : Player O</div>
  <div id="O" class="">Opponent : Player X</div>
  <%}else{ %>
  <div id="X" class="">You : Player X</div>
  <div id="O" class="">Opponent : Player O</div>
  <%} %>
  <input type="submit" name="button" value="New AI Game" onclick="javascript:newGame()">
  <!-- <input type="button" name="button" value="Save Game" onclick="javascript:saveGame()"> -->
</div>
<p style="margin-left: 40px;">Please make your move:</p>

<div style="margin-left: 40px;">
<table border="1" cellpadding="2" cellspacing="2">
	<tbody>
		<tr>
			<td><a id="0" href="javascript:mark(0,this)">_</a></td>
			<td><a id="1" href="javascript:mark(1,this)">_</a></td>
			<td><a id="2" href="javascript:mark(2,this)">_</a></td>
		</tr>
		<tr>
			<td><a id="3" href="javascript:mark(3,this)">_</a></td>
			<td><a id="4" href="javascript:mark(4,this)">_</a></td>
			<td><a id="5" href="javascript:mark(5,this)">_</a></td>
		</tr>
		<tr>
			<td><a id="6" href="javascript:mark(6,this)">_</a></td>
			<td><a id="7" href="javascript:mark(7,this)">_</a></td>
			<td><a id="8" href="javascript:mark(8,this)">_</a></td>
		</tr>
	</tbody>
</table>
</div>
<br/>
<input type="submit" name="button" value="View Saved Games" onclick="javascript:viewSavedGames()">
<input type="submit" name="button" value="View History" onclick="javascript:viewHistory()">
<form name="newGameForm" action="./newGame.html" method="post">
<input type="hidden" id="save" name="save" value="NO">
</form>
<script type="text/javascript">
var cells = new Array(-1,-1,-1,-1,-1,-1,-1,-1,-1);
var isGameOver = false;
var isSaved = true;
var isGameStarted = false;
var isYourTurn = true;
function mark(indexCell,place){
	if(!isGameStarted){
		alert('Waiting for another player to join the game...');
		//isGameStartedCheck();
		return;
	}
	if(isGameOver){
		alert('Game Over! Start New Game');
		return;
	}
	if(!isYourTurn){
		alert('Its Opponents turn now.. Wait for their move..');
		//isMyTurn();
		return;
	}
	if(cells[indexCell] == -1){
		isSaved=false;
		cells[indexCell] = sign;
		document.getElementById(indexCell).innerHTML=symbol;
		markSignServerSide(indexCell);
		isYourTurn =false;
	}
}
var xmlhttp = new XMLHttpRequest();
xmlhttp.onreadystatechange=function()
{
if (xmlhttp.readyState==4 && xmlhttp.status==200)
  {
   var msg =xmlhttp.responseText;
   //var results= result.split('~');
   //var index = results[0];
   //var msg = results[1];
   //mark(index,1,document.getElementById(index));
   if(msg!=null && msg !=''){
	   isGameOver= true;
	   isSaved=true;
	   alert(msg);
	   return;
   }
	isYourTurn=false;
	isMyTurn();
  }
};
function markSignServerSide(index){

xmlhttp.open("POST","./playHumanGame.html?cellIndex="+index,false);
xmlhttp.send();
}
function newGame(){

	/*if(!isGameOver && !isSaved && confirm('This Game is Not Saved,Do want to Save?')){
		document.getElementById("save").value='YES';
	}*/
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
/*function sleep(delay) {
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
  }

var xmlhttphost = new XMLHttpRequest();
xmlhttphost.onreadystatechange=function()
{
if (xmlhttphost.readyState==4 && xmlhttphost.status==200)
  {
	 var result =xmlhttp.responseText;
	 if(result=='YES'){
		 isGameStarted=true;
		 alert('Opponent Has Joined..Start Playing..');
	 }
	 else{
		 alert('NO');
		 sleep(500);
		 isGameStartedCheck();
	 }
  }
};
function isGameStartedCheck(){
	alert('check');
	if(!isGameStarted){
		alert('check1');
		xmlhttphost.open("POST","./userHasJoined.html",false);
		xmlhttphost.send();
	}
}*/
function isMyTurn()
{
    $.ajax({
        url: "isMyTurn.deferred.json",
        success: function( data ) {
        	 //alert(data);
        	 if(data!=null || data!=''){
        		 isSaved=false;
        		 cells[data] = oppSign;
        		 document.getElementById(data).innerHTML=oppSymbol;
        		 //mark(data,1,document.getElementById(data));
        		 isYourTurn=true;
        		 alert('Your Turn..');
        	 }
        	 else{
        		 isYourTurn=false;
        		 isMyTurn();
        		 
        	 }
        },
        error: function (xhr, ajaxOptions, thrownError) {
        	isMyTurn();
          }
    });
}
function isGameStartedCheck()
{
    $.ajax({
        url: "whosonline.deferred.json",
        success: function( data ) {
        	//alert(data);
        	 if(data=='YES'){
        		 isGameStarted=true;
        		 alert('Opponent Has Joined..Start Playing..');
        	 }
        	 else{
        		// alert('NO');
        		// sleep(500);
        		 isGameStartedCheck();
        	 }
        },
        error: function (xhr, ajaxOptions, thrownError) {
        	isGameStartedCheck();
          }
    });
}
<%if(request.getAttribute("isHost")!=null){ %>
alert('Waiting for Opponent to Join Game');
isGameStartedCheck();
isYourTurn = true;
var sign=0;
var oppSign=1;
var symbol='O';
var oppSymbol='X';
<%}else{%>
isGameStarted = true;
isYourTurn = false;
isMyTurn();
var sign=1;
var oppSign=0;
var symbol='X';
var oppSymbol='O';
<%} %>
<%if(request.getAttribute("gameBoard")!=null){ %>
loadGame();
<%}%>
</script> 
</html>