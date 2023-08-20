package risiko;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KartenListener implements ActionListener {
	private Spiel spiel;
	private boolean karte1da = false;
	private boolean karte2da = false;
	private Phasen runde;
	private Karten karte1;
	private Karten karte2;
	
	
	public KartenListener(Spiel test) {
		spiel=test;
		runde = spiel.getRunde();
	}
	/**
	 * Action der karten ausführen
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(runde.getAktiv().getMensch()) {
			if (runde.getTausch()&&!karte1da) {
				for(int i =0;i<5;i++) {
					if(e.getSource()==spiel.erde.gui1.cart[i]) {
						karte1=spiel.erde.gui1.cart[i].getKarte();
						karte1da= true;
					}
				}
			}
			else if (runde.getTausch()&&!karte2da) {
				for(int i =0;i<5;i++) {
					if(e.getSource()==spiel.erde.gui1.cart[i]) {
						karte2=spiel.erde.gui1.cart[i].getKarte();
						karte2da= true;
					}
				}		
			}
			else if (runde.getTausch()&&karte1da&&karte2da) {
				for(int i =0;i<5;i++) {
					if(e.getSource()==spiel.erde.gui1.cart[i]) {
						runde.setAnzahl(runde.getAnzahl()+runde.getDeck().eintauschen(runde.getAktiv(),karte1,karte2,spiel.erde.gui1.cart[i].getKarte()));
						karte2da= false;
						karte1da=false;
						spiel.erde.gui1.kartenWechseln(runde.getAktiv());
					}
				}
			
			}
		
		}
	}

}
