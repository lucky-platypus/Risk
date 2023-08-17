package risiko;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class menu implements ActionListener {
		
	public double aufloesungX=10;
	public double aufloesungY=10;
	
	public JComboBox box;
	public JComboBox box2;
	
	public double X[]= {1920,1920,1280,1280};
	public double Y[]= {1200,1080,800,720};
	
	public Welt welt;
	public Spiel spiel;
	public Deck deck;
	public Phasen runde;
	
	private int playerCount;
	@Override
	public void actionPerformed(ActionEvent e) {
		
		aufloesungX = (X[box.getSelectedIndex()]/1920);
		aufloesungY = Y[box.getSelectedIndex()]/1200;
		playerCount=box2.getSelectedIndex()+1;
		
		try {
			spielSetup();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
 
    
       box = new JComboBox(comboBoxListe);
       box2 = new JComboBox(comboBoxListe2);
 
       box.setBounds(100, 100, 100, 10);
       panel.add(box);
       JLabel frage2 = new JLabel("                 Spieler                  ");
       panel.add(frage2);
       box2.setBounds(100, 100, 100, 10);
       panel.add(box2);
       JLabel frage3 = new JLabel("                                               ");
       panel.add(frage3);

       	JButton weiter = new JButton();
       	weiter.setSize(10, 100);
       	weiter.setText("Weiter");
       	weiter.addActionListener(this);
       	
       	
       	panel.add(weiter);
        frame.add(panel);
        frame.setVisible(true);

        
 
    }
	
		public int getPlayerCount() {
			return this.playerCount;
		}
	
		public void spielSetup() throws IOException{
			GUI gui =new GUI(this.aufloesungX,this.aufloesungX,this.playerCount);
			welt = new Welt(gui);
			deck = new Deck();
			runde = new Phasen(welt, deck);
			spiel = new Spiel(this.getPlayerCount(),0,welt, runde);
			runde.setSpiel(spiel);
			
			
			
			
			gui.setRunde(runde);
			gui.setSpiel(spiel);
			spiel.landverteilung(welt);
			spiel.truppenverteilung(welt);
		}
		
	}
	