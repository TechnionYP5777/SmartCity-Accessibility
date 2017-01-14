package smartcity.accessibility.gui.components;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * This Class is a frame that display a spinning wheel.
 * Use:
 * 		SpinningWheel wheel = new SpinningWheel();
 * 		//long calculation....
 *  	wheel.dispose();
 * @author yael
 *
 */
public class SpinningWheel extends JFrame{

	private static final long serialVersionUID = 1L;

	public SpinningWheel(){
	    ImageIcon loading = new ImageIcon("res/wheel.gif");
	    this.add(new JLabel("", loading, JLabel.CENTER));
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(loading.getIconHeight(), loading.getIconWidth());
	    this.setUndecorated(true);
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}
}
