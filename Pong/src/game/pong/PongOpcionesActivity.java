package game.pong;

import opciones.OpcionesSingleton;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class PongOpcionesActivity extends Activity {
	private OpcionesSingleton mPrefers;

	private PrefsFragment mPrefsFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPrefers = OpcionesSingleton.getInstance(this);
		// preferencias

		// Display the fragment as the main content.
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		mPrefsFragment = new PrefsFragment();
		fragmentTransaction.replace(android.R.id.content, mPrefsFragment);
		fragmentTransaction.commit();

	}

	public static class PrefsFragment extends PreferenceFragment implements
			OnSharedPreferenceChangeListener {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// Load the preferences from an XML resource
			addPreferencesFromResource(R.xml.preferencies);
			getPreferenceScreen().getSharedPreferences()
					.registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onResume() {
			// update summary for first time
			super.onResume();
			/*for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
				Preference preference = getPreferenceScreen().getPreference(i);
				onSharedPreferenceChanged(getPreferenceScreen()
						.getSharedPreferences(), preference.getKey());
			}*/
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			// update summary after changes
			try{
				Preference pref = findPreference(key);
				String str = sharedPreferences.getString(key, null);
				pref.setSummary(str);
			} catch(Exception e){
				return;
			}

		}
	}

}