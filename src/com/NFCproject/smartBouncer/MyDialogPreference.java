package com.NFCproject.smartBouncer;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class MyDialogPreference extends DialogPreference {
	

	private Context mContext;
	
    public MyDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.mContext = context;
    }

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// TODO Auto-generated method stub
		super.onDialogClosed(positiveResult);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		super.onClick(dialog, which);
		
		// No es necesario comprobar el dialog, solo hay uno
		if(which == DialogInterface.BUTTON_POSITIVE) {
			AccesoBD database = new AccesoBD(this.mContext,"Acceso",null,1);
			database.eraseBD();
		}
	}

}
