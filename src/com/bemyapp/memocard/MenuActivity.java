package com.bemyapp.memocard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bemyapp.memocard.R;
import com.bemyapp.memocard.util.DensityHelper;
import com.bemyapp.memocard.util.MVMediaPlayer;

public class MenuActivity extends Activity{

	private Button 	btn_easy,
					btn_medium,
					btn_hard;
	
	private ImageView btn_start;
	
	private LinearLayout main_layout, level_layout;
	
	private boolean isMain = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	// Removes notification bar

		setContentView(R.layout.activity_main);

		main_layout = (LinearLayout) findViewById(R.id.main_layout);
		level_layout = (LinearLayout) findViewById(R.id.level_layout);
		
		btn_start = (ImageView) findViewById(R.id.btn_start);
		btn_easy = (Button) findViewById(R.id.btn_easy);
		btn_medium = (Button) findViewById(R.id.btn_medium);
		btn_hard = (Button) findViewById(R.id.btn_hard);

		btn_easy.setTypeface(DensityHelper.getInstance(this).getCooperblk());
		btn_medium.setTypeface(DensityHelper.getInstance(this).getCooperblk());
		btn_hard.setTypeface(DensityHelper.getInstance(this).getCooperblk());
		
		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MVMediaPlayer.getInstance(MenuActivity.this).playMenuClick();
				switchViews(level_layout, main_layout);
				isMain = false;
			}
		});

		btn_easy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MVMediaPlayer.getInstance(MenuActivity.this).playMenuClick();
				startGame(6, 20000);
			}
		});
		
		btn_medium.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MVMediaPlayer.getInstance(MenuActivity.this).playMenuClick();
				startGame(8, 20000);
			}
		});
		
		btn_hard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MVMediaPlayer.getInstance(MenuActivity.this).playMenuClick();
				startGame(10, 30000);
			}
		});

	}
	
	@Override
	public void onBackPressed() {
		if(!isMain){
			isMain = true;
			switchViews(main_layout, level_layout);
		}
		else
			super.onBackPressed();
	}

	private void startGame(int levelId, int timeMillis){
		MVMediaPlayer.getInstance(MenuActivity.this).playMenuClick();
		Intent intent2 =new Intent(MenuActivity.this,GameActivity.class);
		intent2.putExtra("level", levelId);
		intent2.putExtra("time", timeMillis);
		overridePendingTransition(R.anim.left_out, R.anim.left_in);
		startActivity(intent2);
		finish();
	}
	
	private void switchViews(final LinearLayout inLayout, final LinearLayout outLayout){
		final Animation in = new AlphaAnimation(0.0f, 1.0f);
		in.setDuration(600);

		final Animation out = new AlphaAnimation(1.0f, 0.0f);
		out.setDuration(600);

		AnimationSet as = new AnimationSet(true);
		as.addAnimation(out);
		in.setStartOffset(600);
		as.addAnimation(in);

		out.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				outLayout.setVisibility(View.GONE);
				inLayout.setVisibility(View.VISIBLE);
				inLayout.startAnimation(in);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});
		
		in.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});
		
		outLayout.startAnimation(out);	

	}
	
	
}
