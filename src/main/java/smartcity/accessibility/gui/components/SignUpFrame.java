package smartcity.accessibility.gui.components;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SignUpFrame extends JFrame {
	private static final int X_SIZE = 400;
	private static final int Y_SIZE = 600;
	
	private JPanel panel;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8954943356608092335L;

	public SignUpFrame(){
		super("Signup");
		setSize(X_SIZE,Y_SIZE);
		panel = new JPanel();
		panel.setSize(X_SIZE,Y_SIZE);
		setVisible(true);
		
	}
	
}
