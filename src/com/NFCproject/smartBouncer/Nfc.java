package com.NFCproject.smartBouncer;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.widget.Toast;

public class Nfc {

	// Actividad de login
	static private Activity mContext;

	// Adaptador NFC (igual para todos los objetos de la clase)
	static private NfcAdapter mNfcAdapter;
	
	// Usuario, empresa y contraseña
	private String mUser;
	private String mEmpresa;
	private String mPass;
	
	
	public Nfc(Activity context) {
		mContext = context;
		mNfcAdapter = NfcAdapter.getDefaultAdapter(mContext);
	}
	
	
    public void enabledNFC(String usuario, String empresa,String password) {
		this.mUser = usuario;
		this.mEmpresa = empresa;
		this.mPass = password;
    	// Comprobamos si el móvil dispone de NFC y también si esta habilitado. A diferencia de WiFi 
		// Android no permite habilitar vía software el NFC, unicamente 
    	// podemos mandar al usuario a la pantalla de settings
    	
    	if (mNfcAdapter==null) {
    		toast(R.string.NFCNoDispone);
    	} else if (!mNfcAdapter.isEnabled()) {
    		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
			dialogBuilder.setTitle(mContext.getString(R.string.NFCAdvertencia));
			dialogBuilder.setIcon(R.drawable.exclamacion);
			dialogBuilder.setMessage(mContext.getString(R.string.NFCMensajeDialogo));
			dialogBuilder.setPositiveButton(mContext.getString(R.string.Si), new DialogInterface.OnClickListener() {
				 
				public void onClick(DialogInterface dialog, int which) {
					
					// Se manda al usuario a la pantalla para habilitar NFC
					mContext.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));  
				 }
			});
			
			dialogBuilder.setNegativeButton(mContext.getString(R.string.No), new DialogInterface.OnClickListener() {
				 public void onClick(DialogInterface dialog, int which) {
				      dialog.cancel();
				   }
			});
	
			AlertDialog dialog = dialogBuilder.create();
			dialog.show();
    	} else {
    		newMessage();

    	}
    		
    }
   
    private void newMessage() {
    	// Creamos el mensaje formado por dos registros: usuario y password
    	NdefRecord[] rec = new NdefRecord[2];
    	rec[0] = newTextRecord(mUser);
    	rec[1] = newTextRecord(mPass);
    	NdefMessage msg = new NdefMessage(rec);
    	// Enviamos el mensaje
    	mNfcAdapter.enableForegroundNdefPush(mContext,msg);
		toast(R.string.DatosEnviados);
		updateDataBase();
   }
    
    private NdefRecord newTextRecord(String text) {
    	   	
    	// Hay que tener en cuenta que java guarda los caracteres en formato Unicode (16 bits) y la especificación indica que hay 
    	// que utilizar US-ASCII y UTF-8
    	byte[] langBytes = Locale.ENGLISH.getLanguage().getBytes(Charset.forName("US-ASCII"));
    	byte[] textBytes = text.getBytes(Charset.forName("UTF-8"));
    	
    	
    	byte[] payload = new byte[1 + langBytes.length + textBytes.length];
    	// Longitud de la identificacion del pais para los 6 bits menos significativos. El bit mas significativo hace 
    	// referencia a la codificacion del texto, si es UTF-8 como aqui es 0.
    	payload[0] = (byte) (langBytes.length);
    	// Identificacion del pais segun ISO/IANA
    	System.arraycopy(langBytes,0,payload,1,langBytes.length);
    	// Texto
    	System.arraycopy(textBytes,0,payload,1+langBytes.length,textBytes.length);
    	// Se forma el registro
    	NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,NdefRecord.RTD_TEXT,new byte[0],payload);
    	
    	return record;
    	
    }
    
    private void toast(int resource) {
        Toast.makeText(mContext, mContext.getString(resource), Toast.LENGTH_LONG).show();
    }

    // Almacenamos los datos de acceso en la base de datos
    private void updateDataBase() {
    	AccesoBD database = new AccesoBD(mContext,"Acceso",null,1);
		database.writeBD(this.mUser,this.mEmpresa,(String) DateFormat.getTimeInstance().format(new Date()),(String) DateFormat.getDateInstance().format(new Date()));
    }
    
    
    
	public void disableForeground() {
        if (mNfcAdapter != null || mNfcAdapter.isEnabled()) {
        	mNfcAdapter.disableForegroundNdefPush(mContext);
        }
	}
}
