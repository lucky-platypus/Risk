package risiko;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;


public class Land {
	private int truppen;
	private String name;
	private Spieler besetzer;
	public ArrayList<Land> nachbar;
	private boolean entdeckt;
	private JButton button;
	private JLabel label;

	Land(String l){
		nachbar = new ArrayList<Land>();
		name = l;
		truppen =0;



	}
	public ArrayList<Land> nachbarschaft(){
		return nachbar;
	}
	/**
	 * hinzufügen von truppen zum land
	 * @param a hinzugefügte truppen
	 */
	public void verstaerken (int a) {
		truppen+=a;;
		label.setText(String.valueOf(truppen));
	}
	
	/**
	 * entfernen von truppen aus einem Land
	 * @param a entfernte Truppe
	 */
	public void verlust (int a) {
		if (truppen>=a) {
			truppen -=a;
			label.setText(String.valueOf(truppen));
		}else;

	}
	/**
	 * nachbar hinzufügen
	 * @param a nachbarland
	 */
	public void addnachbar(Land a) {
		nachbar.add(a);
	}
	
	/**
	 * Setzt neuen Besitzer	
	 * @param b neuer Besitzer
	 * @param t anzahl neuer truppen
	 */
	public void setbesetzer(Spieler b, int t) {
		besetzer = b;
		truppen = t;
		label.setText(String.valueOf(truppen));
		this.button.setBackground(b.getColor());
	}

	public String getName() {
		return name;
	}
	public void setName(String s) {
		name = s;
	}
	public int getTruppen() {
		return truppen;
	}
	public void setTruppen(int s) {
		truppen=s;
	}
	public Spieler getBesetzer() {
		return besetzer;
	}
	public void setSpieler(Spieler s) {
		besetzer =s;
	}
	public boolean getEntdeckt() {
		return entdeckt;
	}
	public void setEntdeckt(boolean s) {
		entdeckt =s;
	}
	public JButton getButton() {
		return button;
	}
	public void setButton(JButton s) {
		button = s;
	}
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel s) {
		label = s;
	}
}