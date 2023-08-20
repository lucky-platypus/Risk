package risiko;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
	ArrayList<Karten> deck;
	ArrayList<Karten> ablage;
	private int gefüllt;
	private int tauschintensität;
	
	Deck(){
		deck= new ArrayList<Karten>();
		ablage = new ArrayList<Karten>();
		gefüllt = 42;
		tauschintensität =4;
		Karten temp;
		BufferedReader lies;
			try {
				lies = new BufferedReader(new FileReader(".\\Laender.txt"));
				for (int i =0;i<42;i++) {
					try {
						temp = new Karten(lies.readLine(), i);
						deck.add(temp);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	void austeilen (Spieler p) {
		int r;
		Karten haltmal;
		Random shuffle = new Random();
		r = shuffle.nextInt(gefüllt);
		haltmal=deck.get(r);
		deck.remove(r);
		gefüllt -=1;
		p.gibKarte(haltmal);
		if (gefüllt==0) {
			deck = ablage;
			ablage.clear();
		}
		
	}
	
	
	
	void ablage (Karten k) {
		ablage.add(k);
	}
	
	int eintauschen (Spieler aktiv, Karten a, Karten b, Karten c) {
		int halten;
		if((a.getTyp()==b.getTyp() && b.getTyp()==c.getTyp())||(a.getTyp()!=b.getTyp()&& a.getTyp()!=c.getTyp() && b.getTyp()!=c.getTyp())) {
			halten = tauschintensität;
			tauschintensität +=2;
			Land ln;
			for (int i=0; i < aktiv.besetzt.size();i++) {
				ln=aktiv.besetzt.get(i);
				if(a.getLand() == ln.getName() || b.getLand() == ln.getName() || c.getLand() == ln.getName()) {
					ln.verstaerken(2);
				}
			}
			aktiv.nimmKarte(a);
			aktiv.nimmKarte(b);
			aktiv.nimmKarte(c);
			return halten;
		}
		return 0;
	}
	
}