package edu.java.project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textName;
	private GUI parent;
	private ProjectDAO dao;
	private JFrame frame;


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MyFrame frame = new MyFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public MyFrame(GUI parent) {
		this.parent = parent;
		dao = ProjectDAOImple.getInstance();
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 445, 229);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JLabel label = new JLabel("30자 이내로 입력하세요");
		label.setBounds(10, 166, 153, 15);
		contentPane.add(label);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 20, 421, 139);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Review", null, panel, null);
		panel.setLayout(null);
		
		JTextArea textCt = new JTextArea();
		textCt.setBounds(0, 0, 416, 120);
		textCt.setLineWrap(true);
		
		panel.add(textCt);
		
		JLabel label_1 = new JLabel("Name");
		label_1.setBounds(244, 1, 38, 33);
		contentPane.add(label_1);
		
		JButton button = new JButton("Confirm");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				String name = parent.getUserName();
				String contents = textCt.getText();
				if(contents.length()>=30) {
					JOptionPane.showMessageDialog(frame, "30자 이하로 입력해 주세요.");
					return;
				}
				String title = parent.getDbtitleMain();
				
				Rate r = new Rate(name, contents, title);
				
				int result = dao.insertRate(r);
				JOptionPane.showMessageDialog(frame, "입력에 성공하였습니다.");
				
				parent.initializeTable();
				dispose();
			}
		});
		button.setBounds(323, 162, 97, 23);
		contentPane.add(button);
		
		textName = new JTextField();
		textName.setEditable(false);
		textName.setHorizontalAlignment(SwingConstants.CENTER);
		textName.setBounds(294, 6, 122, 23);
		contentPane.add(textName);
		textName.setColumns(10);
		textName.setText(parent.getUserName());
	}

	public static void showMyDialog(GUI window) {
		MyFrame dlg = new MyFrame(window);
		dlg.setVisible(true);
	}

}
