package com.NFCproject.smartBouncer;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;


public class Preferencias extends PreferenceActivity {
	
	// Objeto de la clase preferencias
	private Preference mPreferenciaAyuda;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);
		setContentView(R.layout.preferenciaslayout);
					
		// Adherimos la actividad ayuda a su preferencia
		mPreferenciaAyuda = findPreference("ayudaKey");
		Intent ayudaActivity=new Intent(this,Ayuda.class);
		mPreferenciaAyuda.setIntent(ayudaActivity);
	}
	
}
