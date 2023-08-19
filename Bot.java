package risiko;
import java.util.ArrayList;
import java.util.Arrays;

public class Bot {
	Spiel spiel;
	Spieler ich;
	Kontinent ziel, zielb;
	Land angreifer, verteidiger;
	int[] kontinentwert =  { 3, 6, 2, 4, 1, 7};
	int[] isolation;
	int verstaerkungen =0;
	// Diese Daten kommen von http://www.datagenetics.com/blog/november22011/index.html
	double[][] chancen = {
		    {41.667, 75.424, 91.637, 97.154, 99.032, 99.671, 99.888, 99.962, 99.987, 99.996, 99.998, 99.999, 100.000, 100.000, 100.000, 100.000, 100.000, 100.000, 100.000, 100.000},
		    {10.610, 36.265, 65.595, 78.545, 88.979, 93.398, 96.665, 98.031, 99.011, 99.420, 99.709, 99.830, 99.915, 99.950, 99.975, 99.985, 99.993, 99.996, 99.998, 99.999},
		    {2.702, 20.607, 47.025, 64.162, 76.937, 85.692, 90.994, 94.680, 96.699, 98.110, 98.839, 99.349, 99.603, 99.781, 99.867, 99.928, 99.956, 99.976, 99.986, 99.992},
		    {0.688, 9.130, 31.499, 47.653, 63.829, 74.487, 83.374, 88.780, 92.982, 95.393, 97.204, 98.199, 98.932, 99.321, 99.605, 99.751, 99.857, 99.911, 99.950, 99.969},
		    {0.175, 4.913, 20.594, 35.861, 50.620, 63.772, 73.640, 81.841, 87.294, 91.628, 94.304, 96.370, 97.581, 98.498, 99.015, 99.401, 99.612, 99.768, 99.851, 99.912},
		    {0.045, 2.135, 13.370, 25.250, 39.675, 52.068, 64.007, 72.956, 80.764, 86.109, 90.522, 93.354, 95.611, 96.991, 98.065, 98.697, 99.180, 99.455, 99.663, 99.779},
		    {0.011, 1.133, 8.374, 18.149, 29.742, 42.333, 53.553, 64.294, 72.608, 79.983, 85.205, 89.612, 92.541, 94.929, 96.441, 97.644, 98.377, 98.949, 99.287, 99.547},
		    {0.003, 0.490, 5.350, 12.340, 22.405, 32.948, 44.558, 54.736, 64.641, 72.397, 79.412, 84.486, 88.857, 91.838, 94.318, 95.933, 97.242, 98.063, 98.715, 99.112},
		    {0.001, 0.259, 3.277, 8.617, 16.156, 25.777, 35.693, 46.399, 55.807, 65.006, 72.303, 78.988, 83.916, 88.227, 91.231, 93.772, 95.466, 96.861, 97.758, 98.482},
		    {0.000, 0.112, 2.075, 5.719, 11.828, 19.343, 28.676, 37.987, 47.994, 56.759, 65.383, 72.284, 78.676, 83.457, 87.696, 90.704, 93.284, 95.038, 96.504, 97.465},
		    {0.000, 0.059, 1.255, 3.917, 8.292, 14.698, 22.187, 31.173, 39.987, 49.395, 57.629, 65.762, 72.319, 78.447, 83.088, 87.248, 90.246, 92.848, 94.647, 96.169},
		    {0.000, 0.025, 0.791, 2.555, 5.942, 10.721, 17.331, 24.704, 33.375, 41.749, 50.650, 58.430, 66.140, 72.395, 78.284, 82.790, 86.869, 89.845, 92.457, 94.290},
		    {0.000, 0.013, 0.475, 1.725, 4.079, 7.963, 13.039, 19.735, 26.971, 35.338, 43.328, 51.787, 59.174, 66.515, 72.501, 78.172, 82.551, 86.547, 89.496, 92.107},
		    {0.000, 0.006, 0.299, 1.111, 2.875, 5.679, 9.956, 15.221, 21.943, 29.026, 37.110, 44.756, 52.827, 59.869, 66.884, 72.629, 78.102, 82.359, 86.273, 89.189},
		    {0.000, 0.003, 0.179, 0.742, 1.941, 4.142, 7.321, 11.889, 17.277, 23.980, 30.904, 38.723, 46.062, 53.788, 60.524, 67.248, 72.775, 78.065, 82.208, 86.040},
		    {0.000, 0.001, 0.112, 0.473, 1.351, 2.901, 5.486, 8.964, 13.753, 19.211, 25.868, 32.631, 40.204, 47.264, 54.681, 61.144, 67.605, 72.934, 78.055, 82.089},
		    {0.000, 0.001, 0.067, 0.314, 0.900, 2.085, 3.958, 6.871, 10.589, 15.540, 21.034, 27.624, 34.230, 41.572, 48.379, 55.516, 61.732, 67.956, 73.103, 78.068},
		    {0.000, 0.000, 0.042, 0.198, 0.620, 1.438, 2.920, 5.081, 8.273, 12.182, 17.252, 22.755, 29.265, 35.717, 42.844, 49.419, 56.302, 62.293, 68.300, 73.280},
		    {0.000, 0.000, 0.025, 0.131, 0.408, 1.021, 2.073, 3.833, 6.248, 9.675, 13.736, 18.890, 24.381, 30.804, 37.106, 44.031, 50.393, 57.043, 62.829, 68.637},
		    {0.000, 0.000, 0.016, 0.082, 0.279, 0.696, 1.510, 2.788, 4.803, 7.441, 11.066, 15.246, 20.457, 25.922, 32.251, 38.410, 45.145, 51.310, 57.746, 63.343}
		};
	
	
	Bot(Spiel a, Spieler b){
		spiel =a;
		ich =b;
		
		

	
	}
	
