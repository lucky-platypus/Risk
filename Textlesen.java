package risiko;

public class Textlesen {
	public Welt erde;
	
	Textlesen(Welt welt){
		
	}
	
	void getVerschieben() {
		int anzahl =0;
		try {
			anzahl= Integer.parseInt(erde.gui1.textfeld2.getText());
		} catch (NumberFormatException e) {
			erde.gui1.textfeld.setText("Du musst ein Int eingeben");
		}
		
		
}
}