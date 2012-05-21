package com.NFCproject.smartBouncer;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class Bluetooth {
	
    // Constante que indica si el bluetooth fue activado por el usuario
    private static final int REQUEST_ENABLE_BT = 2;

    // Adaptador bluetooth
    private BluetoothAdapter mBluetoothAdapter = null;
    
    // Objeto de la clase BluetoothIdentificatioService
    private BluetoothIdentificationService mIdentificationService = null;
    
 // Objeto de la clase DeviceList
    private DeviceList mDeviceList = null;
			
	// Actividad de login
	static private Activity mContext;
	
	// Usuario, empresa y contraseña
	private String mUser;
	private String mEmpresa;
	private String mPass;
	
	// Buffer de datos que se envian por Bluetooth
	byte[] mBuffer;
	
	public Bluetooth(Activity context) {
		mContext = context;
	}
	
	
	public void enabledBluetooth(String usuario, String empresa,String password) {
		this.mUser = usuario;
		this.mEmpresa = empresa;
		this.mPass = password;
		
		// Se obtiene el adaptador Bluetooth
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Se comprueba si el dispositivo dispone de Bluetooth
        if (mBluetoothAdapter == null) {
            toast(R.string.BluetoothNoDispone);
        }
        else {
		// Se comprueba si el Bluetooth esta activado
    		if (!mBluetoothAdapter.isEnabled()) {
    			
    			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
    			dialogBuilder.setTitle(mContext.getString(R.string.BluetoothAdvertencia));
    			dialogBuilder.setIcon(R.drawable.exclamacion);
    			dialogBuilder.setMessage(mContext.getString(R.string.BluetoothMensajeDialogo));
    			dialogBuilder.setPositiveButton(mContext.getString(R.string.Si), new DialogInterface.OnClickListener() {
    				 
    				public void onClick(DialogInterface dialog, int which) {
    					// Se manda al usuario a la pantalla para habilitar Bluetooth
    					Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    					mContext.startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    				}
    			});
    			
    			dialogBuilder.setNegativeButton(mContext.getString(R.string.No), new DialogInterface.OnClickListener() {
    				 public void onClick(DialogInterface dialog, int which) {
    				      dialog.cancel();
    				   }
    			});
    	
    			AlertDialog dialog = dialogBuilder.create();
    			dialog.show();
    	    			
    		} else 
   				setupIdentification();
    
        }
        	
	}
	
	public void setupIdentification() {

		mIdentificationService = new BluetoothIdentificationService(mContext);
		mDeviceList = new DeviceList();
		
		// Comprobamos si el usuario ha pareado el dispositivo al de la empresa
		mDeviceList.checkBlueEmpresa();
		if(mDeviceList.getState()) {
			 
			// Objeto que hace referencia al dispositivo remoto
	        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(mDeviceList.getMAC());

	        // Creamos mensaje y establecemos conexión con el remoto
	        createMessage();
	        boolean connect = mIdentificationService.connect(device);
	        if(connect) {
	        	boolean state = mIdentificationService.connected(mBuffer);
	        	if(state) {
	        		updateDataBase();
	        	} 
	        }
		} else 
			toast(R.string.BluetoothNoAsociado);
	}
        
	
	private void createMessage() {
		mBuffer = new byte[2 + mUser.length()+mPass.length()];
		StringBuffer sb = new StringBuffer(2 + mUser.length()+mPass.length());
		String s;
		s = sb.append("$").append(mUser).append("$").append(mPass).append("\r").toString();
		mBuffer = s.getBytes(); 
	}
	
    private void updateDataBase() {
    	AccesoBD database = new AccesoBD(mContext,"Acceso",null,1);
		database.writeBD(this.mUser,this.mEmpresa,(String) DateFormat.getTimeInstance().format(new Date()),(String) DateFormat.getDateInstance().format(new Date()));
    }


    private void toast(int resource) {
        Toast.makeText(mContext, mContext.getString(resource), Toast.LENGTH_LONG).show();
    }

}
