package com.NFCproject.smartBouncer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

public class BluetoothIdentificationService {
	
	    // UUID de la aplicación, debe ser unico e igual en el servidor
	    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	    
	    // Socket Bluetooth
	    private BluetoothSocket mSocket;
	    
	    // Actividad de login
	    static private Activity mContext;
	    
	    // Constructor Bluetooth
	    public BluetoothIdentificationService(Activity context) {
	    	mContext = context;
	    }


	    public boolean connect(BluetoothDevice device) {
	    	BluetoothSocket tmp = null;

            // Obtenemos el socket Bluetooth pasando el UUID, este debe ser el mismo en el lado del servidor
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                toast(R.string.BluetoothUUID);
            }
            mSocket = tmp;

            try {
                // Llamada bloqueante
                mSocket.connect();
                return true;
            } catch (IOException e) {
            	toast(R.string.BluetoothNoConexion);	   
                // En caso de que que no se pueda establecer la conexion 
                try {
                    mSocket.close();
                } catch (IOException e2) {
                	toast(R.string.BluetoothNoCierre);
                }
                return false;
            }
        }
	    
	    public boolean connected (byte[] buffer) {
            OutputStream outStream;
            OutputStream tmpOut = null;
     
            // Obtenemos el handle para enviar datos
            try {
                tmpOut = mSocket.getOutputStream();
            } catch (IOException e) {
            	toast(R.string.BluetoothNoObtenerStream);
            }
 
            outStream = tmpOut;
        
            // Escribimos en el stream de salida
            try {
	                outStream.write(buffer);
	                
	                try {
	                    mSocket.close();
	                } catch (IOException e2) {
	                	toast(R.string.BluetoothNoCierre);
	                }
	                toast(R.string.DatosEnviados);
	                return true;
	              
	        } catch (IOException e) { 
            	toast(R.string.BluetoothNoEscribirStream);
	            	return false;
	        }
	     }
	    
	    private void toast(int resource) {
	        Toast.makeText(mContext, mContext.getString(resource), Toast.LENGTH_LONG).show();
	    }
	    
	    

}