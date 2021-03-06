package edu.java.project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import oracle.jdbc.OracleDriver;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JScrollPane;
import static edu.java.project.OracleQuery.*;
import javax.swing.SwingConstants;

public class GUI {

	private static final String INFO_PANEL = "INFO_PANEL";
	private static final String SEARCH_PANEL = "SEARCH_PANEL";
	public static final String[] COLUMN_NAMES = { "Name", "Review" };
	private DefaultTableModel model;

	private ProjectDAO dao;
	private JFrame frame;
	private JTextField texttitle;
	private JTable tableCriticContents;
	private JTextArea spInfo;
	private JScrollPane spcritic = new JScrollPane();
	private JLabel lblPoster = new JLabel("");
	private JButton rateButton;
	private JLabel lblTitle;
	private JScrollPane scrollPane;
	private JLabel lblBack;
	private JLabel lblTomato;
	private String userName;
	private String dbtitleMain;
	private JButton btnLogout;
	private JButton btnlogin;

	public String getDbtitleMain() {
		return dbtitleMain;
	}

	public void setDbtitleMain(String dbtitmeMain) {
		this.dbtitleMain = dbtitmeMain;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void enableRateButton() {
		rateButton.setEnabled(true);
	}
	public void visibleLogoutButton() {
		btnLogout.setVisible(true);
	}
	public void unVisibleLoginButton() {
		btnlogin.setVisible(false);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		dao = ProjectDAOImple.getInstance();
		initialize();
	}
	public void initializeTable() {
		deleteAllrowsFromTable();
		
		List<Rate> list = dao.select(this);
		for(Rate r:list) {
			Object[] rowDate = { r.getCrn(),r.getCrc() };
			model.addRow(rowDate);
		} // end for
	}
	public void deleteAllrowsFromTable() {
		
		int rows = tableCriticContents.getRowCount();
		for (int i = rows-1; i>=0; i--) {
			model.removeRow(i);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setForeground(Color.WHITE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		JPanel SearchPanel = new JPanel();
		frame.getContentPane().add(SearchPanel, "SEARCH_PANEL");
		SearchPanel.setLayout(null);

		texttitle = new JTextField();
		texttitle.setHorizontalAlignment(SwingConstants.CENTER);
		texttitle.setFont(new Font("굴림", Font.PLAIN, 18));
		texttitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectMovieByTitle();
			}
		});
		texttitle.setBorder(new LineBorder(Color.BLACK, 5));
		texttitle.setBounds(267, 289, 263, 44);
		SearchPanel.add(texttitle);
		texttitle.setColumns(10);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("D:\\bdsucess\\Study (4)\\Study\\Individual Project\\libs\\Project Outline\\f_ResizeImage_Redhood.png"));
		lblLogo.setBounds(261, 76, 289, 230);
		SearchPanel.add(lblLogo);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("D:\\bdsucess\\Study (4)\\Study\\Individual Project\\libs\\Project Outline\\1.jpg"));
		lblNewLabel.setBounds(0, 0, 762, 407);
		SearchPanel.add(lblNewLabel);

		JPanel TablePanel = new JPanel();
		frame.getContentPane().add(TablePanel, "name_341162945689050");

		JPanel InfoPanel = new JPanel();
		frame.getContentPane().add(InfoPanel, INFO_PANEL);
		InfoPanel.setLayout(null);

		JButton backButton = new JButton("Back");
		backButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				texttitle.setText("");

				CardLayout c1 = (CardLayout) frame.getContentPane().getLayout();
				// 2. 카드 레이아웃에 있는 show() 메소드
				c1.show(frame.getContentPane(), SEARCH_PANEL);
				
			}
		});
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				btnLogout.setVisible(false);
				btnlogin.setVisible(true);
				rateButton.setEnabled(false);
				
			}
		});
		btnLogout.setFont(new Font("굴림", Font.BOLD, 12));
		btnLogout.setForeground(new Color(106, 90, 205));
		btnLogout.setBackground(Color.LIGHT_GRAY);
		btnLogout.setBorder(new LineBorder(new Color(192, 192, 192), 3));
		btnLogout.setBounds(488, 339, 69, 46);
		InfoPanel.add(btnLogout);
		btnLogout.setVisible(false);

		lblPoster.setBorder(new LineBorder(Color.LIGHT_GRAY, 5));
		lblPoster.setBounds(488, 59, 241, 270);
		lblPoster.setForeground(Color.WHITE);
		InfoPanel.add(lblPoster);
		backButton.setBounds(660, 339, 69, 46);
		InfoPanel.add(backButton);

		rateButton = new JButton("Rate");
		rateButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		rateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyFrame.showMyDialog(GUI.this);
			}
		});

		rateButton.setBounds(574, 340, 69, 46);
		rateButton.setEnabled(false);
		InfoPanel.add(rateButton);

		spcritic.setBorder(new LineBorder(new Color(130, 135, 144), 5));
		spcritic.setBounds(23, 290, 448, 96);
		InfoPanel.add(spcritic);

		tableCriticContents = new JTable();
		spcritic.setViewportView(tableCriticContents);

		btnlogin = new JButton("Login");
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyFrame_Login.showMyDialog(GUI.this);
			}
		});
		btnlogin.setBorder(new LineBorder(new Color(192, 192, 192), 3));
		btnlogin.setBounds(489, 339, 69, 46);
		InfoPanel.add(btnlogin);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 59, 448, 216);
		InfoPanel.add(scrollPane);

		spInfo = new JTextArea();
		spInfo.setEditable(false);
		spInfo.setLineWrap(true);
		spInfo.setBorder(new LineBorder(Color.LIGHT_GRAY, 5));

		scrollPane.setViewportView(spInfo);

		lblTomato = new JLabel("");

		lblTomato.setBounds(402, 32, 69, 26);
		lblTomato.setForeground(Color.WHITE);
		InfoPanel.add(lblTomato);

		lblTitle = new JLabel();

		lblTitle.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 23));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(30, 18, 289, 39);
		InfoPanel.add(lblTitle);

		lblBack = new JLabel("");
		lblBack.setIcon(new ImageIcon("D:\\bdsucess\\Study (4)\\Study\\Individual Project\\libs\\Project Outline\\1.jpg"));
		lblBack.setBounds(0, 0, 762, 407);
		InfoPanel.add(lblBack);
		frame.setBounds(100, 100, 778, 446);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] data = {};
		model = new DefaultTableModel(data, COLUMN_NAMES) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// JTable 객체 생성
		tableCriticContents = new JTable();
		// tableCriticContents에 DefaultTableModel을 세팅
		tableCriticContents.setModel(model);
	 
		tableCriticContents.getColumn("Name").setPreferredWidth(-10000);
		tableCriticContents.getColumn("Review").setPreferredWidth(350);
		spcritic.setViewportView(tableCriticContents);
		
	}

	private void selectMovieByTitle() {

		String title = texttitle.getText();
		if (title == null || title.equals("")) {
			JOptionPane.showMessageDialog(frame, "Enter title, please");
			return;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			DriverManager.registerDriver(new OracleDriver());

			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			pstmt = conn.prepareStatement(SQL_SELECTED_BY_TITLE);
			pstmt.setString(1, title.toUpperCase());
			rs = pstmt.executeQuery();

			String dbtitle = null;
			String movieinfo = null;
			String tomato = null;
			String poster = null;
			if (rs.next()) {
				dbtitle = rs.getString(1);
				movieinfo = rs.getString(2);
				tomato = rs.getString(3);
				poster = rs.getString(4);

				lblTitle.setText(dbtitle);
				spInfo.setText(movieinfo);
				lblPoster.setIcon(new ImageIcon(poster));
				lblTomato.setIcon(new ImageIcon(tomato));

				dbtitleMain = dbtitle;
				initializeTable();
				
				// CardLayout의 Panel을 변경
				// 1. 카드 레이아웃을 찾음
				CardLayout c1 = (CardLayout) frame.getContentPane().getLayout();
				// 2. 카드 레이아웃에 있는 show() 메소드
				c1.show(frame.getContentPane(), INFO_PANEL);

			} else {
				JOptionPane.showMessageDialog(frame, "데이터베이스에 영화정보가 존재하지 않습니다.");
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

	} // end method
}
