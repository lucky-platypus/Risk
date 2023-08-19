package risiko;
import java.util.ArrayList;
import java.util.Arrays;

public class Bot {
	Spiel spiel;
	Spieler ich;
	Kontinent ziel;
	int[] kontinentwert =  { 3, 6, 2, 4, 1, 7};
	int verstaerkungen =0;
	
	Bot(Spiel a, Spieler b){
		spiel =a;
		ich =b;
	}
	
	
	void verstärken() {
		int anzahl=0;
		
		if (ich.territorien>=9) {
			anzahl+=ich.territorien/3;
		}else anzahl+=3;
		for (int i=0;i<6;i++) {
			anzahl+=spiel.erde.kontinente[i].Kontinentkontrolle(ich);
		}
		if (ich.hand.size()>=3) anzahl +=kartentausch();
		verstaerkungen=anzahl;
		
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
	void zielkontinent () {
		int freund, feind, dis;
		dis=0;
		double max;
		Land land;
		Kontinent kontinent;
		double[] relation = new double[6];
		for(int i=0;i<spiel.erde.kontinente.length;i++) {
			freund =0;
			feind =0;
			kontinent =spiel.erde.kontinente[i];
			if (kontinent.Kontinentkontrolle(ich)!=0) {
				for(int j=0;j<kontinent.enthalten.size();j++) {
					land =kontinent.enthalten.get(j);
					if (land.besetzer==ich) {
						freund+=land.truppen;
					}else feind+=land.truppen;
				}
				relation[i]=(freund+verstaerkungen)/feind;
				
			}else	relation[i]=0;
			
		}

		max=0;
		for (int i=0;i<relation.length;i++) {
			if (relation[i]*kontinentwert[i]>max) {
				max=relation[i];
				dis=i;
			}
		}
		ziel=spiel.erde.kontinente[dis];
		
	}
	
	
	
	
	
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
