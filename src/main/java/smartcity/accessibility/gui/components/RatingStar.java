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

public class RatingStar extends JPanel implements MouseListener{
	private List<JLabel> labels;
	private int currRate;
	private Icon emptyStar;
	private Icon fullStar;
	private boolean clickAble;
	public RatingStar(int numOfStars){
		super(new GridLayout(1,5,2,2));
		this.clickAble = true;
		this.labels = new ArrayList<JLabel>();
		this.emptyStar = new ImageIcon("res/emptyStar");
		this.fullStar = new ImageIcon("res/fullStar");
		for(int i=0; i<numOfStars; ++i){
			JLabel l = new JLabel();
			l.setIcon(this.emptyStar);
			l.addMouseListener(this);
			this.add(l);
			this.labels.add(l);
		}
	}
	public int getRate(){
		return this.currRate;
	}
	public void setRate(int rate){
		for(int i=0;i<this.labels.size();i++){
			this.labels.get(i).setIcon((i < rate) ? this.fullStar : this.emptyStar);
		}
	}
	public void setClickable(boolean b){
		clickAble = b;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!clickAble)
			return;
		boolean flag = true;
		for(int i= 0;i<labels.size();i++){
			JLabel l = labels.get(i);
			l.setIcon(flag ? this.fullStar : this.emptyStar);
			if(e.getSource().equals(l)){
				flag = false;
				this.currRate = i+1;
			}
		}
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
