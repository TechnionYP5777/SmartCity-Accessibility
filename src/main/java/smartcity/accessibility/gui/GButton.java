package smartcity.accessibility.gui;

import java.awt.Button;

public class GButton extends Button {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1936213381544975733L;

	public GButton(){
		//setSize(20, 30);
		setEnabled(true);
		setVisible(true);
	}
	
	public GButton(String s){
		this();
		setLabel(s);
	
	}


	

}
