package risiko;

import java.util.Random;
import java.util.Scanner;

//import combat.Spieler;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Phasen {

	/*public void PhasenTests() {
		// TODO Auto-generated method stub
		//Test 1
		Welt erde = new Welt();
		String a = erde.laender[34].getname();
		String b = "Japan";
		if (a.equals(b)){
			System.out.println("1 bestanden");
		}else {
			System.out.println("1 verkackt");
		}
		Spieler Player1 = new Spieler (true);
		Deck deck = new Deck();
		//Test 1.5
		Random rand = new Random();
		Land lal, lel;
		lal = erde.laender[rand.nextInt(42)];
		System.out.print(lal.name);
		System.out.println(" hat die Nachbarn:");
		for (int i=0; i< lal.nachbarschaft().size();i++) {
			lel =lal.nachbarschaft().get(i);
			System.out.println(lel.name);
		}
		System.out.println("kannste selber Prüfen");
		
		// Test 1.7
		int x=5;
		for (int i=0;i< erde.kontinente[x].enthalten.size();i++) {
			lel=(erde.kontinente[x].enthalten.get(i));
			System.out.print(lel.name);
			System.out.print("--");
		}
		System.out.print(erde.kontinente[x].wert);
		System.out.println();
		
		
		//Test 2
		for (int i=0;i<5;i++) {
		deck.austeilen(Player1);
		}
		if (deck.gefüllt==37 && Player1.hand.size()==5) {
			System.out.println("2 bestanden");
		}else System.out.print("2 verkackt");
		
		//Test 3
		Karten card1, card2, card3;
		card1= Player1.hand.get(0);
		card1.ansagen();
		card2= Player1.hand.get(1);
		card2.ansagen();
		card3= Player1.hand.get(2);
		card3.ansagen();
		System.out.println(deck.eintauschen(Player1, card1, card2, card3));
		
			if (Player1.hand.size()==2){
				System.out.println("3 bestanden");
			}else System.out.println("3: Hand stimmt nicht, es sei denn Karten waren nicht tauschbar");
		//Test 4
		Phasen game = new Phasen(erde, deck);
		for (int i=0;i<16;i++) {
			Player1.erobert(erde.laender[i]);
			erde.laender[i].setbesetzer(Player1, 7);
		}
		System.out.println("4?");
		game.setaktiv(Player1);
		game.verstaerkung();
		System.out.println("4!");
		
		//Test 5
		Spieler Player2 = new Spieler(true);
		Player2.erobert(erde.laender[20]);
		erde.laender[20].setbesetzer(Player2, 7);
		game.kampf(erde.laender[1], erde.laender[20]);
		
		
		//Test 6
		game.truppenverschiebung();
			
	}*/
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
	
	void setSpiel(Spiel c) {
		spiel = c;
	}
	
	void setaktiv(Spieler s) {
		aktiv = s;
		
	}
	//Verstärkungen fragt erst ob Karten getauscht werden, dann Gibt es pro 3 Gebiete eine Einheite, dann für jeden Kontinent
	void verstaerkung() {
		anzahl=0;
		Land land;
		Karten a,b,c;
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
	
	
	void kampf(Land angreifer, Land verteidiger) {
		boolean weiter = true;
		int atruppen, min;
		Spieler defender = verteidiger.getBesetzer();
		if (angreifer.nachbar.contains(verteidiger)) {
			if (angreifer.getBesetzer() != verteidiger.getBesetzer()) {
			}else {
				System.out.println("Du kannst nicht dein eigenes Land angfreifen mkay?");
				weiter=false;
			}
		}else {
			System.out.println("Flugzeuge wurden noch nicht erfunden");
			weiter = false;
		}
		System.out.println("Um doch nicht anzugreifen drücken Sie jetzt die 1");
		if (angreifer.getTruppen()==1)return;
		do {
			System.out.println("Mit wievielen Truppen möchtest du angreifen?");
			try {
				atruppen= Integer.parseInt(erde.gui1.textfeld2.getText());
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
				System.out.print(aktiv.ergebnis[i]);
				System.out.print(" vs ");
				System.out.println(defender.ergebnis[i]);
				if (aktiv.ergebnis[i]>defender.ergebnis[i]){
					verteidiger.verlust(1);
				}else if (aktiv.ergebnis[i]<=defender.ergebnis[i]) {
					atruppen-=1;
				}else System.out.println("Wie bin ich denn bitte hier gelandet?");
				
			}
			anzeigen(verteidiger, atruppen);
			if (atruppen==0) {
				erde.gui1.textfeld.setText("Der Angriff ist fehlgeschlagen");
				weiter = false;
				spiel.ausgewaehlt=null;
				spiel.auchausgewaehlt=null;
				
			} else if (verteidiger.getTruppen()==0) {
				defender.verloren(verteidiger);
				erde.gui1.textfeld.setText("der Angriff war erfolgreich");
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
	
	void anzeigen (Land v, int atruppen)
	 {
		String str;
		str="Ergebnisse: \nAngreifer:";
		
		 System.out.println("Ergebnisse:");
		 System.out.print("Angreifer:    ");
		 //for (int i = aktiv.ergebnis.length - 1; i >= 3-Math.min(atruppen,3); i--)
		 for (int i = aktiv.ergebnis.length - 1; i >= 0; i--)
			 str+=(aktiv.ergebnis[i] + " "); 
		 str+="\nVerteidiger:  ";
		 System.out.println("");
		 System.out.print("Verteidiger:  ");
		 //for (int i = v.besetzer.ergebnis.length - 1; i >= 2-Math.min(v.truppen, 2); i--)
		 for (int i = v.getBesetzer().ergebnis.length - 1; i >= 0; i--)
			 str+=(v.getBesetzer().ergebnis[i] + " "); 
		 System.out.println("");
		 System.out.print("Der Angreifer hat noch ");
		 System.out.print(atruppen);
		 System.out.println(" Truppen");
		 System.out.print("Der Verteidiger hat noch ");
		 System.out.print(v.getTruppen());
		 System.out.println(" Truppen");
		 str+="\nDer Angreifer hat noch ";
		 str+=atruppen;
		 str+=" Truppen \nDer Verteidiger hat noch ";
		 str+=v.getTruppen();
		 str+=" Truppen \nAngriff fortgesetzt ?";
		 erde.gui1.textfeld.setText(str);
		 
		 
		 
		 
	 }
	
	
	void truppenverschiebung(Land von,Land zu) {
		Land über;
		int anzahl, j;
		boolean verbunden = false;
		Scanner scan1 = new Scanner(System.in);
		//Hier Abfrage ob man überhaupt verschieben will
		//do {
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
			}else System.out.println("Die Länder sind nicht verbunden");
		//}while (!verbunden&&j<17);
		
		if(verbunden) {
				System.out.println("Wie viele Truppen sollen verschoben werden?");
				System.out.println(von.getTruppen());
				System.out.println(zu.getTruppen());
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
	
	
}