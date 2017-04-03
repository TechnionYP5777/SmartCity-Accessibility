package smartcity.accessibility.gui.components.search;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;

public class SearchFieldUI extends BasicTextFieldUI {
    private final JTextField textField;

    public SearchFieldUI(JTextField textField) {
        this.textField = textField;
    }

    @Override
    protected void paintBackground(Graphics ¢) {
        super.paintBackground(¢);
        if (textField.getToolTipText() != null && textField.getText().isEmpty())
			paintPlaceholderText(¢, textField);
    }

    protected void paintPlaceholderText(Graphics g, JComponent c) {
        g.setColor(new Color(0x75, 0x75, 0x75));
        g.setFont(c.getFont());
        String text = textField.getToolTipText();
        if (g instanceof Graphics2D)
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString(text, 0, 14);
    }
}