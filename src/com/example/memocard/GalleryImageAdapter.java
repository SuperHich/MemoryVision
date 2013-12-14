package com.example.memocard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class GalleryImageAdapter extends BaseAdapter{
	  private Context mContext;

	    private Integer[] mImageIds = {
	            R.drawable.as_c,
	            R.drawable.deux_c,
	            R.drawable.trois_c,
	            R.drawable.quatre_c,
	            R.drawable.cinq_c,
	            R.drawable.six_c,
	            R.drawable.sept_c,
	            R.drawable.huit_c,
	            R.drawable.neuf_c,
	            R.drawable.dix_c,
	            R.drawable.valet_c,
	            R.drawable.dame_c,
	            R.drawable.roi_c,
	        
	            R.drawable.as_p,
	            R.drawable.deux_p,
	            R.drawable.trois_p,
	            R.drawable.quatre_p,
	            R.drawable.cinq_p,
	            R.drawable.six_p,
	            R.drawable.huit_p,
	            R.drawable.neuf_p,
	            R.drawable.dix_p,
	            R.drawable.valet_p,
	            R.drawable.dame_p,
	            R.drawable.roi_p,
	            
	            R.drawable.as_co,
	            R.drawable.deux_co,
	            R.drawable.trois_co,
	            R.drawable.quatre_co,
	            R.drawable.cinq_co,
	            R.drawable.six_co,
	            R.drawable.sept_co,
	            R.drawable.huit_co,
	            R.drawable.neuf_co,
	            R.drawable.dix_co,
	            R.drawable.valet_co,
	            R.drawable.dame_co,
	            R.drawable.roi_co,
	            
	            R.drawable.as_t,
	            R.drawable.deux_t,
	            R.drawable.trois_t,
	            R.drawable.quatre_t,
	            R.drawable.cinq_t,
	            R.drawable.six_t,
	            R.drawable.sept_t,
	            R.drawable.huit_t,
	            R.drawable.neuf_t,
	            R.drawable.dix_t,
	            R.drawable.valet_t,
	            R.drawable.dame_t,
	            R.drawable.roi_t,
	           
	            
	           
	    };

	    public GalleryImageAdapter(Context context) 
	    {
	        mContext = context;
	    }

	    public int getCount() {
	        return mImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }


	    // Override this method according to your need
	    public View getView(int index, View view, ViewGroup viewGroup) 
	    {
	        // TODO Auto-generated method stub
	        ImageView i = new ImageView(mContext);

	        i.setImageResource(mImageIds[index]);
	        i.setLayoutParams(new Gallery.LayoutParams(35, LayoutParams.WRAP_CONTENT));
	    
	        i.setScaleType(ImageView.ScaleType.FIT_XY);

	        return i;
	    }
}
