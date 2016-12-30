package smartcity.accessibility.gui.components;

import javax.swing.JTextArea;
import javax.swing.UIManager;

public class JMultilineLabel extends JTextArea {
	private static final long serialVersionUID = 1L;

	public JMultilineLabel(String text, int rows, int cols) {
		super(text);
		// setColumns(10);
		setEditable(false);
		setCursor(null);
		setOpaque(false);
		setFocusable(false);
		setFont(UIManager.getFont("Label.font"));
		setWrapStyleWord(false);
		setLineWrap(true);
		System.out.println(getRowHeight());
	}
}
