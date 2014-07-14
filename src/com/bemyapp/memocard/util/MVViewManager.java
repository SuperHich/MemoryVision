package com.bemyapp.memocard.util;

import java.nio.ByteBuffer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

public class MVViewManager {
	
	public final static String TAG = "FSDS : " + MVViewManager.class.getSimpleName();

	//unbind drawables
	//	public static void cleanViews(View view) {
	//		
	//		try{
	//			if (view.getBackground() != null) {
	//				//Log.v("cleanViews", "view has a background drawable*****************");
	//				view.getBackground().setCallback(null);
	//				view.setBackgroundResource(0);
	//			}
	//
	//			if(view instanceof ImageView){
	//				//Log.v("cleanViews", "********view instanceof ImageView");
	//				((ImageView) view).setImageDrawable(null);
	//				((ImageView) view).setImageBitmap(null);
	//				((ImageView) view).setImageResource(0);
	//			}
	//
	//			if (view instanceof ViewGroup) {
	//				//Log.v(TAG, "unbindDrawables*************");
	//				for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
	//					cleanViews(((ViewGroup) view).getChildAt(i));
	//					//((ViewGroup) view).removeViewAt(i);
	//					//Log.v(TAG, "unbindDrawables child************* "+i);
	//				}
	//
	//				try{
	//
	//					((ViewGroup) view).removeAllViews();
	//					//Log.v("cleanViews", "removeAllViews ====>>> "+((ViewGroup) view).getChildCount());
	//
	//				}catch (Exception e) {
	//					//Log.e("cleanViews", "=================>>> cleanViews error");
	//				}
	//			}
	//		}catch (Exception e) {
	//			// TODO: handle exception
	//		}
	//	}

	/**
	 * Removes the reference to the activity from every view in a view hierarchy (listeners, images etc.).
	 * This method should be called in the onDestroy() method of each activity
	 * @param viewID normally the id of the root layout
	 * 
	 * see http://code.google.com/p/android/issues/detail?id=8488
	 */
	public static void unbindReferences(Activity activity, int viewID) {
		try {
			View view = activity.findViewById(viewID);
			if (view!=null) {
				unbindViewReferences(view);
				if (view instanceof ViewGroup) cleanViews((ViewGroup) view);
			}
			//			System.gc();
		}
		catch (Throwable e) {
			// whatever exception is thrown just ignore it because a crash is always worse than this method not doing what it's supposed to do
		}
	}

	/**
	 * Removes the reference to the activity from every view in a view hierarchy (listeners, images etc.).
	 * This method should be called in the onDestroy() method of each activity
	 * @param viewGroup is the root layout
	 * 
	 */
	public static void cleanViews(ViewGroup viewGroup) {
		try{
			int nrOfChildren = viewGroup.getChildCount();
			for (int i=0; i<nrOfChildren; i++){
				View view = viewGroup.getChildAt(i);
				unbindViewReferences(view);
				if (view instanceof ViewGroup) cleanViews((ViewGroup) view);
			}
			try {
				clearBitmap(viewGroup.getDrawingCache());
				viewGroup.removeAllViews();
			}
			catch (Throwable mayHappen) {
				// AdapterViews, ListViews and potentially other ViewGroups don't support the removeAllViews operation
			}
		}catch (Exception e) {
			Log.e(TAG, "Unable to clean views ! ");
		}
	}


	public static void clearImageView(ImageView imgView){

		Drawable d = imgView.getDrawable();
		if (d!=null) d.setCallback(null);

		Bitmap bm= imgView.getDrawingCache();
		if (bm != null) {
			bm.recycle();
			bm = null;
		}

		imgView.setImageDrawable(null);
		imgView.setBackgroundDrawable(null);
	}

	private static void unbindViewReferences(View view) {
		// set all listeners to null (not every view and not every API level supports the methods)
		try {view.setOnClickListener(null);} catch (Throwable mayHappen) {};
		try {view.setOnCreateContextMenuListener(null);} catch (Throwable mayHappen) {};
		try {view.setOnFocusChangeListener(null);} catch (Throwable mayHappen) {};
		try {view.setOnKeyListener(null);} catch (Throwable mayHappen) {};
		try {view.setOnLongClickListener(null);} catch (Throwable mayHappen) {};
		try {view.setOnClickListener(null);} catch (Throwable mayHappen) {};

		// clear bkg drawable
		clearBitmap(view.getDrawingCache());

		// set background to null
		Drawable d = view.getBackground(); 
		if (d!=null) d.setCallback(null);

		if (view instanceof ImageView) {

			ImageView imageView = (ImageView) view;
			clearImageView(imageView);
		}

		//		if (view instanceof GridView) {
		//			GridView grid = (GridView) view;
		//			grid.removeAllViewsInLayout();
		//		}

		//		if (view instanceof ListView) {
		//			
		//			Log.v("cleaaaaaaaaaaaaaaannn", "this is a listview............");
		//			ListView list = (ListView) view;
		//			list.cl;
		//		}

		// destroy webview
		if (view instanceof WebView) {
			((WebView) view).destroyDrawingCache();
			((WebView) view).destroy();
		}
	}

