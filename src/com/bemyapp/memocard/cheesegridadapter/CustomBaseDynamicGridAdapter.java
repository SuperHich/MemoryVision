package com.bemyapp.memocard.cheesegridadapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

/**
 * Author: alex askerov
 * Date: 9/7/13
 * Time: 10:49 PM
 */
public abstract class CustomBaseDynamicGridAdapter extends AbstractDynamicGridAdapter {
	static final String TAG = CustomBaseDynamicGridAdapter.class.getSimpleName();

	private Context mContext;

    private ArrayList<Object> mItems = new ArrayList<Object>();
    private int mColumnCount;
    private IGameNotify mNotifier;

    protected CustomBaseDynamicGridAdapter(Context context, int columnCount) {
    	this.mContext = context;
        this.mColumnCount = columnCount;
    }

    public CustomBaseDynamicGridAdapter(Context context, List<?> items, int columnCount, IGameNotify notifier) {
    	mContext = context;
        mColumnCount = columnCount;
        mNotifier = notifier;
        init(items);
    }

    private void init(List<?> items) {
        addAllStableId(items);
        this.mItems.addAll(items);
    }


    public void set(List<?> items) {
        clear();
        init(items);
        notifyDataSetChanged();
    }
    
    public void setOnly(List<?> items) {
        clear();
        init(items);
//        notifyDataSetChanged();
    }

    public void clear() {
        clearStableIdMap();
        mItems.clear();
        notifyDataSetChanged();
    }

    public void add(Object item) {
        addStableId(item);
        mItems.add(item);
        notifyDataSetChanged();
    }


    public void add(List<?> items) {
        addAllStableId(items);
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }


    public void remove(Object item) {
        mItems.remove(item);
        removeStableID(item);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getColumnCount() {
        return mColumnCount;
    }

    public void setColumnCount(int columnCount) {
        this.mColumnCount = columnCount;
        notifyDataSetChanged();
    }

    @Override
    public void reorderItems(int originalPosition, int newPosition) {
    	DynamicGridUtils.reorder(mItems, originalPosition, newPosition);
    	
//    	Log.i(TAG, "... originalPosition  "+ originalPosition + " ... newPosition  "+ newPosition + "\n--------------------");
//    	for(Object item : mItems){
//    		Card c = (Card) item;
//    		Log.i(TAG, "... Card id  "+ c.getId());
//    	}
//    	Log.i(TAG, "----------------------");
    	
    	mNotifier.requestNotify(mItems);
    }

    public List getItems() {
        return mItems;
    }

    protected Context getContext() {
        return mContext;
    }
}
