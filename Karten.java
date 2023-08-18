package risiko;

public class Karten {
	public String land;
	public int typ;
	int position;


	Karten(String l, int t){
		land = l;
		position =t;
		typ = t%3;
	}

	void ansagen () {
		System.out.print(land);
		System.out.print("---");
		if (typ==0) {
			System.out.println("Infanterie");
		}else if(typ==1) {
			System.out.println("Kavallerie");
		}else if (typ==2){
			System.out.println("Artillerie");
		}else {
			System.out.println("Modulo verkackt");
		}
	}

}