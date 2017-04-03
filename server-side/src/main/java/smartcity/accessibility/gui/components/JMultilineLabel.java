package smartcity.accessibility.gui.components;

import javax.swing.JTextArea;
import javax.swing.UIManager;

public class JMultilineLabel extends JTextArea {
	private static final long serialVersionUID = 1L;

	public JMultilineLabel(String text) {
		super(text);
		setEditable(false);
		setCursor(null);
		setOpaque(false);
		setFocusable(false);
		setFont(UIManager.getFont("Label.font"));
		setWrapStyleWord(false);
		setLineWrap(true);
	}
}
