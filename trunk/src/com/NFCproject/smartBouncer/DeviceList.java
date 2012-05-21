package com.NFCproject.smartBouncer;


import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;



 // Esta actividad se encarga de buscar dispositivos emparejados o no
 
public class DeviceList {

    // Adaptador bluetooth
    private BluetoothAdapter mBtAdapter;
    
    // True: receptor de la empresa encontrado
    // False: receptor de la empresa no encontrado
    private boolean mState;
    
    // Direccion mac
    private String mMAC;
        
    public DeviceList () {
    	// Obtenemos el adaptador bluetooth
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    }
        
    
    public void checkBlueEmpresa() {

        // Se obtiene la lista de dispositivos pareados
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        for (BluetoothDevice device : pairedDevices) {
        	if(device.getName().equals("(((UEM-BT)))")) {
        		mState=true;
        		mMAC=device.getAddress();
        	}
        	else 
        		mState=false;
        }	
    }
    
    public boolean getState() {
    	return mState;
    }
    
    public String getMAC() {
    	return mMAC;
    }
}