	public static void clearBitmap(Bitmap bm) {
		if(bm != null){
			bm.recycle();
			bm = null;
			//			System.gc();
		}
	}


	//calculateInSampleSize
	public static int calculateInSampleSize(
			Bitmap bmp, int reqWidth, int reqHeight) {

		// Raw height and width of image
		final int height = bmp.getHeight();
		final int width = bmp.getWidth();

		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float)height / (float)reqHeight);
			} else {
				inSampleSize = Math.round((float)width / (float)reqWidth);
			}
		}

		return inSampleSize;
	}

	//decodeSampledBitmapFromDescriptor
	public static Bitmap decodeSampledBitmapFromDescriptor(
			Bitmap bmp, int reqWidth, int reqHeight) {

		//Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inPurgeable = true;
		o2.inInputShareable = true;
		o2.inSampleSize = calculateInSampleSize(bmp, reqWidth, reqHeight);
		//o2.inSampleSize = scale;
		o2.inJustDecodeBounds = false;

		int bytes = bmp.getHeight() * bmp.getWidth() * 4;

		ByteBuffer buffer = ByteBuffer.allocate(bytes);

		bmp.copyPixelsToBuffer(buffer);

		byte[] array = buffer.array();

		return BitmapFactory.decodeByteArray(array, 0, bytes, o2);
	}

	public static Bitmap getBitmapWithText(Bitmap bmp, String name, float textSize)
	{		
		bmp = adjustOpacity(bmp, 100);

		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(0xff404040);
		paint.setStyle(Paint.Style.FILL);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(name, canvas.getWidth()/2, (canvas.getHeight() * 3)/5, paint);

		return bmp;
	}

	public static Bitmap adjustOpacity(Bitmap bitmap, int opacity)
	{

		Bitmap mutableBitmap= null;
		try{

			mutableBitmap = bitmap.isMutable() ? bitmap : bitmap.copy(Bitmap.Config.ARGB_8888, true);

			Canvas canvas = new Canvas(mutableBitmap);
			int colour = (opacity & 0xFF) << 24;
			canvas.drawColor(colour, PorterDuff.Mode.DST_IN);

		}catch (Exception e) {
			Log.e(TAG, "Unable to adjust opacity ! ");
		}

		return mutableBitmap;
	}

//	public static Bitmap getBitmapWithText(int iconId, String name, float textSize)
//	{		
//		Bitmap bmp = BitmapFactory.decodeResource(ApplicationHelper.getInstance().getResources(), iconId);
//		bmp = adjustOpacity(bmp, 100);
//
//		Canvas canvas = new Canvas(bmp);
//		Paint paint = new Paint();
//		paint.setTextSize(textSize);
//		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//		paint.setColor(0xffffffff);
//		paint.setStyle(Paint.Style.FILL);
//		paint.setTypeface(Typeface.DEFAULT_BOLD);
//		paint.setTextAlign(Paint.Align.CENTER);
//		canvas.drawText(name, canvas.getWidth()/2, canvas.getHeight()/2, paint);
//
//		return bmp;
//	}
//
//
//	//refresh a single item in listView
//	public static void updateAudioItem(ListView list, int itemIndex){
//
//		int start = list.getFirstVisiblePosition();
//		//for(int i=start,j=list.getLastVisiblePosition(); i<=j; i++){
//		//if(id.equals(list.getItemAtPosition(i).getId())){
//		View view = list.getChildAt((itemIndex - start));
//		//list.getAdapter().getView(i, view, list);
//		if ( null != view ) {
//			// get the image view for the view
//			ImageView icon_fav = (ImageView) view.findViewById(R.id.audio_img_etoile);
//			ImageView audio_img = (ImageView) view.findViewById(R.id.audio_img);
//			
//			if(audio_img.getVisibility() == View.GONE){
//				audio_img.setVisibility(View.VISIBLE);
//				audio_img.setImageDrawable(ApplicationHelper.getInstance().getResources().getDrawable(R.drawable.icon_list4_favorite));
//			}else
//			if ( icon_fav != null && icon_fav.getVisibility() == View.GONE)
//				icon_fav.setVisibility(View.VISIBLE);
//			
//			
//		}
//	}
//
//	//refresh a single item in listView after deletion from favorites list
//	public static void updateAudioItemInListView(ListView list, int itemIndex){
//
//		int start = list.getFirstVisiblePosition();
//		//for(int i=start,j=list.getLastVisiblePosition(); i<=j; i++){
//		//if(id.equals(list.getItemAtPosition(i).getId())){
//		View view = list.getChildAt((itemIndex - start));
//		//list.getAdapter().getView(i, view, list);
//		if ( null != view ) {
//			// get the image view for the view
//			ImageView icon_fav = (ImageView) view.findViewById(R.id.audio_img_etoile);
//			ImageView audio_img = (ImageView) view.findViewById(R.id.audio_img);
//
//			if ( icon_fav != null && icon_fav.getVisibility() == View.VISIBLE) {
//				icon_fav.setVisibility(View.GONE);
//			}else{
//				audio_img.setVisibility(View.GONE);
//			}
//		}
//	}

}
