package tryout.app.brutto_netto_rechner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import tryout.app.brutto_netto_rechner.*;

public class FormularActivity extends ActionBarActivity {
	 /** Schluessel fuer den Betragswert, im Bundle des Intent */
	  public static final String BETRAG_KEY = "betrag";
	  
	  /** Schluessel fuer die Betragsart (Brutto, Netto), im Bundle des Intent  */
	  public static final String BETRAG_ART = "art";
	  
	  /** Schluessel fuer  den Prozentwert der Umsatzsteuer, im Bundle des Intent  */
	  public static final String UST_PROZENT = "ust";
	  
	  public static final String BETRAG_TITEL = "title";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formular);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.formular_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
//FGL: Das Original ersetzt durch switch Anweisung und erweitert um das Beenden.       
//        if (id == R.id.action_settings) {
//            return true;
//        }
        
        switch (item.getItemId()) {
        case R.id.opt_beenden:
        	finish();                    //Beendet die Aktuelle Aktivität und damit die gesamte Anwendung.
        	break;
        case R.id.action_settings:
        	return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    public void onClickBerechnen(View button) {
        // Betrag:
        final EditText txtBetrag = 
          (EditText) findViewById(R.id.edt_betrag);  
        final String tmpBetrag = 
            txtBetrag.getText().toString();
        float betrag = 0.0f;
        if (tmpBetrag.length() > 0) {
          betrag = Float.parseFloat(tmpBetrag);
        }
        
        // Art des Betrags (Brutto, Netto):
        boolean isNetto = true;
        final RadioGroup rg = 
          (RadioGroup) findViewById(R.id.rg_art);
        switch (rg.getCheckedRadioButtonId()) {
          case R.id.rb_art_netto:
            isNetto = true;
            break;
          case R.id.rb_art_brutto:
            isNetto = false;
            break;
          default:    
        }
        
        String typ = "";
        if(isNetto){
        	typ = "Nettoberechnung";
        }else{
        	typ = "Bruttoberechnung";
        }
        
        // Prozentwert Umsatzsteuer:
        final Spinner spinner =
          (Spinner) findViewById(R.id.sp_umsatzsteuer);
        final int pos = spinner.getSelectedItemPosition();
        final int[] prozentwerte = 
          getResources().getIntArray(R.array.ust_werte);
        final int prozentwert = prozentwerte[pos];
        
        Intent intent = new Intent(this, ErgebnisAnzeigenActivity.class);
        
               
        intent.putExtra(BETRAG_KEY, betrag);
        intent.putExtra(BETRAG_ART, isNetto);
        intent.putExtra(UST_PROZENT, prozentwert);
        
        intent.putExtra(BETRAG_TITEL, "Ergebnis " + typ);
        
        startActivity(intent);  
      }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_formular_activity, container, false);
            return rootView;
        }
    }

}
