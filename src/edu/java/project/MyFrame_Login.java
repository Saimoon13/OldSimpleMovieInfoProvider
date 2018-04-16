package edu.java.project;
import static edu.java.project.OracleQuery.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import oracle.jdbc.OracleDriver;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;


public class MyFrame_Login extends JFrame {

	private JPanel contentPane;
	private JTextField textEmail;
	private JPasswordField textPw;
	private JButton btnSignup;
	private ProjectDAO dao;
	private JFrame frame;
	private GUI parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame_Login frame = new MyFrame_Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyFrame_Login() {
		dao = ProjectDAOImple.getInstance();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 246, 147);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("E-Mail:");
		label.setBounds(12, 10, 48, 27);
		contentPane.add(label);
		
		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(62, 13, 157, 21);
		contentPane.add(textEmail);
		
		JLabel label_1 = new JLabel("PW:");
		label_1.setBounds(32, 47, 28, 27);
		contentPane.add(label_1);
		
		textPw = new JPasswordField();
		textPw.setBounds(62, 50, 157, 21);
		contentPane.add(textPw);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String email = textEmail.getText();
				char[] pw = textPw.getPassword();
				String rpw = String.valueOf(pw);
				
				if(email.equals("")||email == null||rpw == null) {
					JOptionPane.showMessageDialog(frame, "정확한 정보를 입력하세요.");
					return;
				}
				
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try {
					DriverManager.registerDriver(new OracleDriver());
					
					conn = DriverManager.getConnection(URL,USER,PASSWORD);
					pstmt = conn.prepareStatement(SQL_SELECED_BY_ID);
					pstmt.setString(1, email);
					rs = pstmt.executeQuery();
					
					String mname = null; // 데이터 베이스 이름
					String memail = null; // 데이터 베이스 메일
					String mpw = null; // 데이터 베이스 암호
					if(rs.next()) {
						mname = rs.getString(1);
						memail = rs.getString(2);
						mpw = rs.getString(3);
					} // end while
					
					if (memail == null) {
						JOptionPane.showMessageDialog(frame, "아이디와 비밀번호를 다시 입력해 주십시오.");
					} else if(memail.equals(email) && rpw.equals(mpw)) {
						JOptionPane.showMessageDialog(frame, "로그인 되었습니다.");
						parent.enableRateButton();
						parent.unVisibleLoginButton();
						parent.visibleLogoutButton();
						parent.setUserName(mname);
						dispose();
						
					} else {
						JOptionPane.showMessageDialog(frame, "비밀번호가 알맞지 않습니다.");
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					try {
						rs.close();
						pstmt.close();
						conn.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		btnLogin.setBounds(122, 81, 97, 23);
		contentPane.add(btnLogin);
		
		btnSignup = new JButton("SignUp");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				MyFrame_signUp.showMyDialog();
			}
		});
		btnSignup.setBounds(12, 81, 97, 23);
		contentPane.add(btnSignup);
	}

	public static void showMyDialog(GUI window) {
		MyFrame_Login dlg = new MyFrame_Login();
		dlg.parent = window;
		dlg.setVisible(true);
	}
	
	
}
