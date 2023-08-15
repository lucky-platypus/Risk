package risiko;

import java.util.Random;
import java.util.Scanner;

//import combat.Spieler;

import java.util.ArrayList;

public class Phasen {

	public static void main(String[] args) {
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
		
		
		
	}
	public Spieler aktiv;
	private int phase;
	public Welt erde;
	public Deck deck;
	
	Phasen(Welt a, Deck b){
		erde = a;
		deck = b;
		phase=0;	
	}
	
	void setaktiv(Spieler s) {
		aktiv = s;
		
	}
	//Verstärkungen fragt erst ob Karten getauscht werden, dann Gibt es pro 3 Gebiete eine Einheite, dann für jeden Kontinent
	void verstaerkung() {
		int anzahl=0;
		boolean ja=false;
		Karten a,b,c;
		do {
			System.out.println((aktiv.hand.size()));
			if (aktiv.hand.size()>2&&aktiv.hand.size()<5) {
				System.out.println("Möchtest du Karten eintauschen?");
				//Eingabe ob man tauschen möchte	
				Scanner scan = new Scanner(System.in);
				if (scan.nextInt()==1) ja=true;
			}else if (aktiv.hand.size()==5) {
				ja=true;
			}else if (aktiv.hand.size()>5) System.out.println ("Hand zu groß, das darf nicht passieren");
			if (ja) {
				do {
					a= aktiv.kartewählen();
					b= aktiv.kartewählen();
					c= aktiv.kartewählen();
				} while (a==b || b==c);
				anzahl+=deck.eintauschen(aktiv,a,b,c);
			}
		}while (anzahl==0 && ja);
		if (aktiv.territorien>=9) {
			anzahl+=aktiv.territorien/3;
		}else anzahl+=3;
		for (int i=0;i<6;i++) {
			anzahl+=erde.kontinente[i].Kontinentkontrolle(aktiv);
		}
		System.out.print("Du bekommst ");
		System.out.print(anzahl);
		System.out.println(" Verstärkungen");
		//aktiv.platzieren(anzahl);

		
	}
	
	void kampfphase() {
		Land von, zu;
		boolean tiptop = false;
		do {
			von = aktiv.landwahl();
			zu = aktiv.landwahl();
			if (von.nachbar.contains(zu)) {
				if (von.besetzer != zu.besetzer) {
					tiptop=true;
				}else System.out.println("Du kannst nicht dein eigenes Land angfreifen mkay?");
			}else System.out.println("Flugzeuge wurden noch nicht erfunden");
			System.out.println("Um doch nicht anzugreifen drücken Sie jetzt die 1");
			//Hier die Option Phase zu wechseln
		} while (!tiptop);
		kampf (von, zu);
		
	}
	
	
	
	void kampf(Land angreifer, Land verteidiger) {
		boolean weiter = true;
		int atruppen, min;
		Spieler defender = verteidiger.besetzer;
		Scanner scan = new Scanner(System.in);
		do {
			System.out.println("Mit wievielen Truppen möchtest du angreifen?");
			
			atruppen = scan.nextInt();
		}while (atruppen >=angreifer.truppen);
		angreifer.verlust(atruppen);
		while (weiter) {
			aktiv.würfeln(atruppen, false);
			defender.würfeln(verteidiger.truppen, true);
			min = 3- Math.min(2,Math.min(atruppen, verteidiger.truppen));
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
				System.out.println("Der Angriff ist fehlgeschlagen");
				weiter = false;
				
			} else if (verteidiger.truppen==0) {
				System.out.println("der Angriff war erfolgreich");
				verteidiger.setbesetzer(aktiv, atruppen);
				aktiv.eroberer=true;
				aktiv.erobert(verteidiger);
				weiter =false;
			}else {
				System.out.println("Soll der Angriff abgebrochen werden?");
				if (scan.nextInt()==1) {
					weiter =false;
					angreifer.verstaerken(atruppen);
				}
				
			}
		}
			
			
			
	}
	
	void anzeigen (Land v, int atruppen)
	 {
		 System.out.println("Ergebnisse:");
		 System.out.print("Angreifer:    ");
		 //for (int i = aktiv.ergebnis.length - 1; i >= 3-Math.min(atruppen,3); i--)
		 for (int i = aktiv.ergebnis.length - 1; i >= 0; i--)
			    System.out.print(aktiv.ergebnis[i] + " "); 
		 System.out.println("");
		 System.out.print("Verteidiger:  ");
		 //for (int i = v.besetzer.ergebnis.length - 1; i >= 2-Math.min(v.truppen, 2); i--)
		 for (int i = v.besetzer.ergebnis.length - 1; i >= 0; i--)
			    System.out.print(v.besetzer.ergebnis[i] + " "); 
		 System.out.println("");
		 System.out.print("Der Angreifer hat noch ");
		 System.out.print(atruppen);
		 System.out.println(" Truppen");
		 System.out.print("Der Verteidiger hat noch ");
		 System.out.print(v.truppen);
		 System.out.println(" Truppen");
		 
		 
		 
	 }
	
	
	void truppenverschiebung() {
		Land von,zu,über;
		int anzahl, j;
		boolean verbunden = false;
		Scanner scan = new Scanner(System.in);
		//Hier Abfrage ob man überhaupt verschieben will
		//do {
			ArrayList<Land> kopf = new ArrayList<Land>();
			//von = aktiv.landwahl();
			von = erde.laender[2];
			//zu=aktiv.landwahl();
			zu = erde.laender[3];
			kopf.add(von);
			for (int i=0; i< 42;i++) {
				erde.laender[i].entdeckt=false;
			}
			j=0;
			do {
				über=kopf.get(j);
				for (int i=0;i<über.nachbar.size();i++) {
					if (aktiv.besetzt.contains(über.nachbar.get(i))) {
						if (!über.nachbar.get(i).entdeckt) {
							kopf.add(über.nachbar.get(i));
							über.nachbar.get(i).entdeckt = true;
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
			do {
				System.out.println("Wie viele Truppen sollen verschoben werden?");
				anzahl = scan.nextInt();
			} while (anzahl>von.truppen-1);
			von.truppen-=anzahl;
			zu.truppen+=anzahl;
		}
		scan.close();
		
	}
	
	
	
}