package com.bemyapp.memocard;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bemyapp.memocard.entity.Card;
import com.bemyapp.memocard.util.DensityHelper;

@SuppressWarnings("deprecation")
public class GalleryImageAdapter extends ArrayAdapter<Card>{
	private Context mContext;
	private ArrayList<Card> mCards = new ArrayList<Card>();

	public GalleryImageAdapter(Context context, ArrayList<Card> listCard) 
	{
		super(context, -1, listCard);
		mContext = context;
		mCards = listCard;

	}

	public int getCount() {
		return mCards.size();
	}

	// Override this method according to your need
	public View getView(int index, View view, ViewGroup viewGroup) 
	{		
		LinearLayout ll;
		
		if(view == null)
		{
			ll = new LinearLayout(mContext);
			ImageView i = new ImageView(mContext);
		
			ll.setLayoutParams(new Gallery.LayoutParams(DensityHelper.getInstance(mContext).getScaledWidth(60), DensityHelper.getInstance(mContext).getScaledHeight(110)));

			i.setId(1000);
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			
			ll.addView(i);
		}else
			ll = (LinearLayout) view;
		
		ImageView img = (ImageView) ll.findViewById(1000);
		img.setImageResource(mCards.get(index).getImage());

		return ll;
	}
}
