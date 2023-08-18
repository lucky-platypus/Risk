package risiko;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KartenListener implements ActionListener {
	Spiel spiel;
	boolean karte1da = false;
	boolean karte2da = false;
	Karten karte1;
	Karten karte2;
	public KartenListener(Spiel test) {
		spiel=test;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.print(51345);
		System.out.print(spiel.runde.tausch);
		if (spiel.runde.tausch&&!karte1da) {
			for(int i =0;i<5;i++) {
				if(e.getSource()==spiel.erde.gui1.cart[i]) {
					karte1=spiel.erde.gui1.cart[i].getKarte();
					karte1da= true;
				}
			}
			
		}
		else if (spiel.runde.tausch&&!karte2da) {
			for(int i =0;i<5;i++) {
				if(e.getSource()==spiel.erde.gui1.cart[i]) {
					karte2=spiel.erde.gui1.cart[i].getKarte();
					karte2da= true;
				}
			}
			
		}
		else if (spiel.runde.tausch&&karte1da&&karte2da) {
			for(int i =0;i<5;i++) {
				if(e.getSource()==spiel.erde.gui1.cart[i]) {
					spiel.runde.anzahl=0;
					spiel.runde.anzahl+=spiel.runde.deck.eintauschen(spiel.runde.aktiv,karte1,karte2,spiel.erde.gui1.cart[i].getKarte());
					karte2da= false;
					karte1da=false;
					spiel.erde.gui1.kartenWechseln(spiel.runde.aktiv);
				}
			}
			
		}
		
	}

}
