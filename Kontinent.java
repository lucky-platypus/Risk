package risiko;

import java.util.ArrayList;

public class Kontinent {
	private String name;
	private int wert;
	public ArrayList<Land> enthalten;
	public ArrayList<Land> modifiziert;

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
			if (enthalten.get(i).getBesetzer()!=aktiv) {
				kontrolliert = false;
			}
			i+=1;
		}
		if (kontrolliert) b+=wert;
		return b;
	}
	void modifizieren() {
		Land land;
		modifiziert = new ArrayList<Land>();
        for (int i=0;i<enthalten.size();i++) {
            modifiziert.add(enthalten.get(i));
        }
		for (int i=0;i<enthalten.size();i++) {
			land = (enthalten.get(i));
			for (int j=0;j<land.nachbar.size();j++) {
				if (!modifiziert.contains(land.nachbar.get(j))) {
					modifiziert.add(land.nachbar.get(j));
				}
			}
			
		}
	}
	public String getString() {
		return name;
	}
	public void setString(String s) {
		name =s;
	}
	public int getWert() {
		return wert;
	}
	public void setWert(int s) {
		wert=s;
	}


}