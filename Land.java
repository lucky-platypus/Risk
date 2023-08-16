package risiko;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;


public class Land {
	public int truppen;
	public String name;
	public Spieler besetzer;
	public ArrayList<Land> nachbar;
	public boolean entdeckt;
	public JButton button;
	public JLabel label;
	
	Land(String l){
		nachbar = new ArrayList<Land>();
		name = l;
		truppen =0;
		
		
		
	}
	ArrayList<Land> nachbarschaft(){
		return nachbar;
	}
	
	void verstaerken (int a) {
		System.out.print(truppen);
		truppen+=a;
		System.out.print(truppen);
		label.setText(String.valueOf(truppen));
	}
	
	void verlust (int a) {
		if (truppen>a) {
			truppen -=a;
		}else if (truppen == a){
			truppen -=a;
		}else System.out.println("Fehler, zu wenig Truppen vorhanden");
		
	}
	
	void addnachbar(Land a) {
		nachbar.add(a);
	}
	
	void setbesetzer(Spieler b, int t) {
		besetzer = b;
		truppen = t;
		this.button.setBackground(b.color);
	}
	
	String getname() {
		return name;
	}
}
