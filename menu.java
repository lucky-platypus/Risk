package risiko;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.util.Scanner;

public class menu implements ActionListener {
		
	private double aufloesungX=10;
	private double aufloesungY=10;
	
	private JComboBox box;
	private JComboBox box2;
	private JComboBox box3;
	
	private double X[]= {1920,1920,1280,1280};
	private double Y[]= {1200,1080,800,720};
	
	private JButton weiter;
	private JButton laden;
	
	private Welt welt;
	private Spiel spiel;
	private Deck deck;
	private Phasen runde;
	
	private int playerCount;
	private int botCount;
	/**
	 * legt aktionen der speichern und laden knöpfe fest
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		aufloesungX = (X[box.getSelectedIndex()]/1920);
		aufloesungY = Y[box.getSelectedIndex()]/1200;
		playerCount=box2.getSelectedIndex()+1;
		botCount =Math.min(box3.getSelectedIndex(),playerCount);
		
		
		if (e.getSource()==weiter) {
			try {
				spielSetup();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource()==laden) {
			try {
				spielLaden();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	public menu() {

	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.setSize(200, 250);
	    frame.setVisible(true);
        JPanel panel = new JPanel();
 
        JLabel frage = new JLabel("                 Auflösung                   ");
        panel.add(frage);
 
        
        String comboBoxListe[] = {"1920x1200","1920x1080","1280x800","1280x720"};
        String comboBoxListe2[] = {"1","2","3","4","5","6"};
        String comboBoxListe3[] = {"0","1","2","3","4","5"};
 
    
       box = new JComboBox(comboBoxListe);
       box2 = new JComboBox(comboBoxListe2);
       box3 =new JComboBox(comboBoxListe3);
 
       box.setBounds(100, 100, 100, 10);
       panel.add(box);
       JLabel frage2 = new JLabel("                 Spieler                  ");
       panel.add(frage2);
       box2.setBounds(100, 100, 100, 10);
       panel.add(box2);
       JLabel frage3 = new JLabel("            Wie viel davon Bots             ");
       panel.add(frage3);
       panel.add(box3);
       JLabel frage4 = new JLabel("                                               ");
       panel.add(frage4);

       	weiter = new JButton();
       	weiter.setSize(10, 100);
       	weiter.setText("Weiter");
       	weiter.addActionListener(this);
       	
       	laden = new JButton();
       	laden.setSize(10, 100);
       	laden.setText("Laden");
       	laden.addActionListener(this);
       	
       	
       	panel.add(laden);
       	panel.add(weiter);
        frame.add(panel);
        frame.setVisible(true);

        
 
    }
	
		public int getPlayerCount() {
			return this.playerCount;
		}
		/**
		 * erstellt ein neues spiel
		 * @throws IOException
		 */
		public void spielSetup() throws IOException{
			GUI gui =new GUI(this.aufloesungX,this.aufloesungX,this.playerCount);
			welt = new Welt(gui);
			deck = new Deck();
			runde = new Phasen(welt, deck);
			spiel = new Spiel(this.getPlayerCount(),this.botCount,welt, runde);
			runde.setSpiel(spiel);
			
			
			
			
			gui.setRunde(runde);
			gui.setSpiel(spiel);
			gui.setKAL();
			spiel.landverteilung(welt);
			spiel.truppenverteilung(welt);
		}
		/**
		 * Liest einen gespeicherten spielstand ein 
		 * @throws IOException
		 */
		
		public void spielLaden()throws IOException{
			String gelesen;
			int landzahl,a,b;
			Spieler dran;
			ArrayList<Karten> stack = new ArrayList<Karten>();
			BufferedReader lies = new BufferedReader(new FileReader(".\\speicherstand.txt"));
			gelesen =lies.readLine();
			Scanner scan = new Scanner(gelesen);
			playerCount=scan.nextInt();
			botCount = scan.nextInt();
			GUI gui =new GUI(this.aufloesungX,this.aufloesungX,this.playerCount);
			welt = new Welt(gui);
			deck = new Deck();
			runde = new Phasen(welt, deck);
			spiel = new Spiel(this.getPlayerCount(),botCount,welt, runde);
			runde.setSpiel(spiel);
			
			gui.setRunde(runde);
			gui.setSpiel(spiel);
			gui.setKAL();
			for (int i=0;i<playerCount;i++) {
				dran = spiel.spielende.get(i);
				gelesen =lies.readLine();
				scan = new Scanner(gelesen);
				if (scan.nextInt()==1)dran.setMensch(true);
				else dran.setMensch(false);
				landzahl = scan.nextInt();
				for (int j=0;j<landzahl;j++) {
					a=scan.nextInt();
					b=scan.nextInt();
					dran.erobert(welt.laender[a]);
					welt.laender[a].setbesetzer(dran, b);
				}
				landzahl= scan.nextInt();
				for (int k=0;k<landzahl;k++) {
					a=scan.nextInt();
					dran.gibKarte(deck.deck.get(a));
					stack.add(deck.deck.get(a));
				}
			}
			for (int l=0;l<stack.size();l++) {
				deck.deck.remove(stack.get(l));
			}
			spiel.erde.gui1.setPhase(2);
			gelesen =lies.readLine();
			scan.close();
			scan = new Scanner(gelesen);
			spiel.erde.gui1.setCurrentPlayer(scan.nextInt());
			actionlistener lis = new actionlistener(spiel);
			for(int i = 0; i <42;i++) {
				spiel.erde.gui1.list[i].addActionListener(lis);

			}
			scan.close();
			lies.close();
		}
		
	}
	
