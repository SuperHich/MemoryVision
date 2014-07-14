package com.bemyapp.memocard.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bemyapp.memocard.R;
import com.bemyapp.memocard.util.DensityHelper;

public class CounterDialog extends Dialog{
	int i;
	TextView Compteur;
	Context mContext;
	
	public CounterDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
	
		setCancelable(false);
		
		mContext = context;
		
		setContentView(R.layout.dialog_layout);
		Compteur = (TextView) findViewById(R.id.compteur);
		Compteur.setTypeface(DensityHelper.getInstance(context).getCooperblk());
		
		new Thread(new Runnable() {

			@Override
			public void run() {

				// Start seek bar animation
				for ( i = 6; i > 0; i--) {
					try{
						Thread.sleep(1000);
					}catch(Exception e){}
					
					((Activity) mContext).runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if(i==0)
							{
								Compteur.setText(R.string.go);
							}else
								Compteur.setText(i+"");

						}
					});	

				}
				
				try{
					Thread.sleep(1000);
				}catch(Exception e){}
				
				dismiss();
			}
		}).start();

	}


}
