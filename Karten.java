package risiko;

public class Karten {
	public String land;
	public int typ;
	public kartenGraph button;
	int position;


	Karten(String l, int t){
		land = l;
		typ = t%3;
		position =t;
	}

	public String ansagen() {
		String typname="";
		if (typ==0) {
			typname = "Infanterie";
		}else if(typ==1) {
			typname = "Kavallerie";
		}else if (typ==2){
			typname =  "Artillerie";
		}
		return typname;
	}
	void setButton(kartenGraph temp){
		button = temp;
	}

}