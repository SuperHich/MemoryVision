package com.bemyapp.memocard.cheesegridadapter;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bemyapp.memocard.R;
import com.bemyapp.memocard.entity.Card;
import com.bemyapp.memocard.util.DensityHelper;


/**
 * @author H.L - admin
 *
 */
public class CheeseDynamicAdapter extends CustomBaseDynamicGridAdapter
{
	Context context;
	List<?> items;
    public CheeseDynamicAdapter(Context context, List<?> items, int columnCount, IGameNotify notifier)
    {
        super(context, items, columnCount, notifier);
        this.context = context;
        this.items = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CheeseViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, null);
            
            holder = new CheeseViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
            holder.image.setLayoutParams(new LinearLayout.LayoutParams(DensityHelper.getInstance(getContext()).getScaledWidth(80), DensityHelper.getInstance(getContext()).getScaledHeight(160)));
        
            convertView.setTag(holder);
        } else
        {
            holder = (CheeseViewHolder) convertView.getTag();
        }
        holder.position = position;
        
        new DisplayCard(position, holder, (Card)items.get(position)).execute();
        return convertView;
    }
    
    private static class CheeseViewHolder
    {
        private ImageView image;
        private int position;
    }
   
    private static class DisplayCard extends AsyncTask<Void, Void, Integer> {

		private int mPosition;
		private CheeseViewHolder mHolder;
		private Card item;
		private final WeakReference<ImageView> imageViewReference;

		public DisplayCard(int position, CheeseViewHolder holder, Card card_item) {

			mPosition = position;
			mHolder = holder;
			item = card_item;
			imageViewReference = new WeakReference<ImageView>(mHolder.image);
		}

		@Override
		protected Integer doInBackground(Void... params) {

//			Log.i(TAG, " ..mPosition " + mPosition + " ... card id " + item.getId() + " ... toShow " + item.isToShow());
			if(item.isToShow())
			{
				return item.getImage();
			}
			
			return R.drawable.renverser;
		}

		@Override
		protected void onPostExecute(Integer resId) {

			if (isCancelled()) {
				return;
			}

			if (imageViewReference != null) {
				ImageView imageView = imageViewReference.get();

				if (mHolder.position == mPosition) {

					if(imageView != null){
						imageView.setImageResource(resId);
					}
				}
			}

			this.cancel(true);
		}
	}


}