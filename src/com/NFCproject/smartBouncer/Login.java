package com.NFCproject.smartBouncer;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{

    // Constante que indica si el bluetooth fue activado por el usuario
	private static final int REQUEST_ENABLE_BT = 2;
	
	// Botones
	private Button mButtonAcceso;
	private Button mButtonHistorial;
	private Button mButtonPreferencias;
	
	// Campos de edicion de texto
	private EditText mTextUser;
	private EditText mTextEmpresa;
	private EditText mTextPassword;
	
	// Texto asociado a los campo de edicion de texto
	private String mUser;
	private String mEmpresa;
	private String mPass;
	
	// Objeto de la clase preferencias
	private SharedPreferences mPreferencias;
	
	// option=1 --> Bluetooth, option=0 --> NFC
	private boolean mOption;
	
	// Objetos de la clase Bluetooth y Nfc
	private Nfc mObjectNFC = null;
	private Bluetooth mObjectBluetooth = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		// Asociamos XML
        this.mButtonAcceso=(Button) findViewById(R.id.buttonAccesoID);
        this.mButtonAcceso.setOnClickListener(this);
        
        this.mButtonHistorial=(Button) findViewById(R.id.buttonHistorialID);
        this.mButtonHistorial.setOnClickListener(this);
        
        this.mButtonPreferencias=(Button) findViewById(R.id.buttonPreferenciasID);
        this.mButtonPreferencias.setOnClickListener(this);
        
        this.mTextUser = (EditText) findViewById(R.id.editTextUser);
        this.mTextEmpresa = (EditText) findViewById(R.id.editTextEmpresa);
        this.mTextPassword = (EditText) findViewById(R.id.editTextPassword);
             
        // Creamos aqui los objetos para que no se creen cada vez que el usuario envia informacion
        this.mObjectNFC = new Nfc(this);
        this.mObjectBluetooth = new Bluetooth(this);
                
	}
	

	// Esta actividad es llamada despues de startActivityForResult
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
      	
        	case REQUEST_ENABLE_BT:
            // Se mando al usuario a habilitar el Bluetooth si lo hizo entra aqui
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                mObjectBluetooth.setupIdentification();
            }
        }
    }


	protected void onPause() {
        super.onPause();
        
        this.mObjectNFC.disableForeground();

    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		// Deshabilitamos  Bluetooth para que cuando salga de la aplicación no quede encendido
		BluetoothAdapter bluetoothAdapter;
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled()) 
            	bluetoothAdapter.disable();
		}
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	  setContentView(R.layout.login);
	}

	
    public void onClick(View v){
    	switch(v.getId()){
    	
    	case (R.id.buttonAccesoID):
    		mUser = this.mTextUser.getText().toString();
    		mEmpresa = this.mTextEmpresa.getText().toString();	
    		mPass = this.mTextPassword.getText().toString();
    		if(!mUser.equals("") && !mPass.equals("") && !mEmpresa.equals("")) {
    			menu();
			}
    		else {
    			toast(R.string.LoginRellene);
    		}
    		break;
    	case (R.id.buttonHistorialID):
    	    Intent historialActivity=new Intent(this,Historial.class);
    	    startActivity(historialActivity);
    	    overridePendingTransition(R.anim.incoming,R.anim.outgoing);
    	    break;
    		
    	case (R.id.buttonPreferenciasID):
    	    Intent preferenciasActivity=new Intent(this,Preferencias.class);
    	    startActivity(preferenciasActivity);
    	    overridePendingTransition(R.anim.incoming,R.anim.outgoing);
    	    break;
    	}
    	
	}
    

    private void menu() {
    	// Antes de mandar los datos comprobamos las preferencias del usuario: Bluetooth o NFC
		mPreferencias = PreferenceManager.getDefaultSharedPreferences(this);
		// En caso de no encontrarse la preferencia checkBoxKey devuelve false
		mOption = mPreferencias.getBoolean("checkBoxKey",false);
		if (mOption) {
			mObjectBluetooth.enabledBluetooth(mUser,mEmpresa,mPass);

		}
		else {
			mObjectNFC.enabledNFC(mUser, mEmpresa, mPass);
		}
			
    }

    private void toast(int resource) {
        Toast.makeText(this, getString(resource), Toast.LENGTH_LONG).show();
    }
}

