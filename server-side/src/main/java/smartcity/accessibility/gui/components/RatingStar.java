package smartcity.accessibility.gui.components;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * Rating Star class - receive the number of starts. the minimum rate must be
 * one.
 * 
 * @author yael
 */
public class RatingStar extends JPanel implements MouseListener {
	private static final int padding = 2;
	private static final long serialVersionUID = 1L;
	private List<JLabel> labels;
	private int currRate;
	private Icon emptyStar;
	private Icon fullStar;
	private boolean clickAble;
	
	public RatingStar(int numOfStars) {
		super(new GridLayout(1, numOfStars, padding, padding));
		this.clickAble = true;
		this.labels = new ArrayList<JLabel>();
		this.emptyStar = new ImageIcon("res/emptyStar.png");
		this.fullStar = new ImageIcon("res/fullStar.png");
		this.currRate = 1;
		for (int i = 0; i < numOfStars; ++i) {
			JLabel l = new JLabel();
			l.setIcon((i == 0) ? this.fullStar : this.emptyStar);
			l.addMouseListener(this);
			this.add(l);
			this.labels.add(l);
		}
	}

	public int getRate() {
		return this.currRate;
	}

	public void setRate(int rate) {
		for (int ¢ = 0; ¢ < this.labels.size(); ++¢)
			this.labels.get(¢).setIcon((¢ < rate) ? this.fullStar : this.emptyStar);
		this.repaint();
	}

	public void setClickable(boolean ¢) {
		clickAble = ¢;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!clickAble)
			return;
		boolean flag = true;
		for (int i = 0; i < labels.size(); ++i) {
			JLabel l = labels.get(i);
			l.setIcon(flag ? this.fullStar : this.emptyStar);
			if (e.getSource().equals(l)) {
				flag = false;
				this.currRate = i + 1;
			}
		}
		this.repaint();
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