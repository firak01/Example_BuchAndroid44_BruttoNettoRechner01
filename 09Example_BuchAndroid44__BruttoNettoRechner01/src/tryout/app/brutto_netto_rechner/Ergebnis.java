package tryout.app.brutto_netto_rechner;

public class Ergebnis {
	public float betrag;
	public boolean isNetto;
	public int ustProzent;
	
	public float betragNetto;
	public float betragBrutto;
	public float betragUst;
	
	public void berechneErgebnis() {
				
		if (isNetto ) {
			//Berechne Bruttobetrag aus Nettobetrag:
			betragNetto = betrag;
			betragUst = betrag + ustProzent / 100;
			betragBrutto = betrag + betragUst;
			
		} else {
			// Berechne Nettobetrag aus Bruttobetrag:
			betragBrutto = betrag;
			betragUst = betrag * ustProzent / ( 100 + ustProzent );
			betragNetto = betrag - betragUst;
			
		}
	}
}
