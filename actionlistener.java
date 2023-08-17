package risiko;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class actionlistener implements ActionListener {
	public Spiel spiel;
	public int spieler = 0;
	boolean landausgewaehlt= false;
	boolean verlegt = false;
	//Land ausgewaehlt, auchausgewaehlt;
	public actionlistener(Spiel spiel1) {
		spiel = spiel1;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(spiel.erde.gui1.phase==0) {
			verlegt =false;
			for(int i = 0;i<42;i++) {
				if(e.getSource()== spiel.erde.gui1.list[i]) {
					Land give = spiel.erde.laender[i];
					spiel.runde.aktiv.platzieren(1, give,spiel.runde.anzahl);
					spiel.runde.anzahl-=1;
				}
			}
		}
		else if(spiel.erde.gui1.phase==1){
			if (!landausgewaehlt) {
				for(int i = 0;i<42;i++) {
					if(e.getSource()== spiel.erde.gui1.list[i]) {
						if (spiel.erde.laender[i].besetzer==spiel.runde.aktiv) {
							spiel.ausgewaehlt=spiel.erde.laender[i];
							landausgewaehlt = true;
						}else spiel.erde.gui1.setText("Das ist nicht dein Land");
					}
				}
			}else if (landausgewaehlt){
				for(int i = 0;i<42;i++) {
					if(e.getSource()== spiel.erde.gui1.list[i]) {
						landausgewaehlt = false;
						spiel.auchausgewaehlt=spiel.erde.laender[i];
						spiel.runde.kampf(spiel.ausgewaehlt,spiel.auchausgewaehlt);
						
					}
			}
			}
		}
		else if(spiel.erde.gui1.phase==2){
			if (!landausgewaehlt&&!verlegt) {
				for(int i = 0;i<42;i++) {
					if(e.getSource()== spiel.erde.gui1.list[i]) {
						spiel.ausgewaehlt=spiel.erde.laender[i];
						landausgewaehlt = true;
					}
				}
			}else if (landausgewaehlt&&!verlegt){
				for(int i = 0;i<42;i++) {
					if(e.getSource()== spiel.erde.gui1.list[i]) {
						landausgewaehlt = false;
						spiel.auchausgewaehlt=spiel.erde.laender[i];
						spiel.runde.truppenverschiebung(spiel.ausgewaehlt,spiel.auchausgewaehlt);
						verlegt=true;
						spiel.ausgewaehlt=null;
						spiel.auchausgewaehlt=null;
						//spiel.runde.endstep();
					}
			}
			}
		}
		else {
			for(int i = 0;i<42;i++) {
				if(e.getSource()== spiel.erde.gui1.list[i]) {
					Land give = spiel.erde.laender[i];
					if (spiel.spielende.get(spieler).besetzt.contains(give) && spiel.spielende.get(spieler).truppen>0) {
						spiel.spielende.get(spieler).platzieren(5,give,10);
						spieler = (spieler+1)%spiel.players;
						spiel.erde.gui1.currentPlayer=spieler;
						spiel.erde.gui1.nextPlayer();
						spiel.erde.gui1.textfeld.setText("");
					}
					else if(spiel.spielende.get(spieler).besetzt.contains(give)) {
						spiel.erde.gui1.textfeld.setText("keine truppen mehr");
					}
					else {
						spiel.erde.gui1.textfeld.setText("Das ist nicht dein land");
						
					}
				}


		}



}
}
	}