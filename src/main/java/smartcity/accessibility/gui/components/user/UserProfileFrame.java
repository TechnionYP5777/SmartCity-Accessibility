package smartcity.accessibility.gui.components.user;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import smartcity.accessibility.gui.Application;

/**
 * 
 * @author yael
 *
 */
public class UserProfileFrame {
	private JFrame frame;

	public UserProfileFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1));

		JLabel lblUsername = new JLabel("Username: " + Application.appUser.getName());
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 12));
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password:" + Application.appUser.getPassword());
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 12));
		frame.getContentPane().add(lblPassword);

		JLabel lblPrivilege = new JLabel("Privilege:" + Application.appUser.getPrivilege().name());
		lblPrivilege.setFont(new Font("Dialog", Font.PLAIN, 12));
		frame.getContentPane().add(lblPrivilege);

		frame.setVisible(true);
	}
}
