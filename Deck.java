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
	int gefüllt;
	int tauschintensität;
	
	Deck(){
		deck= new ArrayList<Karten>();
		ablage = new ArrayList<Karten>();
		gefüllt = 42;
		tauschintensität =4;
		Karten temp;
		BufferedReader lies;
			try {
				lies = new BufferedReader(new FileReader("C:\\Users\\Lumia\\eclipse-workspace\\Risiko\\src\\risiko\\Laender.txt"));
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
		
		temp=deck.get(5);
		temp.ansagen();
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
		if((a.typ==b.typ && b.typ==c.typ)||(a.typ!=b.typ&& a.typ!=c.typ && b.typ!=c.typ)) {
			halten = tauschintensität;
			tauschintensität +=2;
			Land ln;
			for (int i=0; i < aktiv.besetzt.size();i++) {
				ln=aktiv.besetzt.get(i);
				if(a.land == ln.name || b.land == ln.name || c.land == ln.name) {
					ln.verstaerken(2);
				}
			}
			aktiv.nimmKarte(a);
			aktiv.nimmKarte(b);
			aktiv.nimmKarte(c);
			return halten;
		}
		System.out.println("fehler: Karten sind so nicht tauschbar");
		return 0;
	}
	
}