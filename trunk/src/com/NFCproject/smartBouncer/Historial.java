package com.NFCproject.smartBouncer;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class Historial extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historial);

		// Instanciamos un objeto de la base de datos
		AccesoBD database = new AccesoBD(this,"Acceso",null,1);
		Cursor c = database.readBD();
		
		// Si no existe ningun registro en la base de datos no cargamos la vista
		if(c.moveToFirst()) {
			AdapterHistorial rowAdapter = new AdapterHistorial(this,c);
			ListView listAcceso=(ListView)findViewById(R.id.listviewHistorial);
			listAcceso.setAdapter(rowAdapter);
		} else {
			toast(R.string.HistorialNoRegistros);
			finish();
		}
	}
	
    private void toast(int resource) {
        Toast.makeText(this, getString(resource), Toast.LENGTH_LONG).show();
    }
}
