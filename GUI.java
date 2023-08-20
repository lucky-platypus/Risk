package risiko;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.*;
public class GUI implements ActionListener{
		JButton list[] = new JButton[42];
		JLabel labellist[] =new JLabel[42];
		JLabel playerlist[] = new JLabel[0];
		JLabel phasenlist[] = new JLabel[3];
		kartenGraph cart[] = new kartenGraph[5];
		private int x[] = {150,280,560,280,400,490,280,400,310,400,420,520,450,670,790,910,650,660,800,810,
			720,830,820,900,830,960,1040,1140,1250,1370,1230,1230,1020,1200,1380,930,
			1090,1200,1200,1350,1270,1390};
		private int y[] = {200,180,150,270,293,290,370,400,480,570,680,640,765,250,220,300,360,480,360,420,
			615,580,725,670,810,830,280,220,170,180,295,370,400,470,370,500,510,560,700,
			680,830,800};
		private int withd[] = {110,150,110,110,80,110,110,110,110,110,110,110,120,110,120,110,110,110,110,
			110,140,110,130,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,
			110,110,110,110,140,140};
		private int height[] = {30,50,30,30,30,30,40,40,40,30,30,30,30,30,30,30,40,40,40,40,30,30,
			30,30,60,40,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,50,30,30};
		private Color colors[] = {Color.red,Color.green,Color.pink,Color.yellow,Color.ORANGE,Color.MAGENTA};
        JButton nextturn=new JButton();
        JButton savegame=new JButton();
        JButton ja = new JButton();
        JButton nein = new JButton();
        JTextArea textfeld = new JTextArea();
        JTextArea textfeld2 = new JTextArea();
        private int currentPlayer;
        private int phase = 30;
        private int chooseLand= 100;
        private Phasen phasen;
        private Spiel spiel;
	public GUI(double scaleX,double scaleY,int playerCount) throws IOException {
		
        JFrame frame = buildFrame(scaleX,scaleY);
        final BufferedImage image = ImageIO.read(new File(".\\Welt.jpg"));
        buttonSetup(frame,scaleX,scaleY);
        
        //erstellen der karte mit eingelesenem bild
        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 10, 10,(int)(1620*scaleX),(int)(1000*scaleY), null);
            }
        };
        //Erstellen der Spieleranzeige
		playerlist = new JLabel[playerCount];
        for(int i=0; i<playerCount;i++) {
        	playerlist[i] = (buildPlayerLabel((int)(1720*scaleX),(int)((i*50+5)*scaleY),"Player"+(i+1), 100, 50));
        	frame.add(playerlist[i]);
        }
        //Erstellen der Phasenanzeige
        for(int i=0; i<3;i++) {
        	phasenlist[i] = (buildPlayerLabel((int)(1720*scaleX),(int)((((i*50)+5)+305)*scaleY),"Phase"+(i+1), 100, 50));
        	frame.add(phasenlist[i]);
        }
        labelSetup(list,frame);
        kartenSetup(frame,scaleX,scaleY);
   
        nextturn = thisButton("weiter",1640,1010,260,130,scaleX,scaleY);        
        savegame = thisButton("Speichern",1420,1010,200,130,scaleX,scaleY);
        ja = thisButton("JA",1640,900,125,100,scaleX,scaleY);
        nein = thisButton("NEIN",1775,900,125,100,scaleX,scaleY);

        textfeld =textfeld("Ausgabefeld",1640,500,260,200,scaleX,scaleY);
        textfeld2 =textfeld("Eingabefeld",1640,705,260,190,scaleX,scaleY);
        textfeld =textfeld("Ausgabefeld",1640,500,260,300,scaleX,scaleY);
        textfeld2 =textfeld("Eingabefeld",1640,805,260,90,scaleX,scaleY);

        frame.add(savegame);
        frame.add(nextturn);
        frame.add(ja);
        frame.add(nein);
        frame.add(textfeld);
        frame.add(textfeld2);
        frame.add(pane);
        frame.setVisible(true);
		nextPlayer();
    }
	/**
	 * erstellen des frames
	 * @param aX skalierung x
	 * @param aY skalierung y
	 * @return erstellter frame
	 */
    private static JFrame buildFrame(double aX,double aY) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize((int)(1920.0*aX),(int) (1200.0*aY));
        frame.setVisible(true);
        return frame;
    }
    /**
	/**
	 * 	erstellen eines Länderbuttons
	 * 
	 * @param s Name des landes
	 * @param x X position 
	 * @param y Y position
	 * @param height Höhe
	 * @param withd Breite 
	 * @param scaleX Skalierung X
	 * @param scaleY Skalierung Y
     * @param fontSize
     * @return erstellter button
     */
    private static JButton buildButton(int x, int y,String name, int height,int withd, int fontSize) {
        JButton button = new JButton();
		Font font = new Font("Monospaced", Font.BOLD,fontSize);
		button.setFont(font);
        button.setBounds(x, y, height, withd);
        button.setVisible(true);
        button.setContentAreaFilled(true);
        button.setBackground(Color.gray);
        button.setText(name);
        return button;
    }
    
	/**
	 * 	erstellen der Spieleranzeige 
	 * 
	 * @param s Name des spielers
	 * @param x X position 
	 * @param y Y position
	 * @param height Höhe
	 * @param withd Breite 
	 * @param scaleX Skalierung X
	 * @param scaleY Skalierung Y
	 * @return erstelltes Label
	 */
    private static JLabel buildPlayerLabel(int x, int y, String name,int withd,int height) {
        JLabel label = new JLabel();
        label.setBounds(x, y, withd, height);
        label.setVisible(true);
        label.setText(name);
        label.setOpaque(true);
        label.setBackground(Color.white);
        return label;
    }
    /**
     * erstellen aller länderbuttons
     * @param frame Frame der GUI
     * @param xScaling Skalierung X
     * @param yScaling Skalierung Y
     */
    private void buttonSetup(JFrame frame,double xScaling,double yScaling) {
        BufferedReader lies;
		try {
			lies = new BufferedReader(new FileReader(".\\Laender_html.txt"));
			for (int i =0;i<42;i++) {
				try {
		        	list[i]= buildButton((int)(x[i]*xScaling),(int)(y[i]*yScaling),lies.readLine(),(int)(withd[i]*xScaling*1.05),(int)(height[i]*yScaling*1.1),(int)(11*(yScaling)));
		        	frame.add(list[i]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    /**
     * aktionen der Ja/Nein/Weiter/Speicher buttons
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!spiel.getGewinner()) {
			
		 if(e.getSource() == nextturn) {
			 	textfeld.setText("");
			 	textfeld2.setText("");
			 	if(spiel.getRunde().getAktiv().hatVerloren()) {
			 		currentPlayer =(currentPlayer+1)%playerlist.length;
			 	}else {
				 spiel.setSpeichernerlaubt(false);
				 spiel.erde.setStep(0);
				 if(phase==30) {
					 phase=0;
					 currentPlayer=0;
					 this.nextPhase();
					 this.nextPlayer();
				 }else {
					 if(phase ==0) {
						 if(spiel.getRunde().getAktiv().hand.size()<5)	{
							 phase =(phase+1)%3;
						 }else {
							 textfeld.setText("du musst karten eintauschen");
						 }
						 }
							 
					 else {
					 phase =(phase+1)%3;}}
				 	if(spiel.getRunde().getAktiv().hatVerloren()) {
				 		currentPlayer =(currentPlayer+1)%playerlist.length;
				 	}
				 	spiel.Runde(phase);
				 	this.nextPhase();
				 	this.nextPlayer();
				 	if (phase == 2) {
				 		currentPlayer =(currentPlayer+1)%playerlist.length;
				 		
				 	}
				 	
				 	spiel.erde.gui1.kartenWechseln(spiel.getRunde().getAktiv());

			 	}
		 }
		 else if(e.getSource() == ja && spiel.getRunde().getAktiv().getMensch()) {
			 if (spiel.erde.getStep()==1) {
				 if(spiel.ausgewaehlt !=null&&spiel.auchausgewaehlt !=null)
				 spiel.getRunde().kampf(spiel.ausgewaehlt , spiel.auchausgewaehlt);
			 }
			 
		 }else if(e.getSource() == nein && spiel.getRunde().getAktiv().getMensch()) {
			 if (spiel.erde.getStep()==1) {
				 spiel.ausgewaehlt=null;
				 spiel.auchausgewaehlt=null;
				 textfeld2.setText("");
				 
			 }
			 
		 }else if(e.getSource() == savegame) {
			 if (spiel.getSpeichernerlaubt()) {
				 spiel.save();
			 }
		 }
	}else {
			textfeld.setText("Gewinner Gefunden");
			textfeld2.setText("Gewinner Gefunden");
}
}
	/**
	 * 	erstellen der textfelder
	 * 
	 * @param s Name des Texteldes
	 * @param x X position 
	 * @param y Y position
	 * @param height Höhe
	 * @param withd Breite 
	 * @param scaleX Skalierung X
	 * @param scaleY Skalierung Y
	 * @return erstelltes Textfeld
	 */
	private JTextArea textfeld (String text,int x,int y,int height,int withd,double scaleX,double scaleY) {
        JTextArea textfeld = new JTextArea();
        textfeld.setBounds((int)(x*scaleX),(int)(scaleY*y),(int)(260*scaleX),(int)(190*scaleY));
        textfeld.setBounds((int)(x*scaleX),(int)(scaleY*y),(int)(height*scaleX),(int)(withd*scaleY));
        textfeld.setLineWrap(true);
        textfeld.setWrapStyleWord(true);
        textfeld.setText(text);
        textfeld.setBorder(new LineBorder(Color.BLACK,3));
        return textfeld;
	}
	/**
	 * erstellen Ja/Nein/weiter/speichern buttons
	 * 
	 * @param s Name des Buttons
	 * @param x X position 
	 * @param y Y position
	 * @param height Höhe
	 * @param withd Breite 
	 * @param scaleX Skalierung X
	 * @param scaleY Skalierung Y
	 * @return erstellter button
	 */
	private JButton thisButton(String s,int x,int y,int height,int withd,double scaleX,double scaleY) {
		JButton temp = new JButton();
        temp.setBounds((int)(x*scaleX),(int)(y*scaleY),(int)(height*scaleX),(int)(withd*scaleY));
        temp.setText(s);
        temp.setVisible(true);
        temp.addActionListener(this);
        return temp;
	}
	/**
	 * Aktualliserung der Phasenanzeige
	 */
	public void nextPhase() {
		for (int i=0;i<3;i++) {
			phasenlist[i].setBackground(Color.white);
			phasenlist[phase%3].setBackground(Color.gray);
		}
	}
	/**
	 * Aktualliserung der Spieleranzeige
	 */
	public void nextPlayer() {
		for (int i=0;i<playerlist.length;i++) {
			playerlist[i].setBackground(Color.white);
			playerlist[currentPlayer%playerlist.length].setBackground(colors[currentPlayer%playerlist.length]);
		}
	}
	
	/**
	 * erstellen der Labels für truppenanzeige
	 * 
	 * @param button Button des Landes
	 * @param frame Frame der GUI
	 */
	private void labelSetup(JButton[] button,JFrame frame) {
		for(int i=0;i<button.length;i++) {
			labellist[i]=(buildPlayerLabel(button[i].getX()+(button[i].getWidth()/2-15),button[i].getY()+button[i].getHeight(),"1",30,10));
			frame.add(labellist[i]);
		}
	}
	
	/**
	 * Aktualliserung der Truppenanzeige
	 * 
	 * @param c Label das geändert wird
	 * @param i neuer truppenwert
	 */
	public void changeLabelInt(JLabel c,int i) {
		c.setText(String.valueOf(i+Integer.parseInt(c.getText())));
	}
	
	
	/**
	 * erstellen der Kartenanzeige
	 * 
	 * @param frame Frame der GUI
	 * @param x Skalierwert X Richtung
	 * @param y Skalierwert X Richtung
	 */
	private void kartenSetup(JFrame frame,double x,double y) {
		for(int i=0;i<5;i++) {
			kartenGraph temp = new kartenGraph((int)((30+110*i)*x),(int)((1015)*y),(int)((100)*x),(int)((130)*y));
			cart[i]=temp;
			frame.add(cart[i]);
		}
	}
	
	
	/**
	 * aktualliserung der Kartenanzeige 
	 * @param spieler Spieler des Karten angezeigt werden sollen
	 */
	public void kartenWechseln(Spieler spieler) {
		for(int i=0;i<5;i++) {
			cart[i].removeKarte();
		}
		for(int i =0;i<spieler.hand.size();i++) {
			cart[i].setKarte(spieler.hand.get(i));
		}
	}
	
	/**
	 * zuweisen der Actionlistener zu Kartenbuttons
	 */
	void setKAL(){
        KartenListener tausch = new KartenListener(spiel);
		for(int i = 0;i<5;i++) {
			cart[i].addActionListener(tausch);
		}
	}
	
	//Getter und Setter
	public int getCurrentPlayer() {
		return	currentPlayer;
	}
	public void setCurrentPlayer(int set) {
		currentPlayer=set%5;
	}
	public int getPhase() {
		return phase;
	}
	public void setPhase(int temp) {
		phase = temp;
	}
	public void setText(String text) {
		textfeld.setText(text);
	}
    public void setRunde(Phasen phase) {
    	phasen = phase;
    }
    public void setSpiel (Spiel x) {
    	spiel =x;
    }
}