package pl.coderslab.warsztat3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class User {
	private long id;
	private String username;
	private String email;
	private String password;
	private int userGroupId;
	
	public User() {
		super();
		this.id = 0l;
		this.username = "";
		this.email = "";
		this.password = "";
		this.userGroupId = 0;
	}
	
	public User(String username, String email, String password) {
		super();
		this.id = 0l;
		this.username = username;
		this.email = email;
		setPassword(password);
		this.userGroupId = 0;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public boolean checkPassword(String password) {
		return BCrypt.checkpw(password, this.password);
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public long getId() {
		return id;
	}
	
	public void save(Connection conn) throws SQLException {
		if(this.id == 0) {
			String sql = "INSERT INTO users(username, email, password, user_group_id) "
					   + "VALUES(?, ?, ?, ?);";
			String[] generatedColumns = {"ID"};
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.username);
			ps.setString(2, this.email);
			ps.setString(3, this.password);
			ps.setInt(4, this.userGroupId);
			ps.executeUpdate();
			ResultSet gk = ps.getGeneratedKeys();
			if(gk.next()) {
				this.id = gk.getLong(1);
			}
			gk.close();
			ps.close();
		} else {
			String sql = "UPDATE users SET username=?, email=?, password=?, user_group_id=? "
					   + "WHERE id=?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.username);
			ps.setString(2, this.email);
			ps.setString(3, this.password);
			ps.setInt(4, this.userGroupId);
			ps.setLong(5, this.id);
			ps.executeUpdate();
			ps.close();
		}
	}
	
	public static User getById(long id) {
		String sql ="";
		// execute sql
		User u = new User();
		return u;
	}
}
