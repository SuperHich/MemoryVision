package com.bemyapp.memocard.cheesegridadapter;

import java.util.ArrayList;

/**
 * Author: alex askerov
 * Date: 9/7/13
 * Time: 10:14 PM
 */
public class DynamicGridUtils {
    /**
     * Delete item in <code>list</code> from position <code>indexFrom</code> and insert it to <code>indexTwo</code>
     *
     * @param list
     * @param indexFrom
     * @param indexTwo
     */
    public static void reorder(ArrayList list, int indexFrom, int indexTwo) {
    	try{
    		Object obj = list.remove(indexFrom);
    		list.add(indexTwo, obj);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
