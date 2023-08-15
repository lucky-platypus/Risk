package gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class kartenGraph extends JButton {
	private String land;
	private String typ;
	
	public kartenGraph(int x, int y, int height, int withd, String land, String typ) {
		this.setBounds(x, y, withd, height);
		this.setText("<html>"+land + "<br>" + typ + "</html>") ;
		this.setBorder(new LineBorder(Color.black,2));
	}
		
	
	
}
