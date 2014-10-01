package ttt.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "game")
public class Game {
	 @Id
	 @GeneratedValue
	 int id; 
	 @ManyToOne
	    @JoinColumn(name="player1")
	    private User player1;
	 @ManyToOne
	    @JoinColumn(name="player2")
	    private User player2;
	 String gameboard;
	 String gamestatus;
	 Date started;
	 Date ended;
	 Date saved;
	 @ManyToOne
	    @JoinColumn(name="winner")
	    private User winner;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getPlayer1() {
		return player1;
	}
	public void setPlayer1(User player1) {
		this.player1 = player1;
	}
	public User getPlayer2() {
		return player2;
	}
	public void setPlayer2(User player2) {
		this.player2 = player2;
	}
	public String getGameboard() {
		return gameboard;
	}
	public void setGameboard(String gameboard) {
		this.gameboard = gameboard;
	}
	public String getGamestatus() {
		return gamestatus;
	}
	public void setGamestatus(String gamestatus) {
		this.gamestatus = gamestatus;
	}
	public User getWinner() {
		return winner;
	}
	public void setWinner(User winner) {
		this.winner = winner;
	}
	public Date getStarted() {
		return started;
	}
	public void setStarted(Date started) {
		this.started = started;
	}
	public Date getEnded() {
		return ended;
	}
	public void setEnded(Date ended) {
		this.ended = ended;
	}
	public Date getSaved() {
		return saved;
	}
	public void setSaved(Date saved) {
		this.saved = saved;
	}
	
}
