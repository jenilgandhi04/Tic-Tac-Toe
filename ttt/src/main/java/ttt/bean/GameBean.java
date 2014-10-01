package ttt.bean;

import java.util.Date;

import org.springframework.web.context.request.async.DeferredResult;



public class GameBean {
	private int gameId=-1;
	private GameBoard board;
	private Player player1;
	private Player player2;
	private Player turn;
	private Player Winner;
	private boolean isGameOver;
	private Date startTime;
	private Date endTime;
	private Date lastSaveTime;
	private Double gameLength;
	private DeferredResult deferredResult;
	private final Object lock = new Object();
	private int lastMarkIndex=-1;
	
	public GameBean() {
		gameId=-1;
		board = new GameBoard();
		player1 = new Player();
		player1.setPlayerSign(0);
		player2 = new Player();
		player2.setPlayerSign(1);
		turn = player1;
		Winner = null;
		isGameOver = false;
		startTime = new Date();
	}

	public boolean markSign(Player player, int cellIndex) {
		if (turn != player)
			return false;
		if (cellIndex < 0) {
			getWinner();
			return false;
		}
		int[] cells = board.getCells();
		if (cells[cellIndex] != -1) {
			return false;
		}
		cells[cellIndex] = player.getPlayerSign();
		board.setCells(cells);
		lastMarkIndex=cellIndex;
		toggleTurn();
		return true;
	}

	private void toggleTurn() {
		if (turn == player1)
			turn = player2;
		else
			turn = player1;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getLastSaveTime() {
		return lastSaveTime;
	}

	public void setLastSaveTime(Date lastSaveTime) {
		this.lastSaveTime = lastSaveTime;
	}

	public Double getGameLength() {
		return gameLength;
	}

	public void setGameLength(Double gameLength) {
		this.gameLength = gameLength;
	}

	public boolean isWinner(int[] cells, Player player) {
		for (int i = 0; i < 3; i++) {
			// Check for vertical
			if (cells[i] == cells[i + 3] && cells[i + 3] == cells[i + 6]
					&& cells[i + 6] == player.getPlayerSign())
				return true;

			// Check for horizontal
			else if (cells[(i * 3)] == cells[((i * 3) + 1)]
					&& cells[((i * 3) + 1)] == cells[((i * 3) + 2)]
					&& cells[((i * 3) + 2)] == player.getPlayerSign())
				return true;

		}
		if (cells[0] == cells[4] && cells[4] == cells[8]
				&& cells[8] == player.getPlayerSign())
			return true;
		else if (cells[2] == cells[4] && cells[4] == cells[6]
				&& cells[6] == player.getPlayerSign())
			return true;

		return false;
	}

	public int getBestMove(Player player) {
		int tempCells[] = this.getBoard().getCells().clone();
		// find place where player can win
		for (int i = 0; i < 9; i++) {
			if (tempCells[i] == -1) {
				tempCells[i] = player.getPlayerSign();
				if (this.isWinner(tempCells, player))
					return i;
				tempCells = this.getBoard().getCells().clone();
			}
		}
		// find place where opponent can win
		tempCells = this.getBoard().getCells().clone();
		for (int i = 0; i < 9; i++) {
			if (tempCells[i] == -1) {
				tempCells[i] = player1.getPlayerSign();
				if (this.isWinner(tempCells, player1))
					return i;
				tempCells = this.getBoard().getCells().clone();
			}
		}
		// any empty place
		for (int i = 0; i < 9; i++) {
			if (tempCells[i] == -1) {
				return i;
			}
		}
		return -1;
	}

	public GameBoard getBoard() {
		return board;
	}

	public void setBoard(GameBoard board) {
		this.board = board;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Player getTurn() {
		return turn;
	}

	public void setTurn(Player turn) {
		this.turn = turn;
	}

	public Player getWinner() {
		if (Winner == null) {
			if (isWinner(getBoard().getCells(), player1))
				Winner = player1;
			else if (isWinner(getBoard().getCells(), player2))
				Winner = player2;
			else {
				int tempCells[] = this.getBoard().getCells();
				for (int i = 0; i < 9; i++) {
					if (tempCells[i] == -1) {
						return null;
					}
				}
				// match drawn
				Player player = new Player();
				player.setPlayerSign(-1);
				Winner = player;
			}
		}
		return Winner;
	}

	public void setWinner(Player winner) {
		Winner = winner;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String isGameStarted(DeferredResult deferredResult) {
		synchronized(lock) {
			if (this.getPlayer1()==null || this.getPlayer1().getPlayerName()==null ||this.getPlayer1().getPlayerName().isEmpty()) {
				this.deferredResult = deferredResult;
				return null;
			}
			else if (this.getPlayer2()==null || this.getPlayer2().getPlayerName()==null ||this.getPlayer2().getPlayerName().isEmpty()) {
				this.deferredResult = deferredResult;
				return null;
			}
			else {
				return "YES";
			}
		}
	}
	public String isMyTurn(DeferredResult deferredResult,String userName) {
		synchronized(lock) {
			if (this.getTurn()==null || this.getTurn().getPlayerName()==null ||(!this.getTurn().getPlayerName().equals(userName))) {
				this.deferredResult = deferredResult;
				return null;
			}
			else {
				return ""+lastMarkIndex;
			}
		}
	}
}
