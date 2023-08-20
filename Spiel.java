package risiko;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.awt.event.ActionEvent;


public class Spiel {
	private boolean gewinner;
	private boolean speichernerlaubt=false;
	private int players;
	private Phasen runde;
	public Land ausgewaehlt, auchausgewaehlt;
	ArrayList<Spieler> spielende;
	private Color colors[] = {Color.red,Color.green,Color.pink,Color.yellow,Color.ORANGE,Color.MAGENTA};
	Welt erde = new Welt();

	Spiel (int play,int bots,Welt welt, Phasen x){
		spielende = new ArrayList<Spieler>();
		Spieler player;
		gewinner =false;
		players=play;
		erde = welt;
		runde =x;
		for(int i=0; i<players;i++) {
			if (i<players-bots) {
				player = new Spieler (true);
			}else {
				player = new Spieler (false);
				Bot bot = new Bot(this,player);
				player.setBot(bot);
				
			}
			spielende.add(player);
			spielende.get(i).setColor(colors[i]);
		}
		runde.setaktiv(spielende.get(erde.gui1.getCurrentPlayer()));
	}
	void landverteilung(Welt erde) {
		int dran=0;
		int halte;
		Random rand = new Random();
		ArrayList<Land> hold = new ArrayList<Land>();
		for (int i=0; i < erde.laender.length;i++) {
			hold.add(erde.laender[i]);
		}
		while (hold.size()>0) {
			halte = rand.nextInt(hold.size());
			spielende.get(dran).erobert(hold.get(halte));
			spielende.get(dran).besetzt.get(spielende.get(dran).besetzt.size()-1).setbesetzer(spielende.get(dran), 1);
			hold.remove(halte);
			dran+=1;
			if (dran>=spielende.size()) dran=0;
		}

	}

	void Runde(int phase){
		runde.setaktiv(spielende.get(erde.gui1.getCurrentPlayer()));
		if (phase ==0) {
			if(runde.getAktiv().getMensch()) {
			runde.verstaerkung();
			}else {
				runde.getAktiv().getIch().aiVerstaerkung();
			}
		}else if (phase==1) {
			
			if(!runde.getAktiv().getMensch()) {
			runde.getAktiv().getIch().erobern();
			}
			if (runde.getAktiv().hatgewonnen()) {
				gewinner =true;
				return;
			}
		}else if (phase==2) {
			if(!runde.getAktiv().getMensch()) {
			runde.getAktiv().getIch().rochade();
			}
			runde.endstep();
			speichernerlaubt=true;
			erde.gui1.textfeld.setText("Sie können jetzt speichern");
			if (runde.getAktiv().hatgewonnen()) {
				gewinner =true;
				return;
			}
		}else System.out.println("nope");


	}
	void truppenverteilung (Welt erde){
		int verteilbar;
		verteilbar = 50 - 5*spielende.size();
		for (int i =0;i<spielende.size();i++) {
			spielende.get(i).setTruppen(verteilbar);
		}
		actionlistener lis = new actionlistener(this);
		for(int i = 0; i <42;i++) {
			erde.gui1.list[i].addActionListener(lis);

		}

			verteilbar -=1;
		}

	void save() {
		int a;
		Spieler spieler;
		
		
		try {
		      File speicher = new File("speicherstand.txt");
		      if (speicher.createNewFile()) {
		        System.out.println("Neuer Speicherstand " + speicher.getName());
		      } else {
		        System.out.println("Speicherstand wurde überschrieben");
		      }
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		try {
			FileWriter schreiben = new FileWriter("speicherstand.txt");
			a=players;
			System.out.println("blblblblb");
			schreiben.write(a+"\n");
			for (int i=0;i<a;i++) {
				spieler=spielende.get(i);
				if (spieler.getMensch()) {
					schreiben.write (1+" ");
				}else schreiben.write(0 +" ");
				schreiben.write(spieler.besetzt.size()+" ");
				for (int j=0; j<42;j++) {
					if (erde.laender[j].getBesetzer() ==spieler) {
						schreiben.write(j +" ");
						schreiben.write(erde.laender[j].getTruppen() +" ");
					}	
				}
				schreiben.write(" 9999 ");
				schreiben.write(spieler.hand.size()+" ");
				for (int k=0;k<spieler.hand.size();k++) {
					schreiben.write(spieler.hand.get(k).getPosition() +" ");
				}
				schreiben.write("\n");
			}
			a=erde.gui1.getCurrentPlayer();
			schreiben.write(a+"\n");
			//schreiben.write("test");
			schreiben.close();
		} catch (IOException e) {
			System.out.println("hat nicht geklappt");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		
	}
	public boolean getGewinner() {
		return gewinner;
	}
	public boolean getSpeichernerlaubt() {
		return speichernerlaubt;
	}
	public void setSpeichernerlaubt(boolean s) {
		speichernerlaubt =s;
	}
	public int getPlayers() {
		return players;
	}
	public Phasen getRunde() {
		return runde;
	}
	

	}