package risiko;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class actionlistener implements ActionListener {
	public Spiel spiel;
	public int spieler = 0;
	public actionlistener(Spiel spiel1) {
		spiel = spiel1;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		for(int i = 0;i<42;i++) {
			if(e.getSource()== spiel.erde.gui1.list[i]) {
				Land give = spiel.erde.laender[i];
				if (spiel.spielende.get(spieler).besetzt.contains(give) && spiel.spielende.get(i%spiel.players).truppen>0) {
					spiel.spielende.get(spieler).platzieren(3,give);
					spieler = (spieler+1)%spiel.players;
					spiel.erde.gui1.currentPlayer++;
					spiel.erde.gui1.nextPlayer();
				}
				else if(spiel.spielende.get(spieler).besetzt.contains(give)) {
					
				}
				else {
					System.out.println("Das ist nicht dein Land");
				}
		}


		}



}
}