	void verstärken() {
		int anzahl=0;
		int x;
		int max =0;
		Land land, lal;
		double chance;
		lal= ich.besetzt.get(1);
		if (ich.territorien>=9) {
			anzahl+=ich.territorien/3;
		}else anzahl+=3;
		for (int i=0;i<6;i++) {
			anzahl+=spiel.erde.kontinente[i].Kontinentkontrolle(ich);
		}
		if (ich.hand.size()>=4) anzahl +=kartentausch();
		verstaerkungen=anzahl;
		chance = zielkontinent();
		for (int i=0;i<ziel.enthalten.size();i++) {
			x=0;
			land = ziel.enthalten.get(i);
			if(land.besetzer==ich) {
				for (int j=0;j<land.nachbar.size();j++) {
					if(land.nachbar.get(i).besetzer!=ich) {
						x+=1;
					}
				}
			}
			if (x>max) {
				max=x;
				lal =land;
			}
		}
		lal.verstaerken(anzahl);
		
	}
	
	
	
	int kartentausch() {
		int i =0;
		int k =0;
		int a =0; 
		int anzahl =0;
		ArrayList<Karten> zwischen = new ArrayList<Karten>();
		do {
			zwischen.clear();
			for (int j=0;j<ich.hand.size();j++) {
				if (ich.hand.get(j).typ==anzahl) zwischen.add(ich.hand.get(j));
				if(anzahl==3) {
					zwischen.add(ich.hand.get(j));
					for(int l=0;l<zwischen.size();l++) {
						if (zwischen.get(l)!=ich.hand.get(i)) {
							if (zwischen.get(l).typ==ich.hand.get(i).typ) zwischen.remove(ich.hand.get(i).typ);
						}
					}
				}
			}
			anzahl+=1;
		}while (zwischen.size()<3&&anzahl<=3);
		if (anzahl==4) return 0;
		anzahl=spiel.runde.deck.eintauschen(ich, zwischen.get(0), zwischen.get(1), zwischen.get(2));
		return anzahl;
		
	}
		
		
	/*	for (int j=0;j<ich.hand.size();j++) {
			if (ich.hand.get(j).typ==0) {
				i+=1;
			}else if (ich.hand.get(j).typ==1) {
				k+=0;
			}else if (ich.hand.get(j).typ==2) {
				a+=0;
			}
			
		}
		if(i>=3) {
			for (int j=0;j<ich.hand.size();j++) {
				if (ich.hand.get(j).typ==0) zwischen.add(ich.hand.get(j));
			}
		}else if (k>=3) {
			for (int j=0;j<ich.hand.size();j++) {
				if (ich.hand.get(j).typ==1) zwischen.add(ich.hand.get(j));
			}
		}else if(a>=3) {
			for (int j=0;j<ich.hand.size();j++) {
				if (ich.hand.get(j).typ==2) zwischen.add(ich.hand.get(j));
			}
		}else if (a>=1&&k>=1&&a>=1) {
			for (int j=0;j<ich.hand.size();j++) {
				if (ich.hand.get(j).typ==0) zwischen.add(ich.hand.get(j));
			}
		}
		
		return anzahl;
	}*/
	
	
	//Berechnet den Kontinent, welcher diese Runde bevorzugt angegriffen werden soll, aus den höchsten Erfolgschancen und dem Strategischen Wert des Kontinents
	double zielkontinent () {
		int freund, feind, dis,das;
		dis=0;
		das=0;
		double max;
		Land land;
		Kontinent kontinent;
		double[] relation = new double[12];
		for(int i=0;i<spiel.erde.kontinente.length;i++) {
			freund =0;
			feind =0;
			kontinent =spiel.erde.kontinente[i];
			if (kontinent.Kontinentkontrolle(ich)==0) {
				for(int j=0;j<kontinent.modifiziert.size();j++) {
					land =kontinent.modifiziert.get(j);
					if (land.besetzer==ich) {
						freund+=land.truppen;
					}else feind+=land.truppen;
				}
				relation[i]=(freund+verstaerkungen)/feind;
				relation[i+6]=freund/feind;
				
			}else	relation[i]=0;
			
		}

		max=0;
		for (int i=0;i<relation.length-6;i++) {
			if (relation[i]*kontinentwert[i]>max) {
				max=relation[i]*kontinentwert[i];
				dis=i;
			}
		}
		ziel=spiel.erde.kontinente[dis];
		max=0;
		for (int i=6;i<relation.length;i++) {
			if (relation[i]*kontinentwert[i]>max) {
				if (i!=dis) {
					max=relation[i]*kontinentwert[i];
					das=i;
				}
				
			}
		}
		zielb=spiel.erde.kontinente[das];
		return max/kontinentwert[dis];
	}
	
