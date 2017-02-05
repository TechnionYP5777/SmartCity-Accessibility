package smartcity.accessibility.gui.components;

import com.teamdev.jxmaps.swing.MapView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class OptionsWindow {
    protected JFrame parentFrame;
    protected JWindow contentWindow;
    private Dimension size;

    public OptionsWindow(MapView parentWindow, Dimension size) {
        this.size = size;
        for (Container parent = parentWindow.getParent(); parent != null;) {
			if (parent instanceof JFrame) {
				parentFrame = (JFrame) parent;
				break;
			}
			parent = parent.getParent();
		}

        parentFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent __) {
                updatePosition();
            }

            @Override
            public void componentResized(ComponentEvent __) {
                updatePosition();
            }
        });

        contentWindow = new JWindow(parentFrame);
        contentWindow.setVisible(true);
        contentWindow.setFocusable(true);
        contentWindow.setFocusableWindowState(true);

        parentFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent __) {
                contentWindow.setVisible(false);
            }

            @Override
            public void windowDeiconified(WindowEvent __) {
                contentWindow.setVisible(true);
            }
        });

        initContent(contentWindow);
        updatePosition();
    }

    public abstract void initContent(JWindow contentWindow);

    protected void updatePosition() {
        Rectangle bounds = new Rectangle();
        bounds.setLocation((int) (parentFrame.getX() + (parentFrame.getWidth() - size.getWidth()) / 2), (int) (parentFrame.getY() + parentFrame.getHeight() - size.getHeight() - 20));
        bounds.setSize(size);

        contentWindow.setBounds(bounds);
    }

    public void dispose() {
        contentWindow.dispose();
    }
}