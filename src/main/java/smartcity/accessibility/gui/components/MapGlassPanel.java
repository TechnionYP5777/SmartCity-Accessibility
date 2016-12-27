package smartcity.accessibility.gui.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class MapGlassPanel extends JComponent implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 803094754517375708L;
	public MapGlassPanel(){
		super();
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("mouse click in glass panel");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		System.out.println("mouse enter in glass panel");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		System.out.println("mouse exit in glass panel");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		System.out.println("mouse pressed in glass panel");
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		System.out.println("mouse released in glass panel");
	}

}
