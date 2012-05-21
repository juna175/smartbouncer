package com.NFCproject.smartBouncer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class MainNFC extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        new CountDownTimer(2000, 1000) {
            public void onFinish() {
                closeScreen();
            }
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
            }
        }.start();
    }

    private void closeScreen() {
        
	    Intent loginActivity=new Intent(this,Login.class);
	    startActivity(loginActivity);
	    overridePendingTransition(R.anim.incoming,R.anim.outgoing);
        finish();
    }

}