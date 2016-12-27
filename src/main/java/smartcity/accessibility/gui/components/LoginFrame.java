package smartcity.accessibility.gui.components;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JPasswordField;

public class LoginFrame {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;


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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		textField.setBounds(151, 59, 198, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSignup = new JButton("Login");
		btnSignup.setBounds(151, 303, 117, 25);
		frame.getContentPane().add(btnSignup);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(151, 119, 198, 19);
		frame.getContentPane().add(passwordField);
		
		frame.setVisible(true);
	}
}
