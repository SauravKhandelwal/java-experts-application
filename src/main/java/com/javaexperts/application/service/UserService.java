package com.javaexperts.application.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.javaexperts.application.dto.UserTO;
import com.javaexperts.application.util.JDBCUtil;

public class UserService {
	public int registerUser(UserTO uto) {
		int x = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = JDBCUtil.getMYSQLConncetion();
			//String sql = "insert into jlcusers values(?,?,?,?,?,?)";
			String sql="insert into Users values(?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, uto.getUserid());
			ps.setString(2, uto.getFname());
			ps.setString(3, uto.getEmail());
			ps.setLong(4, uto.getPhone());
			ps.setString(5, uto.getUsername());
			ps.setString(6, uto.getPassword());
			x = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			JDBCUtil.cleanup(ps, con);
		}
		return x;

	}

	public int getNextUserId() {
		int x = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getMYSQLConncetion();
			String sql = "select max(userid)from Users";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
				System.out.println("The Value is :" + x);
				x = x + 1;

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtil.cleanup(rs, ps, con);
		}
		return x;

	}

	public int verfiyUser(String un, String pw) {
		System.out.println("verfiyUser");
		int x = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = JDBCUtil.getMYSQLConncetion();
			String sql = "select * from Users where username=? and password=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, un);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			if (rs.next()) {
				x = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			//resource closing .
			JDBCUtil.cleanup(rs, ps, con);
		}
		return x;
	}

	public int deleteuser(String un, String pw) {
		System.out.println("delete user () called");
		int x = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			con = JDBCUtil.getMYSQLConncetion();
			String sql = "select * from Users where username=? and password=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, un);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			System.out.println("Here after result set"+rs);
			if (rs.next()) {
				System.out.println("inner called");
				//con = JDBCUtil.getMYSQLConncetion();
				String sql1 = "delete from Users where username=?";
				ps = con.prepareStatement(sql1);
				ps.setString(1, un);
				ps.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.cleanup(rs, ps, con);
		}
		return x;

	}
}
