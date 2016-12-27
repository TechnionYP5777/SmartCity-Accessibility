package smartcity.accessibility.gui.components;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;



public class MapFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6388654596262185662L;

	

	public MapFrame(String string) {
		super(string);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// setSize(FRAME_X_SIZE, FRAME_Y_SIZE);
		setLayout(new GridBagLayout());
		setVisible(true);
		setResizable(false);

	}

	

}
