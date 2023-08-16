package risiko;

import java.util.ArrayList;

public class Kontinent {
	public String name;
	public ArrayList<Land> enthalten;
	public int wert;
	
	Kontinent(String n){
		name = n;
		enthalten = new ArrayList<Land>();
	}
	
	
	void addLand (Land l) {
		enthalten.add(l);
		
	}
	
	int Kontinentkontrolle(Spieler aktiv) {
		int b =0;
		int i =0;
		boolean kontrolliert = true;
		while (kontrolliert && i<enthalten.size()) {
			if (enthalten.get(i).besetzer!=aktiv) {
				kontrolliert = false;
			}
			i+=1;
		}
		if (kontrolliert) b+=wert;
		return b;
	}
	
	
	
}
