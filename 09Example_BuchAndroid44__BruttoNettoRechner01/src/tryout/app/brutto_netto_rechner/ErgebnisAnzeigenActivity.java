package tryout.app.brutto_netto_rechner;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ErgebnisAnzeigenActivity extends ActionBarActivity {
	Ergebnis ergebnis = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ergebnis_anzeigen);

		//Code aus dem Buchbeispiel, welches ohne fragments arbeitet
		final Bundle extras = getIntent().getExtras();
		
		String title = "Ergebnis: ";
		if(extras != null) {
			title = extras.getString(FormularActivity.BETRAG_TITEL);
			
			
			ergebnis = new Ergebnis();
			ergebnis.betrag = extras.getFloat(FormularActivity.BETRAG_KEY);
			ergebnis.isNetto = extras.getBoolean(FormularActivity.BETRAG_ART, true);
			ergebnis.ustProzent = extras.getInt(FormularActivity.UST_PROZENT);
			
			ergebnis.berechneErgebnis();
		}
		
		//FGL: Erweiterung. Setze den Titel aus der Intent - Information
		this.setTitle(title);
		
		//Das Ergebnis an das Fragment übergeben. Das Fragment kann nicht auf das Ergebnisobjekt sonst zugreifen.
		PlaceholderFragment pf = new PlaceholderFragment();
		pf.setErgebnis(ergebnis);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, pf).commit();
		}
		
		
		
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ergebnis_anzeigen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		//FGL: Das Original ersetzt durch switch Anweisung und erweitert um das Beenden.       
//      if (id == R.id.action_settings) {
//          return true;
//      }
      
      switch (item.getItemId()) {
      case R.id.opt_beenden:
      	finish(); //Beendet die aktuelle Aktivität, was so aussieht, als würde man zur vorherigen Aktivität zurückgehen.
      	break;
      case R.id.action_settings:
      	return true;
      }
		
		
		
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		Ergebnis ergebnis = null;
		
		public PlaceholderFragment(){			
		}
		
		public void setErgebnis(Ergebnis ergebnis){
			this.ergebnis = ergebnis;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			//über den Inflator kann man an eine RootView kommen, die dann den Zugriff auf die Gestaltungselemente erlaubt.
			View rootView = inflater.inflate(R.layout.fragment_ergebnis_anzeigen, container, false);
								
			//setTitle("Ergebnis"); //Das kann man mit rootView nicht nutzen
			
			
			//Versuch: Nachdem das Fragment über inflate vorhanden ist, sollte man die Elemente finden, oder?
			final TextView txtNettobetrag = (TextView) rootView.findViewById(R.id.tv_nettobetrag);
			if(txtNettobetrag!=null){
				txtNettobetrag.setText(String.valueOf(ergebnis.betragNetto));
			}

			
			final TextView txtUmsatzsteuer = (TextView) rootView.findViewById(R.id.tv_umsatzsteuer);
			if(txtUmsatzsteuer!=null){
				txtUmsatzsteuer.setText(String.valueOf(ergebnis.betragUst));
			}
			
			final TextView txtBruttobetrag = (TextView) rootView.findViewById(R.id.tv_bruttobetrag);
			if(txtBruttobetrag!=null){
				txtBruttobetrag.setText(String.valueOf(ergebnis.betragBrutto));
			}
			return rootView;
		}
	}
}
