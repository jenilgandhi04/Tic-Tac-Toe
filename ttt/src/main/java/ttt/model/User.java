	package ttt.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "users")
public class User {
	
	public User(){

	}

    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private String password;

    private String email;
    
    private boolean enabled = true;
    
    @OneToMany(mappedBy="player1")
    private Set<Game> gamesAsPlayer1;
    @OneToMany(mappedBy="player2")
    private Set<Game> gamesAsPlayer2;
    @OneToMany(mappedBy="winner")
    private Set<Game> gamesWon;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Set<Game> getGamesAsPlayer1() {
		return gamesAsPlayer1;
	}
	public void setGamesAsPlayer1(Set<Game> gamesAsPlayer1) {
		this.gamesAsPlayer1 = gamesAsPlayer1;
	}
	public Set<Game> getGamesAsPlayer2() {
		return gamesAsPlayer2;
	}
	public void setGamesAsPlayer2(Set<Game> gamesAsPlayer2) {
		this.gamesAsPlayer2 = gamesAsPlayer2;
	}
	public Set<Game> getGamesWon() {
		return gamesWon;
	}
	public void setGamesWon(Set<Game> gamesWon) {
		this.gamesWon = gamesWon;
	}
	
}