package net_finding.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import net_finding.model.Student;

public class DAO {
	private String url = "jdbc:mysql://localhost:3306/student_managment";
	private Connection connection;

	public DAO() throws SQLException {
		getConnection();
	}

	public void getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, "root", "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean findUsername(String username) throws SQLException {
		String sql = "SELECT username FROM user WHERE username = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			return true;
		return false;
	}

	public boolean checkLogin(String username, String password) throws SQLException {
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			rs.close();
			ps.close();
			return true;
		}
		rs.close();
		ps.close();
		return false;
	}

	public List<Student> findByScore(double score) throws SQLException {
		String sql = "SELECT * FROM student WHERE score >= ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setDouble(1, score);
		List<Student> students = new ArrayList<>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Student student = new Student();
			student.setId(rs.getString("id_student"));
			student.setName(rs.getString("name"));
			student.setAge(rs.getInt("age"));
			student.setScore(rs.getDouble("score"));
			students.add(student);
		}
		rs.close();
		ps.close();
		return students;
	}

	public List<Student> findByAge(int age) throws SQLException {
		String sql = "SELECT * FROM student WHERE age = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setDouble(1, age);
		List<Student> students = new ArrayList<>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Student student = new Student();
			student.setId(rs.getString("id_student"));
			student.setName(rs.getString("name"));
			student.setAge(rs.getInt("age"));
			student.setScore(rs.getDouble("score"));
			students.add(student);
		}
		rs.close();
		ps.close();
		return students;
	}

	public List<Student> findByName(String name) throws SQLException {
		String sql = "SELECT * FROM student WHERE name LIKE ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, "%" + name);
		List<Student> students = new ArrayList<>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Student student = new Student();
			student.setId(rs.getString("id_student"));
			student.setName(rs.getString("name"));
			student.setAge(rs.getInt("age"));
			student.setScore(rs.getDouble("score"));
			students.add(student);
		}
		rs.close();
		ps.close();
		return students;
	}

	public List<Student> findById(String id) throws SQLException {
		String sql = "SELECT * FROM student WHERE id_student = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, id);
		List<Student> students = new ArrayList<>();
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Student student = new Student();
			student.setId(rs.getString("id_student"));
			student.setName(rs.getString("name"));
			student.setAge(rs.getInt("age"));
			student.setScore(rs.getDouble("score"));
			students.add(student);
		}
		rs.close();
		ps.close();
		return students;
	}

	public boolean updateScore(String id, double score) throws SQLException {
		String sql = "SELECT * FROM student WHERE id_student = ?";
		PreparedStatement ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			rs.updateDouble("score", score);
			rs.updateRow();
			rs.close();
			ps.close();
			return true;
		}
		rs.close();
		ps.close();
		return false;
	}
}
