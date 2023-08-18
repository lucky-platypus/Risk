package risiko;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class Welt {
	public Land[] laender;
	public Kontinent[] kontinente;
	public GUI gui1;
	public int step;
	public Welt() {
		
	}
	
	Welt (GUI gui){
		//Hier werden die Kontinente erzeugt und in einem Array gespeichert
		laender = new Land[42];
		kontinente = new Kontinent[6];
		String gelesen, zeile;
		BufferedReader lies;
		gui1=gui;
		
		//Hier werden die Länder erzeugt und in einem Array gespeichert
		try {
			lies = new BufferedReader(new FileReader(".\\Laender.txt"));
			for (int i =0;i<42;i++) {
				try {
					gelesen = lies.readLine();
					laender[i] = new Land(gelesen);
					laender[i].button = gui.list[i];
					laender[i].label = gui.labellist[i];
					switch(i) {
					case 0,1,2,3,4,5,6,7,8:
					}
				} catch (IOException e) {	
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File nicht da");
			//Scanner lies = new Scanner(System.in);
			e.printStackTrace();
		}
		//Hier bekommen die Länder jeweils ihre Nachbarn

		try {
			lies = new BufferedReader(new FileReader(".\\Nachbarn.txt"));
			for (int j =0;j<42;j++) {
				try {
					zeile = lies.readLine();
					Scanner scan = new Scanner(zeile);
					int asd;
					while(scan.hasNextInt()) {
						asd = scan.nextInt()-1;
						//System.out.println(laender[asd].name);
						laender[j].addnachbar(laender[asd]);
					}
					scan.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Hier werden die Kontinente erzeugt, bekommen ihre Länder und werden in einem Array gespeichert
		try {
			lies = new BufferedReader(new FileReader(".\\Kontinente.txt"));
			for(int k=0;k<6;k++) {
				try {
					kontinente[k] = new Kontinent(lies.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			for(int l=0;l<6;l++) {
				try {
					zeile = lies.readLine();
					Scanner scan = new Scanner(zeile);
					while(scan.hasNextInt()) {
						kontinente[l].addLand(laender[scan.nextInt()]);
					}
					scan.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			for(int l=0;l<6;l++) {
				try {
					zeile = lies.readLine();
					Scanner scan = new Scanner(zeile);
					while(scan.hasNextInt()) {
						kontinente[l].wert =scan.nextInt();
					}
					scan.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
}