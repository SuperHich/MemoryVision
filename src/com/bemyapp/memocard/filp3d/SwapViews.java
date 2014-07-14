package com.bemyapp.memocard.filp3d;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public final class SwapViews implements Runnable {
	private boolean mIsFirstView;
	ImageView image1;
	ImageView image2;
	int newImageRsc;
	public static	Boolean test_anim=false;
	public SwapViews(boolean isFirstView, ImageView image1, ImageView image2, int newImageRsc) {
		mIsFirstView = isFirstView;
		this.image1 = image1;
		this.image2 = image1;
		this.newImageRsc = newImageRsc;

	}

	public synchronized void run() {
		final float centerX = image1.getWidth() / 2.0f;
		final float centerY = image1.getHeight() / 2.0f;
		Flip3dAnimation rotation;

		if (mIsFirstView) {
			image1.setVisibility(View.GONE);
			image2.setImageResource(newImageRsc);
			image2.setVisibility(View.VISIBLE);
			image2.requestFocus();

		} 

		rotation = new Flip3dAnimation(0, 0, centerX, centerY);
		// cette bloque d'animation responsable pour faire voir la 2 image
		rotation.setDuration(1500);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new DecelerateInterpolator());

		image2.startAnimation(rotation);

	}
	
}