	void erobern() {
		double intensität =50;
		double lohntsich=1;
		boolean weiter = true;
		while (!weiter) {
			lohntsich = zielwahl(intensität);
			if (lohntsich<intensität) weiter=false;
			intensität+=10;
			if (angreifer!=verteidiger) {
				if (isoliert(verteidiger)){
					for (int i=0;i<angreifer.truppen;i++) {
						if (chancen[verteidiger.truppen][i]>75) {
							aikampf(i);
						}
					}
					
				}
				aikampf(angreifer.truppen-1);
			}
			
		}
	}
	
	void aikampf(int atruppen) {
		boolean weiter = true;
		int min;
		if (angreifer.besetzer==ich && verteidiger.besetzer!=ich && angreifer.nachbar.contains(verteidiger)) {
			Spieler defender= verteidiger.besetzer;	
			angreifer.verlust(atruppen);
			while (weiter) {
				weiter =false;
				ich.würfeln(atruppen, false);
				defender.würfeln(verteidiger.truppen, true);
				min = 3- Math.min(2,Math.min(atruppen, verteidiger.truppen));
				for(int i = 2; i>= min ;i--) {
					System.out.print(ich.ergebnis[i]);
					System.out.print(" vs ");
					System.out.println(defender.ergebnis[i]);
					if (ich.ergebnis[i]>defender.ergebnis[i]){
						verteidiger.verlust(1);
					}else if (ich.ergebnis[i]<=defender.ergebnis[i]) {
						atruppen-=1;
					}else System.out.println("Wie bin ich denn bitte hier gelandet?");
					
				}
				if (atruppen==0) {
					return;
				}else if(verteidiger.truppen==0) {
					defender.verloren(verteidiger);
					spiel.erde.gui1.textfeld.setText("der Angriff war erfolgreich");
					verteidiger.setbesetzer(ich, atruppen);
					ich.eroberer=true;
					ich.erobert(verteidiger);
					weiter =false;
					spiel.ausgewaehlt=null;
					spiel.auchausgewaehlt=null;
					
				}else if (chancen[verteidiger.truppen][atruppen]<41) {
					weiter=false;
					angreifer.verstaerken(atruppen);
				}
			}
		}else System.out.println("Error im AIKampf, illegaler Angriff");
	}
	
	
	double zielwahl (double grenzwert) {
		Land land,lal;
		int kont;
		angreifer=verteidiger=spiel.erde.laender[0];
		double winchance, prio;
		
		for (int i=0;i<ich.besetzt.size();i++) {
			prio=0;
			land = ich.besetzt.get(i);
			if (land.truppen>1) {
				for (int j=0;j<land.nachbar.size();j++) {
					lal=land.nachbar.get(j);
					if (lal.besetzer!=ich) {
						if (land.truppen>21) {
							if (lal.truppen>20) {
								winchance =chancen[20][20];
							}else winchance=chancen[lal.truppen][20];
						}else if (lal.truppen>20) {
							winchance=0;
						}else {
							winchance=chancen[lal.truppen][land.truppen-1];
						}
						
					if (winchance>60) {
						if (ziel.enthalten.contains(lal)) {
							prio=winchance*3;
						}else if(zielb.enthalten.contains(lal)) {
							prio=winchance*2;
						}else {
							kont=0;
							for (int k=0;k<6;k++) {
								if (spiel.erde.kontinente[k].enthalten.contains(lal))kont=spiel.erde.kontinente[k].Kontinentkontrolle(lal.besetzer);
							}
							if (kont!=0) prio=winchance*2.5; 
						}
					}
					}
					
				if(prio>grenzwert) {
					grenzwert=prio;
					
					angreifer=land;
					verteidiger=lal;
				}
				}
			}
			
			
		}
		return grenzwert;
		
	}
	
