package risiko;
import java.util.ArrayList;


public class Land {
	public int truppen;
	public String name;
	public Spieler besetzer;
	public ArrayList<Land> nachbar;
	public boolean entdeckt;
	
	Land(String l){
		nachbar = new ArrayList<Land>();
		name = l;
		truppen =0;
		
		
		
	}
	ArrayList<Land> nachbarschaft(){
		return nachbar;
	}
	
	void verstaerken (int a) {
		truppen+=a;
	}
	
	void verlust (int a) {
		if (truppen>a) {
			truppen -=a;
		}else if (truppen == a){
			truppen -=a;
		}else System.out.println("Fehler, zu wenig Truppen vorhanden");
		
	}
	
	void addnachbar(Land a) {
		nachbar.add(a);
	}
	
	void setbesetzer(Spieler b, int t) {
		besetzer = b;
		truppen = t;
	}
	
	String getname() {
		return name;
	}
}