package risiko;

import java.util.Random;
import java.util.Scanner;

//import combat.Spieler;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Phasen {

	
	private Spieler aktiv;
	private int phase;
	private Welt erde;
	private Spiel spiel;
	private Deck deck;
	private int anzahl;
	private boolean tausch=false;

	
	Phasen(Welt a, Deck b){
		erde = a;
		deck = b;
		phase=0;	
	}
	


	/**
	 * Berechnen der Verstärkungsanzahl so wie üperprüfen ob tausch möglich ist
	 */
	void verstaerkung() {
		anzahl=0;

			if (aktiv.hand.size()>2&&aktiv.hand.size()<5) {
				 tausch=true;
			}else if (aktiv.hand.size()==5) {
				tausch=true;
			}

		if (aktiv.getTerritorien()>=9) {
			anzahl+=aktiv.getTerritorien()/3;
		}else anzahl+=3;
		for (int i=0;i<6;i++) {
			anzahl+=erde.kontinente[i].Kontinentkontrolle(aktiv);
		}

			}	
	
	/**
	 * Kampf zwischen zwei ländern 
	 * @param angreifer angreifendes land
	 * @param verteidiger verteidigendes land
	 */
	void kampf(Land angreifer, Land verteidiger) {
		boolean weiter = true;
		int atruppen, min;
		Spieler defender = verteidiger.getBesetzer();
		if (angreifer.nachbar.contains(verteidiger)) {
			if (angreifer.getBesetzer() != verteidiger.getBesetzer()) {
			}else {
				erde.gui1.textfeld.setText("Du kannst nicht dein eigenes Land angfreifen mkay?");
				weiter=false;
			}
		}else {
			erde.gui1.textfeld.setText("Flugzeuge wurden noch nicht erfunden");
			weiter = false;
		}
		if (angreifer.getTruppen()==1)return;
		do {
			erde.gui1.textfeld.setText("Mit wievielen Truppen möchtest du angreifen?");
			try {
				atruppen= Math.max(atruppen= Math.max(1,angreifer.getTruppen()-1 ),Integer.parseInt(erde.gui1.textfeld2.getText()));
				if (atruppen>=angreifer.getTruppen())atruppen=angreifer.getTruppen()-1;
			} catch (NumberFormatException e) {
				erde.gui1.textfeld.setText("Du musst ein Int eingeben");
				atruppen= Math.max(1,angreifer.getTruppen()-1 );
			}
		}while (atruppen >=angreifer.getTruppen());
		if (weiter)angreifer.verlust(atruppen);
		while (weiter) {
			weiter =false;
			aktiv.würfeln(atruppen, false);
			defender.würfeln(verteidiger.getTruppen(), true);
			min = 3- Math.min(2,Math.min(atruppen, verteidiger.getTruppen()));
			for(int i = 2; i>= min ;i--) {

				if (aktiv.ergebnis[i]>defender.ergebnis[i]){
					verteidiger.verlust(1);
				}else if (aktiv.ergebnis[i]<=defender.ergebnis[i]) {
					atruppen-=1;
				}else;
				
			}
			anzeigen(verteidiger, atruppen);
			if (atruppen==0) {
				erde.gui1.textfeld.setText("Der Angriff ist fehlgeschlagen");
				erde.gui1.textfeld2.setText("");
				weiter = false;
				spiel.ausgewaehlt=null;
				spiel.auchausgewaehlt=null;
				
			} else if (verteidiger.getTruppen()==0) {
				defender.verloren(verteidiger);
				erde.gui1.textfeld.setText("der Angriff war erfolgreich");
				erde.gui1.textfeld2.setText("");
				verteidiger.setbesetzer(aktiv, atruppen);
				aktiv.setEroberer(true);
				aktiv.erobert(verteidiger);
				weiter =false;
				spiel.ausgewaehlt=null;
				spiel.auchausgewaehlt=null;
			}else {
				angreifer.verstaerken(atruppen);
				erde.gui1.textfeld2.setText(Integer.toString(atruppen));
				//erde.gui1.textfeld.setText("Soll der Angriff fortgesetzt werden?");
				erde.setStep(1);
				
				
			}
		}
			

			
	}
	/**
	 * gibt ergebnis des Kampfes graphisch aus
	 * @param v verteidigendes land 
	 * @param atruppen angreifende truppen
	 */
	void anzeigen (Land v, int atruppen)
	 {
		String str;
		str="Ergebnisse: \nAngreifer:";
		
		 for (int i = aktiv.ergebnis.length - 1; i >= 0; i--)
			 str+=(aktiv.ergebnis[i] + " "); 
		 str+="\nVerteidiger:  ";
		 for (int i = v.getBesetzer().ergebnis.length - 1; i >= 0; i--)
			 str+=(v.getBesetzer().ergebnis[i] + " "); 
		 str+="\nDer Angreifer hat noch ";
		 str+=atruppen;
		 str+=" Truppen \nDer Verteidiger hat noch ";
		 str+=v.getTruppen();
		 str+=" Truppen \nAngriff fortsetzen ?";
		 erde.gui1.textfeld.setText(str);
		 
		 
		 
		 
	 }
	
	/**
	 * verschieben von truppen zwischen zwei ländern
	 * @param von land aus dem verschoben wird
	 * @param zu land in das verschoben wird
	 */
	void truppenverschiebung(Land von,Land zu) {
		Land über;
		int anzahl, j;
		boolean verbunden = false;
		Scanner scan1 = new Scanner(System.in);
			ArrayList<Land> kopf = new ArrayList<Land>();
			kopf.add(von);
			for (int i=0; i< 42;i++) {
				erde.laender[i].setEntdeckt(false);
			}
			j=0;
			do {
				über=kopf.get(j);
				for (int i=0;i<über.nachbar.size();i++) {
					if (aktiv.besetzt.contains(über.nachbar.get(i))) {
						if (!über.nachbar.get(i).getEntdeckt()) {
							kopf.add(über.nachbar.get(i));
							über.nachbar.get(i).setEntdeckt(true);
						}
					}
				}
				j+=1;
			}while (j<kopf.size());
			if (kopf.contains(zu)) {
				verbunden = true;
			}else erde.gui1.textfeld.setText("Die Länder sind nicht verbunden");
		
		if(verbunden) {
				do {
				try {
					anzahl= Integer.parseInt(erde.gui1.textfeld2.getText());
					if (anzahl>=von.getTruppen()) anzahl=von.getTruppen()-1;
				} catch (NumberFormatException e) {
					erde.gui1.textfeld.setText("Du musst ein Int eingeben");
					anzahl= Math.max(1,von.getTruppen()-1 );
				}
				}while (anzahl>=von.getTruppen());
				
			von.verlust(anzahl);
			zu.verstaerken(anzahl);
			scan1.close();
			for(int i =0; i<kopf.size();i++) {
				kopf.get(i).setEntdeckt(true);
			}
		}
	}
	/**
	 * gibt aktiven spieler eine karte wenn dieser ein land erobert hat
	 */
	void endstep(){
		if (aktiv.getEroberer()) {
			deck.austeilen(aktiv);
			aktiv.setEroberer(false);
		}
	}
	public Spieler getAktiv() {
		return aktiv;
	}
	public void setAktiv(Spieler s) {
		aktiv = s;
	}
	public Deck getDeck() {
		return deck;
	}
	public void setDeck(Deck s) {
		deck=s;
	}
	public int getAnzahl() {
		return anzahl;
	}
	public void setAnzahl(int s) {
		anzahl=s;
	}
	public boolean getTausch() {
		return tausch;
	}
	public void setTausch(boolean s) {
		tausch =s;
	}
	void setSpiel(Spiel c) {
		spiel = c;
	}
	
	void setaktiv(Spieler s) {
		aktiv = s;
		
	}
	
	
}