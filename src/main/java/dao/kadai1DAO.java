package dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.kadai1dto;
import util.kadai1HashedPw;
import util.kadai1Salt;

public class kadai1DAO {
	private static final String URL = "psql:mysql://localhost:3306/kadai1?serverTimezone=Asia/Tokyo";
	private static final String USER = "AppUser";
	private static final String PW = "morijyobi";

	private static void loadDriver() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static Connection getConnection() throws URISyntaxException, SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

	    return DriverManager.getConnection(dbUrl, username, password);
	}
	
	public static int registerkadai2(kadai1dto kadai1) {
		String sql = "INSERT INTO kadai1 VALUES(default, ?, ?, ?, ?, ?, ?, ?, current_timestamp)";
		int result = 0;
		
		String salt = kadai1Salt.getSalt(32);		
		
		String hashedPw = kadai1HashedPw.getSafetyPassword(kadai1.getPassword(), salt);
		
		System.out.println("生成されたソルト：" + salt);
		System.out.println("生成されたハッシュ値：" + hashedPw);
		
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, kadai1.getName());
			pstmt.setInt(2, kadai1.getAge());
			pstmt.setString(3, kadai1.getGender());
			pstmt.setString(4, kadai1.getPhone());
			pstmt.setString(5, kadai1.getMail());
			pstmt.setString(6, salt);
			pstmt.setString(7, hashedPw);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			System.out.println(result + "件更新しました。");
		}
		return result;
	}
	public static List<kadai1dto> selectAllkadai2() {
		loadDriver();
		
		List<kadai1dto> result = new ArrayList<>();

		String sql = "SELECT * FROM kadai1";
		
		try (
				Connection con = DriverManager.getConnection(URL, USER, PW);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			try (ResultSet kadai2 = pstmt.executeQuery()){
				while(kadai2.next()) {
					int id = kadai2.getInt("id");
					String name = kadai2.getString("name");
					int age = kadai2.getInt("age");
					String gender = kadai2.getString("gender");
					String phone = kadai2.getString("phone");
					String mail = kadai2.getString("mail");
					String salt = kadai2.getString("salt");
					String password = kadai2.getString("password");
					String hashedPassword = kadai2.getString("hashedPassword");

					kadai1dto employee = new kadai1dto(id, name, age, gender, phone, mail, salt, password, hashedPassword);
					
					result.add(employee);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getSalt(String mail) {
		
		String sql = "SELECT salt FROM kadai1 WHERE mail = ?";
		
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, mail);

			try (ResultSet rs = pstmt.executeQuery()){
				
				if(rs.next()) {
					String salt = rs.getString("salt");
					return salt;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static kadai1dto login(String mail, String hashedPw) {
		String sql = "SELECT * FROM kadai1 WHERE mail = ? AND password = ?";
		
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, mail);
			pstmt.setString(2, hashedPw);

			try (ResultSet kadai1 = pstmt.executeQuery()){
				
				if(kadai1.next()) {
					int id = kadai1.getInt("id");
					String name = kadai1.getString("name");
					int age = kadai1.getInt("age");
					String gender = kadai1.getString("gender");
					String phone = kadai1.getString("phone");
					String salt = kadai1.getString("salt");
					String ceratedAt = kadai1.getString("created_at");
					
					return new kadai1dto(id, name, age, gender, phone, mail, salt, null, null);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
