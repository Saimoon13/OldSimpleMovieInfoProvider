package edu.java.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import static edu.java.project.OracleQuery.*;

import oracle.jdbc.OracleDriver;

public class ProjectDAOImple implements ProjectDAO {

	private static ProjectDAOImple instance = null;
	private JFrame frame;
	
	private ProjectDAOImple() {
		
		try {
			DriverManager.registerDriver(new OracleDriver());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ProjectDAOImple getInstance() {
		if(instance == null) {
			instance = new ProjectDAOImple();
		}
		return instance;
	}

	@Override
	public int insert(Member m) { // conn, pstmt
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_MEMBER);
			pstmt.setString(1, m.getMname());
			pstmt.setString(2, m.getMemail());

			StringBuffer sb = new StringBuffer();
			for(char g:m.getMpw()) {
				sb.append(g);
			}
			String temp = sb.toString();
			pstmt.setString(3, temp);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {			
				e.printStackTrace();
			}
			
		}
		
		return result;
	} // conn, pstmt

	@Override
	public List<Rate> select(GUI window) {
		
		List<Rate> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_ALL);
			pstmt.setString(1, window.getDbtitleMain());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String crn = rs.getString("crn");
				String crc = rs.getString("crc");
				Rate r = new Rate(crn,crc);
				list.add(r);
			} // end while()
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
	} // end select

	@Override
	public int insertRate(Rate r) {
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = conn.prepareStatement(SQL_INSERT_REVIEW);
			pstmt.setString(1, r.getCrn());
			pstmt.setString(2, r.getCrc());
			pstmt.setString(3, r.getTitle());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}

	@Override
	public int selectByEmail(Member m) {
		
		String id = m.getMemail();
		String dbid = null;
		int temp = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			
			pstmt = conn.prepareStatement(SQL_SELECT_BY_EMAIL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dbid = rs.getString("memail");
			}
			
			if(dbid != null) {
				JOptionPane.showMessageDialog(frame, "중복된 E-Mail이 존재합니다.");
				temp = 1;
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return temp;
	}
	
	
}
