package smartcity.accessibility.gui.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import smartcity.accessibility.gui.components.user.LoginFrame;
import smartcity.accessibility.gui.components.user.SignUpFrame;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.extendedMapView;
import smartcity.accessibility.mapmanagement.jxMapsFunctionalityTest;

public class ButtonsPanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8394584034225983460L;

	public static GButton LOGIN_BUTTON;
	public static GButton SIGNUP_BUTTON;
	public static JLabel USERNAME;
	public static GButton CLEAR_MARKERS_BUTTON;

	public ButtonsPanel() {
		// setSize(MapFrame.FRAME_X_SIZE, 100);
		LOGIN_BUTTON = new GButton("Login");
		LOGIN_BUTTON.addMouseListener(this);
		add(LOGIN_BUTTON);
		LOGIN_BUTTON.setVisible(true);
		SIGNUP_BUTTON = new GButton("Signup");
		SIGNUP_BUTTON.addMouseListener(this);
		add(SIGNUP_BUTTON);
		SIGNUP_BUTTON.setVisible(true);
		USERNAME = new JLabel("");
		add(USERNAME);
		USERNAME.setVisible(true);
		
		CLEAR_MARKERS_BUTTON = new GButton("clear markers");
		CLEAR_MARKERS_BUTTON.addMouseListener(this);
		add(CLEAR_MARKERS_BUTTON);
		CLEAR_MARKERS_BUTTON.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == ButtonsPanel.LOGIN_BUTTON)
			new LoginFrame();
		if (e.getSource() == SIGNUP_BUTTON)
			new SignUpFrame();
		if(e.getSource() == CLEAR_MARKERS_BUTTON)
			JxMapsFunctionality.ClearMarkers((extendedMapView) JxMapsFunctionality.getMapView());
		System.out.println("clicked " + e.getSource());

	}

	@Override
	public void mouseEntered(MouseEvent __) {

	}

	@Override
	public void mouseExited(MouseEvent __) {

	}

	@Override
	public void mousePressed(MouseEvent __) {

	}

	@Override
	public void mouseReleased(MouseEvent __) {

	}

}
