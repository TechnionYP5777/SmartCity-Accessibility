package smartcity.accessibility.gui.components.user;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import smartcity.accessibility.database.UserManager;
import smartcity.accessibility.gui.Application;
import smartcity.accessibility.gui.components.ButtonsPanel;
import smartcity.accessibility.socialnetwork.User;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPasswordField;

public class LoginFrame implements MouseListener {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnLogin;


	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSignupForm = new JLabel("Login Form");
		lblSignupForm.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSignupForm.setBounds(151, 25, 166, 15);
		frame.getContentPane().add(lblSignupForm);
		
		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUsername.setBounds(12, 61, 101, 15);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblPassword.setBounds(12, 121, 101, 15);
		frame.getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(151, 59, 198, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(151, 303, 117, 25);
		btnLogin.addMouseListener(this);
		frame.getContentPane().add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(151, 119, 198, 25);
		frame.getContentPane().add(passwordField);
		
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() != btnLogin)
			return;
		User u = UserManager.LoginUser(textField.getText(), String.copyValueOf(passwordField.getPassword()));
		if (u == null)
			JOptionPane.showMessageDialog(Application.frame, "Login Failed.", "Login error", JOptionPane.ERROR_MESSAGE);
		else {
			Application.appUser = u;
			ButtonsPanel.USER_PROFILE_BUTTON.setText("view profile");
			ButtonsPanel.USER_PROFILE_BUTTON.setVisible(true);
		}
		frame.dispose();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}
