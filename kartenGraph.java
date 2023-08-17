package risiko;



import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class kartenGraph extends JButton {
	private String land;
	private String typ;
	private Spieler besitzer;
	
	public kartenGraph(String land, String typ) {
		this.setText("<html>"+land + "<br>" + typ + "</html>") ;
		this.setBorder(new LineBorder(Color.black,2));
	}



}