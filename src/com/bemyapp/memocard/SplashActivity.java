package com.bemyapp.memocard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.bemyapp.memocard.R;
import com.bemyapp.memocard.util.DensityHelper;
import com.bemyapp.memocard.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SplashActivity extends Activity {
	
	final static String TAG = SplashActivity.class.getName();
	private final static long SLEEP_TIME = 3;	// Sleep for some time
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);	// Removes title bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	// Removes notification bar

		DensityHelper.getInstance(this);
		
		setContentView(R.layout.splash_layout);
		
		// Start asynctask to launch main activity
		new Thread(){
        	@Override
        	public void run(){
        		try{
        			sleep(SLEEP_TIME*1000);
        		}catch(Exception e){}
        		        		
		        startActivity(new Intent(SplashActivity.this, MenuActivity.class));
		        overridePendingTransition(R.anim.down_in, R.anim.down_out);
				finish();
        		
        	}
        }.start();
	}

}
