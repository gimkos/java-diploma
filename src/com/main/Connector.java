package com.main;

import java.sql.*;

public class Connector {
	protected String url = "jdbc:mysql://localhost:3306/diploma_scores";
	protected String username = "root";
	protected String password = "";
	protected String sql = "";
	protected Connection connection;

	private String userID;
	
//	gameID
//	1 - Snake
//	2 - BreakOut
//	3 - FlappyBird

	Connector(String userID){
		this.userID = userID;
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {

		}
	}
	
	public boolean isConnected() {
		try (Connection test = DriverManager.getConnection(url, username, password)){
			connection = DriverManager.getConnection(url, username, password);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean isUserExist() {
		try {
			sql = "SELECT `tb_users`.`userID` from `tb_users` WHERE `tb_users`.`userID` = ?";
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, userID);
			ResultSet rs = pstm.executeQuery();
			if(!rs.next()) return false;
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public void createUser(String playerName) {
		try {
			sql = "INSERT INTO `tb_users` (`userID`, `userName`) VALUES (? , ?)";
			
			PreparedStatement pstmI = connection.prepareStatement(sql);
			pstmI.setString(1, userID);
			pstmI.setString(2, playerName);
			
			pstmI.execute();
			
			sql = "INSERT INTO `tb_scores` (`userID`, `gameID`, `score`) VALUES (?, 1, 0), (?, 2, 0), (?, 3, 0)";
		
			pstmI = connection.prepareStatement(sql);
			pstmI.setString(1, userID);
			pstmI.setString(2, userID);
			pstmI.setString(3, userID);
			
			pstmI.execute();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getUserNickname() {
		try {
			String nickname = "";
			
			sql = "SELECT `tb_users`.`userName` FROM `tb_users` WHERE `tb_users`.`userID` = ?";
			
			PreparedStatement pstm = connection.prepareStatement(sql);
			
			pstm.setString(1, userID);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()) {
				nickname = rs.getString("userName");
			}
			return nickname;
		} catch (SQLException e) {
			return null;
		}

		
	}
	
	public Integer getHighestScore(int gameID) {
		Integer score = 0;
		try {
			sql = "SELECT `tb_scores`.`score` FROM `tb_scores`"
					+ "JOIN `tb_users` on `tb_users`.`userID` = `tb_scores`.`userID`"
					+ "JOIN `tb_game` on `tb_game`.`gameID` = `tb_scores`.`gameID`"
					+ "WHERE `tb_scores`.`userID` = ? and `tb_scores`.`gameID` = ?" ;
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, userID);
			pstm.setInt(2, gameID);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				score = rs.getInt("score");
			}
			return score;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return score;
		}
	}
	public Integer getAllScore() {
		Integer score = 0;
		try {
			sql = "SELECT SUM(`tb_scores`.`score`) as score FROM `tb_scores`"
					+ "JOIN `tb_users` on `tb_users`.`userID` = `tb_scores`.`userID`"
					+ "JOIN `tb_game` on `tb_game`.`gameID` = `tb_scores`.`gameID`"
					+ "WHERE `tb_scores`.`userID` = ?"
					+ "GROUP BY `tb_scores`.`userID`" ;
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, userID);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				score = rs.getInt("score");
			}
			return score;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return score;
		}
	}
	public String getUserFromGameRank(int rank, int gameID) {
		String nickname = null;
		try {
			sql = "WITH tb as (SELECT `tb_users`.`userName`, ROW_NUMBER() OVER(ORDER BY `tb_scores`.`score` DESC) as RANK FROM `tb_scores`"
					+ "join `tb_users` on `tb_users`.`userID` = `tb_scores`.`userID`"
					+ "join `tb_game` on `tb_game`.`gameID` = `tb_scores`.`gameID`"
					+ "WHERE `tb_game`.`gameID` = ?)"
					+ "SELECT *"
					+ "FROM tb "
					+ "WHERE RANK = ?";
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setInt(1, gameID);
			pstm.setInt(2, rank);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				nickname = rs.getString("userName");
			}
			return nickname;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return nickname;
	}
	
	public String getUserFromRank(int rank) {
		String nickname = null;
		try {
			sql = "WITH tb as (SELECT `tb_users`.`userName`, ROW_NUMBER() OVER(ORDER BY SUM(`tb_scores`.`score`) DESC) as RANK FROM `tb_scores`"
					+ "join `tb_users` on `tb_users`.`userID` = `tb_scores`.`userID`"
					+ "join `tb_game` on `tb_game`.`gameID` = `tb_scores`.`gameID`"
					+ "WHERE 1 "
					+ "GROUP by `tb_users`.`userID`)"
					+ "SELECT *"
					+ "FROM tb "
					+ "WHERE RANK = ?";
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setInt(1, rank);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				nickname = rs.getString("userName");
			}
			return nickname;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return nickname;
	}
	
	public Integer getRank() {
		
		Integer rank = null;
		try {
			sql = "WITH tb as (SELECT `tb_users`.`userID`, `tb_users`.`userName`, ROW_NUMBER() OVER(ORDER BY SUM(`tb_scores`.`score`) DESC) as RANK "
					+ "FROM `tb_scores` "
					+ "join `tb_users` on `tb_users`.`userID` = `tb_scores`.`userID` "
					+ "join `tb_game` on `tb_game`.`gameID` = `tb_scores`.`gameID` "
					+ "WHERE 1 "
					+ "GROUP by `tb_users`.`userID`) "
					+ "SELECT * FROM tb WHERE userID = ?";
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, userID);;
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				rank = rs.getInt("RANK");
			}
			return (int) rank;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return (int) rank;
	}
	
	public int getRankInGame(int gameID) {
		Integer rank = null;
		try {
			sql = "WITH tb as (SELECT `tb_users`.`userID`, `tb_users`.`userName`, RANK() OVER(ORDER BY `tb_scores`.`score` DESC) as RANK "
					+ "FROM `tb_scores` "
					+ "join `tb_users` on `tb_users`.`userID` = `tb_scores`.`userID` "
					+ "join `tb_game` on `tb_game`.`gameID` = `tb_scores`.`gameID` "
					+ "WHERE `tb_game`.`gameID` = ?) "
					+ "SELECT * FROM tb WHERE `userID` = ?";
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setInt(1, gameID);
			pstm.setString(2, userID);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				rank = rs.getInt("RANK");
			}
			return (int) rank;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return (int) rank;
	}
	public boolean isNewHighScore(int gameID, int score) {
		if(score > getHighestScore(gameID)) {
			try {
				sql = "UPDATE `tb_scores` "
						+ "JOIN `tb_users` on `tb_users`.`userID` = `tb_scores`.`userID` "
						+ "JOIN `tb_game` on `tb_game`.`gameID` = `tb_scores`.`gameID` "
						+ "SET `tb_scores`.`score` = ? "
						+ "WHERE `tb_users`.`userID` = ? and `tb_game`.`gameID` = ?";
				PreparedStatement pstm = connection.prepareStatement(sql);
				pstm.setInt(1, score);
				pstm.setString(2, userID);
				pstm.setInt(3, gameID);
				pstm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
}