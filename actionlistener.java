package risiko;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class actionlistener implements ActionListener {
	
	private Spiel spiel;
	private Welt welt;
	private int spieler = 0;
	private boolean landausgewaehlt= false;
	private boolean verlegt = false;
	private Land clicked;
	private Phasen runde;
	
	
	public actionlistener(Spiel spiel1) {
		spiel = spiel1;
		welt =spiel.erde;
		runde=spiel.getRunde();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(runde.getAktiv().getMensch()) {
		clicked = getClickedCountry(e);
		
		//Ausführung erste Phase
		if(welt.gui1.getPhase()==0) {

			verlegt =false;
			//gewähltes land überprüfen und truppen platzieren
			if(clicked.getBesetzer()==runde.getAktiv()) {
				Land give = clicked;
				runde.getAktiv().platzieren(1, clicked,runde.getAnzahl());
				runde.setAnzahl( Math.max(runde.getAnzahl()-1, 0));
				welt.gui1.textfeld.setText("");
			}else {
				welt.gui1.textfeld.setText("das ist nicht dein Land");
			}
		}
		//Ausführung zweite Phase
		else if(welt.gui1.getPhase()==1){
			if (!landausgewaehlt) {
						if (clicked.getBesetzer()==runde.getAktiv()) {
							spiel.ausgewaehlt=clicked;
							landausgewaehlt = true;
						}else welt.gui1.setText("Das ist nicht dein Land");
		
			}else if (landausgewaehlt){

						landausgewaehlt = false;
						spiel.auchausgewaehlt=clicked;
						runde.kampf(spiel.ausgewaehlt,spiel.auchausgewaehlt);
						
			}
		}
		//Ausführung dritte Phase
		else if(welt.gui1.getPhase()==2){
			if (!landausgewaehlt&&!verlegt) {

				spiel.ausgewaehlt=clicked;
				landausgewaehlt = true;
	
			}else if (landausgewaehlt&&!verlegt){

				landausgewaehlt = false;
				spiel.auchausgewaehlt=clicked;
				runde.truppenverschiebung(spiel.ausgewaehlt,spiel.auchausgewaehlt);
				verlegt=true;
				spiel.ausgewaehlt=null;
				spiel.auchausgewaehlt=null;
	
			}
		}
		//ausführung anfangs truppenplatzierung
		else {

					Land give = clicked;
					if (spiel.spielende.get(spieler).besetzt.contains(give) && spiel.spielende.get(spieler).getTruppen()>0) {
						spiel.spielende.get(spieler).platzieren(5,give,10);
						spieler = (spieler+1)%spiel.getPlayers();
						welt.gui1.setCurrentPlayer(spieler);

						welt.gui1.nextPlayer();
						welt.gui1.textfeld.setText("");
					}
					else if(spiel.spielende.get(spieler).besetzt.contains(give)) {
						welt.gui1.textfeld.setText("keine truppen mehr");
					}
					else {
						welt.gui1.textfeld.setText("Das ist nicht dein land");
						
					}
					while(!spiel.spielende.get(spieler).getMensch()) {
						spiel.spielende.get(spieler).getIch().verstEntscheidung(5);
						spieler = (spieler+1)%spiel.getPlayers();
					}
		}
		clicked =null;
		}
	}	
	/**
	 * finden des gelickten landes
	 * 
	 * @param e Action event des ActionListeners
	 * @return gelicktes Land
	 */
	private Land getClickedCountry(ActionEvent e) {
		for(int i = 0;i<42;i++) {
			if(e.getSource()==welt.gui1.list[i]) {
				return welt.laender[i];
			}
		}
		return null;
	}
}