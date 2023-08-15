package risiko;

import java.util.ArrayList;

public class Spiel {
	public boolean gewinner;
	public int phase;
	public int anderreihe;
	public Phasen runde;
	ArrayList<Spieler> spielende;
	
	Spiel (){
		Spieler player;
		menue menü = new menue();
		anderreihe =0;
		gewinner =false;
		for(int i=0; i<menü.getplayercount();i++) {
			if (i<menü.getplayercount()-menü.getbots()) {
				player = new Spieler (true);
			}else {
				player = new Spieler (false);
			}
			spielende.add(player);
		}
	}
	
	void Runde(){
		runde.setaktiv(spielende.get(anderreihe));
		phase =1;
		runde.verstaerkung();
		phase =2;
		runde.kampfphase();
		if (spielende.get(anderreihe).hatgewonnen()) {
			gewinner =true;
			return;
		}else {
			phase =3;
			runde.truppenverschiebung();
			anderreihe+=1;
			if(anderreihe>=spielende.size()) {
				anderreihe =0;
			}
		}
		
	}
	
}
