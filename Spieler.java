package risiko;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Spieler {
	public int territorien;
	public boolean mensch;
	public ArrayList<Land> besetzt;
	public ArrayList<Karten> hand;
	public int[] ergebnis;
	public boolean eroberer;
	public Color color;
	public int truppen;
	
	
	
	
	
	
	Spieler(boolean a){
		if(a) mensch = true;
		territorien = 0;
		besetzt = new ArrayList<Land>();
		hand = new ArrayList<Karten>();
		eroberer =false;
		ergebnis = new int[3];
	}
	
	void erobert (Land e) {
		besetzt.add(e);
		territorien +=1;
	}
	
	boolean hatgewonnen() {
		if (besetzt.size()>28) {
			return true;
		}else return false;
	}
	
	void verloren (Land v) {
		if (besetzt.contains(v)) {
			besetzt.remove(v);
			territorien -=1;	
		}else {
			System.out.println("Error: Land nicht da wos sein sollte");
		}
	}
	
	void gibKarte (Karten g) {
		hand.add(g);
	}
	
	void nimmKarte (Karten n) {
		if (hand.contains(n)) {
			hand.remove(n);
		}else {
			System.out.println("Error: Handkarte nicht vorhanden");
		}
	}
	
	Karten kartewählen() {
		//Placeholder Karte auswählen, über gui?
		Karten x;
		Random rand = new Random();
		int a = rand.nextInt(hand.size());
		x = hand.get(a);
		//hand.get(1).ansagen();
		return x;
		
	}
	
	void platzieren(int verstaerkung,Land land) {
		Land ziel = land;
			if (besetzt.contains(ziel)){
				ziel.verstaerken(verstaerkung);
				truppen -= verstaerkung;
			}else System.out.println("Das ist nicht dein Land");
		}

	

	
	void würfeln (int truppen, boolean verteidiger) {
		int max;
		int a =3;
		Arrays.fill(ergebnis, 0);
		Random würfel = new Random();
		if (verteidiger) a=2;
		if (a<truppen) {
			max = a;
		} else max = truppen;
		for (int i =0; i < max; i++) {
			ergebnis[i]= würfel.nextInt(6)+1;
		}
		Arrays.sort(ergebnis);
	 }
	Land landwahl () {
		Land ziel = new Land("placeholder");;
		return ziel;
	}
	
	
	
	
}
