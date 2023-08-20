package risiko;

public class Karten {
	private String land;
	private int typ;
	public kartenGraph button;
	private int position;


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
	public String getLand() {
		return land;
	}
	public int getTyp() {
		return typ;
	}
	public int getPosition() {
		return position;
	}

}