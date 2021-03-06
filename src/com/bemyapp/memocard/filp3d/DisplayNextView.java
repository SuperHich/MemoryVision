package com.bemyapp.memocard.filp3d;

import android.view.animation.Animation;
import android.widget.ImageView;

public final class DisplayNextView implements Animation.AnimationListener {
	private boolean mCurrentView;
	ImageView image1;
	ImageView image2;
	int newImageRsc;

	public DisplayNextView(boolean currentView, ImageView image1, ImageView image2, int newImageRsc) {
		mCurrentView = currentView;
		this.image1 = image1;
		this.image2 = image2;
		this.newImageRsc = newImageRsc;
	}

	public void onAnimationStart(Animation animation) {
	}

	public void onAnimationEnd(Animation animation) {
		image1.post(new SwapViews(mCurrentView, image1, image2, newImageRsc));
	}

	public void onAnimationRepeat(Animation animation) {
	}
}