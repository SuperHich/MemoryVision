package com.bemyapp.memocard.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bemyapp.memocard.R;
import com.bemyapp.memocard.util.DensityHelper;

public class InformationDialog extends Dialog implements OnClickListener{

	private Button valider;
    private TextView text1, text2, title;
    private Context context;

    public InformationDialog(Activity context, int customdialogtheme, String titre, String txt1, String txt2) {
        super(context,customdialogtheme);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_ok);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	// Removes notification bar
		
        initControls(titre, txt1, txt2);
    }

    private void initControls(String titre, String txt1, String txt2){
        valider = (Button) findViewById(R.id.valide);
        valider.setOnClickListener(this);

        title = (TextView)findViewById(R.id.title);
        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);
		
		title.setTypeface(DensityHelper.getInstance(context).getCooperblk(), Typeface.BOLD);
		text1.setTypeface(DensityHelper.getInstance(context).getCooperblk(), Typeface.BOLD);
		text2.setTypeface(DensityHelper.getInstance(context).getCooperblk(), Typeface.NORMAL);
		valider.setTypeface(DensityHelper.getInstance(context).getCooperblk(), Typeface.BOLD);
		
		title.setText(titre);
		
		if(txt1.length()>0)
			text1.setText(txt1);
		else
			text1.setVisibility(View.GONE);
		
		if(txt2.length()>0)
			text2.setText(txt2);
		else
			text2.setVisibility(View.GONE);
    }
    
    public void onClick(View v) {    	    	
    	 dismiss();
    }
}