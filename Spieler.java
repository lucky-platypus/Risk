package risiko;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Spieler {
	private int territorien;
	private boolean mensch;
	public ArrayList<Land> besetzt;
	public ArrayList<Karten> hand;
	public int[] ergebnis;
	private boolean eroberer;
	private Color color;
	private int truppen;
	private Bot ich;
	private boolean verloren;
	
	
	
	
	
	
	Spieler(boolean a){
		mensch = a;
		territorien = 0;
		besetzt = new ArrayList<Land>();
		hand = new ArrayList<Karten>();
		eroberer =false;
		ergebnis = new int[3];
	}
	
	/**
	 * fügt ein erobertes land hinzu
	 * @param e erobertes land
	 */
	void erobert (Land e) {
		besetzt.add(e);
		territorien +=1;
	}
	
	/**
	 * bestimmt ob der spieler gewonnen hat(mehr als 28 länder)
	 * @return hat gewonnen
	 */
	
	boolean hatgewonnen() {
		if (besetzt.size()>28) {
			return true;
		}else return false;
	}
	boolean hatVerloren(){
		if(besetzt.size()<=0) {
			return true;
		}else return false;
	}
	
	/**
	 * entfernt ein verlorenes Land
	 * @param v verlorenes Land
	 */
	void verloren (Land v) {
		if (besetzt.contains(v)) {
			besetzt.remove(v);
			territorien -=1;	
		}else {
			
		}
	}
	
	/**
	 * fügt der Hand eine karte hinzu
	 * @param g hinzuzufügende karte 
	 */
	void gibKarte (Karten g) {
		hand.add(g);
	}
	/**
	 * entfernt eine karte 
	 * @param n zu entfernende karte
	 */
	void nimmKarte (Karten n) {
		if (hand.contains(n)) {
			hand.remove(n);
		}else {
		}
	}
	/**
	 * Platziert truppen in einem land
	 * 
	 * @param verstaerkung Anzahl an neuen truppen
	 * @param land Land zum platzieren 
	 * @param test üperprüfen ob noch truppen vorhanden sind(wird im actionlistener benötigt)
	 */
	void platzieren(int verstaerkung,Land land,int test) {
		Land ziel = land;
			if (besetzt.contains(ziel)){
				if(test>0){
					ziel.verstaerken(verstaerkung);
					truppen -= verstaerkung;
				}
			}else ;
		}

	

	/**
	 * würfelt das ergegniss für den kampf
	 *  
	 * @param truppen anzahl der truppen die kämpfen
 	 * @param verteidiger gibt an ob verteidiger oder angreifer
	 */
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
	public void setBot(Bot bot) {
		ich =bot;
	}
	public int getTerritorien() {
		return territorien;
	}
	public boolean getMensch() {
		return mensch;
	}
	public void setMensch(Boolean s) {
		mensch =s;
	}
	public boolean getEroberer() {
		return eroberer;
	}
	public void setEroberer(Boolean s) {
		eroberer =s;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color s) {
		color =s;
	}
	public int getTruppen() {
		return truppen;
	}
	public void setTruppen(int s) {
		truppen =s;
	}
	public Bot getIch() {
		return ich;
	}
	public void setIch(Bot s) {
		ich =s;
	}
	
	
}