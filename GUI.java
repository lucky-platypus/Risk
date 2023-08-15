package gui;


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
		
        JButton nextturn=new JButton();
        JTextArea textfeld = new JTextArea();
        
        private int currentPlayer=0;
        private int phase = 0;
        
        
	public GUI(double scaleX,double scaleY,int playerCount) throws IOException {
    	
		playerlist = new JLabel[playerCount];
		
        JFrame frame = buildFrame(scaleX,scaleY);

        final BufferedImage image = ImageIO.read(new File(".\\Welt.jpg"));
        
        buttonSetup(frame,scaleX,scaleY);
	

        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 10, 10,(int)(1620*scaleX),(int)(1000*scaleY), null);
            }
        };


        
        for(int i=0; i<playerCount;i++) {
        	playerlist[i] = (buildPlayerLabel((int)(1720*scaleX),(int)((i*50+5)*scaleY),"Player"+(i+1), 100, 50));
        	frame.add(playerlist[i]);
        }
        for(int i=0; i<3;i++) {
        	phasenlist[i] = (buildPlayerLabel((int)(1720*scaleX),(int)((((i*50)+5)+305)*scaleY),"Phase"+(i+1), 100, 50));
        	frame.add(phasenlist[i]);
        }
        
        labelSetup(list,frame);
        
        nextturn.setBounds((int)(1640*scaleX),(int)(1005*scaleY),(int)(260*scaleX),(int)(130*scaleY));
        nextturn.setText("weiter");
        nextturn.setVisible(true);
        nextturn.addActionListener(this);
        
        textfeld .setBounds((int)(1640*scaleX),(int)(scaleY*500),(int)(260*scaleX),(int)(500*scaleY));
        textfeld.setLineWrap(true);
        textfeld.setWrapStyleWord(true);
        textfeld.setText("default");
        textfeld.setBorder(new LineBorder(Color.BLACK,3));
       
        kartenGraph karte = new kartenGraph(10,1020,40,40,"fick","dich");
        
        frame.add(karte);
        frame.add(nextturn);
        frame.add(textfeld);
        frame.add(pane);
        frame.setVisible(true);
 
    }


    private static JFrame buildFrame(double aX,double aY) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize((int)(1920.0*aX),(int) (1200.0*aY));
        frame.setVisible(true);
        return frame;
    }
    private static JButton buildButton(int x, int y,String name, int height,int withd, int fontSize) {
        JButton button = new JButton();
		Font font = new Font("Monospaced", Font.BOLD,fontSize);
		button.setFont(font);
        button.setBounds(x, y, height, withd);
        button.setVisible(true);
        button.setContentAreaFilled(true);
        button.setBackground(Color.red);
        button.setText(name);
        return button;
    	
    }
    private static JLabel buildPlayerLabel(int x, int y, String name,int withd,int height) {
        JLabel label = new JLabel();
        label.setBounds(x, y, withd, height);
        label.setVisible(true);
        label.setText(name);
        label.setOpaque(true);
        label.setBackground(Color.red);
        return label;
    }
    
    private void buttonSetup(JFrame frame,double xScaling,double yScaling) {
        BufferedReader lies;
		try {
			lies = new BufferedReader(new FileReader("E:\\Laender_html.txt"));
			for (int i =0;i<42;i++) {
				try {
		        	list[i]= buildButton((int)(x[i]*xScaling),(int)(y[i]*yScaling),lies.readLine(),(int)(withd[i]*xScaling*1.05),(int)(height[i]*yScaling*1.1),(int)(11*(yScaling)));
		        	list[i].addActionListener(this);
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


	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource() == nextturn) {
			for (int i=0;i<playerlist.length;i++) {
				playerlist[i].setBackground(Color.white);;
			}
			for (int i=0;i<3;i++) {
				phasenlist[i].setBackground(Color.white);;
			}
			playerlist[currentPlayer].setBackground(Color.red);
			phasenlist[phase].setBackground(Color.green);
			phase =(phase+3+1)%3;
			if (phase == 0) {
				
				currentPlayer =(currentPlayer+playerlist.length+1)%playerlist.length;
			}
			
			this.setText("SURPRISE MOTHERFUCKER");
		 }
		else
			for(int i = 0; i<42;i++) {
				if(e.getSource()==list[i]) {
					list[i].setBackground(Color.GREEN);
					changeLabelInt(labellist[i],1);
			}
		}
	
		
	}

	private void labelSetup(JButton[] button,JFrame frame) {
		for(int i=0;i<button.length;i++) {
			labellist[i]=(buildPlayerLabel(button[i].getX()+(button[i].getWidth()/2-15),button[i].getY()+button[i].getHeight(),"0",30,10));
			frame.add(labellist[i]);
		}
	}
	private void changeLabelInt(JLabel c,int i) {
		c.setText(String.valueOf(i+Integer.parseInt(c.getText())));
	}
	public void setText(String text) {
		textfeld.setText(text);
	}

}

