package risiko;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class Spiel {
	public boolean gewinner;
	public int phase;
	public int anderreihe;
	public int players;
	public Phasen runde;
	ArrayList<Spieler> spielende;
	Color colors[] = {Color.red,Color.green,Color.blue,Color.yellow,Color.ORANGE,Color.MAGENTA};
	Welt erde = new Welt();
	
	Spiel (int play,int bots,Welt welt){
		spielende = new ArrayList<Spieler>();
		Spieler player;
		anderreihe =0;
		gewinner =false;
		players=play;
		erde = welt;
		for(int i=0; i<players;i++) {
			if (i<players-bots) {
				player = new Spieler (true);
			}else {
				player = new Spieler (false);
			}
			spielende.add(player);
			spielende.get(i).color = colors[i];
		}
	}
	void landverteilung(Welt erde) {
		int dran=0;
		int halte;
		Random rand = new Random();
		ArrayList<Land> hold = new ArrayList<Land>();
		for (int i=0; i < erde.laender.length;i++) {
			hold.add(erde.laender[i]);
		}
		while (hold.size()>0) {
			halte = rand.nextInt(hold.size());
			spielende.get(dran).erobert(hold.get(halte));
			spielende.get(dran).besetzt.get(spielende.get(dran).besetzt.size()-1).setbesetzer(spielende.get(dran), 1);
			hold.remove(halte);
			dran+=1;
			if (dran>=spielende.size()) dran=0;
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
	void truppenverteilung (Welt erde){
		int verteilbar;
		verteilbar = 50 - 5*spielende.size();
		for (int i =0;i<spielende.size();i++) {
			spielende.get(i).truppen = verteilbar;
		}
		actionlistener lis = new actionlistener(this);
		for(int i = 0; i <42;i++) {
			erde.gui1.list[i].addActionListener(lis);
  
		}

			verteilbar -=1;
			}

		 
	}
	