	boolean isoliert (Land a) {
		for (int i=0; i<a.nachbar.size();i++) {
			if (a.nachbar.get(i).besetzer!=ich) return false;
		}
		
		return true;
	}
	
	
	void pfadfinder (Kontinent k) {
		Land startpunkt, land;
		startpunkt=k.enthalten.get(0);
		
		int max=1;
		ArrayList<Land> ziele = new ArrayList<Land>();
		ArrayList<Land> pfad = new ArrayList<Land>();
		for (int i=0;i<k.enthalten.size();i++) {
			land = k.enthalten.get(i);
			if (land.besetzer!=ich) {
				ziele.add(land);
			}
		}
		for (int i=0;i<k.modifiziert.size();i++) {
			land=k.modifiziert.get(i);
			if (land.besetzer==ich) {
				if (land.truppen>max) {
					startpunkt=land;
					max=land.truppen;
				}
			}
		}
		
		isolation = new int[ziele.size()];
		for (int i=0;i<ziele.size();i++) {
			isolation[i]=0;
			land = ziele.get(i);
			for(int j=0;j<land.nachbar.size();j++) {
				if (land.nachbar.get(j).besetzer!=ich) {
					isolation[i]+=1;
				}
			}
		}
		
		
		
		
		
		for (int i=0;i<startpunkt.nachbar.size();i++) {
			if(ziele.contains(startpunkt.nachbar.get(i))) {
				land =startpunkt.nachbar.get(i);
				pfad.add(land);
			}
		}
		
	}
	
	
	//Findet das Land welches am stärksten bedroht wird und verstärkt dieses mit der Größten nicht an Gegner angrenzenden verfügbaren Armee
	void rochade () {
		boolean weiter =true;
		double hoch=0;
		int verstärkungen=0;
		int haltmal=0;
		Land land;
		ArrayList<Land> grenze = new ArrayList<Land>();
		
		ArrayList<Land> inland = new ArrayList<Land>();
		for (int i=0; i<ich.besetzt.size();i++) {
			land=ich.besetzt.get(i);
			for(int j=0;j<land.nachbar.size();j++) {
				if(land.nachbarschaft().get(j).besetzer!=ich) {
					weiter=false;
				}
			}
			if(weiter) {
				inland.add(land);
			}else {
				grenze.add(land);
				weiter=true;
			}
		}
		double[] grenzwert = new double[grenze.size()];
		for (int i=0;i<grenze.size();i++) {
			land=grenze.get(i);
			grenzwert[i]=signifikanz(land)*bedrohung(land);
		}
		for (int i=0;i<grenzwert.length;i++) {
			if (grenzwert[i]>hoch) {
				hoch=grenzwert[i];
				haltmal=i;
			}
		}
		ArrayList<Land> kopf = new ArrayList<Land>();
		kopf.add(grenze.get(haltmal));
		for (int i=0; i< 42;i++) {
			spiel.erde.laender[i].entdeckt=false;
		}
		int j=0;
		do {
			land=kopf.get(j);
			for (int i=0;i<land.nachbar.size();i++) {
				if (ich.besetzt.contains(land.nachbar.get(i))) {
					if (!land.nachbar.get(i).entdeckt) {
						kopf.add(land.nachbar.get(i));
						land.nachbar.get(i).entdeckt = true;
					}
				}
			}
			j+=1;
		}while (j<kopf.size());
		for (int i=0; i<kopf.size();i++) {
			for(int k=0;k<inland.size();k++) {
				if(kopf.get(i)==inland.get(k)&&inland.get(k).truppen>=verstärkungen) {
					verstärkungen=inland.get(k).truppen;
					j=k;
				}
			}
		}
		if (verstärkungen>1) {
			inland.get(j).verlust(verstärkungen-1);
			grenze.get(haltmal).verstaerken(verstärkungen-1);
		}
		
		
	}
	
	double signifikanz (Land a) {
		double teil=0;
		Kontinent k;
		k = spiel.erde.kontinente[0];
		for(int i=0;i<spiel.erde.kontinente.length;i++) {
			k = spiel.erde.kontinente[i];
			if(k.enthalten.contains(a)) i=7;
		}
		for(int i=0;i<k.enthalten.size();i++) {
			if (k.enthalten.get(i).besetzer==ich) {
				teil+=1;
			}
		}
		teil=teil/k.enthalten.size();
		if (teil==1) teil=2.5;
		return teil;
		
		
	}
	
	double bedrohung (Land a) {
		double threat =0;
		//Spieler agressor=ich;
		for(int i=0; i<a.nachbar.size();i++) {
			if (a.nachbar.get(i).besetzer!=ich && a.nachbar.get(i).truppen>1) {
				threat+=a.nachbar.get(i).truppen;
			}
		}
		threat=threat/10;
		return threat;
	}
	
}
