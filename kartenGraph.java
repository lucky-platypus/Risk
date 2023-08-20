package risiko;



import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class kartenGraph extends JButton {
	private String land;
	private String typ;
	private boolean inHand = false;
	private Spieler besitzer;
	private Karten Karte;
	
	public kartenGraph(int x, int y, int witdh,int height) {
		this.setBounds(x, y, witdh, height);
	}
	
	public void setKarte(Karten karte) {
		
		this.setText("<html>"+karte.getLand() + "<br>" + karte.ansagen() + "</html>") ;
		this.setBorder(new LineBorder(Color.black,2));
		Karte=karte;
	}
	public void removeKarte() {
		this.Karte = null;
		this.setText("");
		this.setBorder(new LineBorder(Color.white,0));
	}
	public void setBesitzer(Spieler spieler) {
		this.besitzer=spieler;
	}
	public Karten getKarte() {
		return Karte;
	}



}