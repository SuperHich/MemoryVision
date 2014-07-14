package com.bemyapp.memocard.util;

import java.util.ArrayList;
import java.util.Random;

import com.bemyapp.memocard.R;
import com.bemyapp.memocard.entity.Card;


public class MVManager {

	static MVManager myInstance;
	public static MVManager getInstance(){
		if(myInstance == null)
			myInstance = new MVManager();

		return myInstance;
	}

	public MVManager() {

		listeCarte.add(new Card(1, R.drawable.as_c));
		listeCarte.add(new Card(2, R.drawable.deux_c));
		listeCarte.add(new Card(3, R.drawable.trois_c));
		listeCarte.add(new Card(4, R.drawable.quatre_c));
		listeCarte.add(new Card(5, R.drawable.cinq_c));
		listeCarte.add(new Card(6, R.drawable.six_c));
		listeCarte.add(new Card(7, R.drawable.sept_c));
		listeCarte.add(new Card(8, R.drawable.huit_c));
		listeCarte.add(new Card(9, R.drawable.neuf_c));
		listeCarte.add(new Card(10, R.drawable.dix_c));
		listeCarte.add(new Card(11, R.drawable.valet_c));
		listeCarte.add(new Card(12, R.drawable.dame_c));
		listeCarte.add(new Card(13, R.drawable.roi_c));

		listeCarte.add(new Card(14, R.drawable.as_p));
		listeCarte.add(new Card(15, R.drawable.deux_p));
		listeCarte.add(new Card(16, R.drawable.trois_p));
		listeCarte.add(new Card(17, R.drawable.quatre_p));
		listeCarte.add(new Card(18, R.drawable.cinq_p));
		listeCarte.add(new Card(19, R.drawable.six_p));
		listeCarte.add(new Card(20, R.drawable.huit_p));
		listeCarte.add(new Card(21, R.drawable.neuf_p));
		listeCarte.add(new Card(22, R.drawable.dix_p));
		listeCarte.add(new Card(23, R.drawable.valet_p));
		listeCarte.add(new Card(24, R.drawable.dame_p));
		listeCarte.add(new Card(25, R.drawable.roi_p));

		listeCarte.add(new Card(26, R.drawable.as_co));
		listeCarte.add(new Card(27, R.drawable.deux_co));
		listeCarte.add(new Card(28, R.drawable.trois_co));
		listeCarte.add(new Card(29, R.drawable.quatre_co));
		listeCarte.add(new Card(30, R.drawable.cinq_co));
		listeCarte.add(new Card(31, R.drawable.six_co));
		listeCarte.add(new Card(32, R.drawable.sept_co));
		listeCarte.add(new Card(33, R.drawable.huit_co));
		listeCarte.add(new Card(34, R.drawable.neuf_co));
		listeCarte.add(new Card(35, R.drawable.dix_co));
		listeCarte.add(new Card(36, R.drawable.valet_co));
		listeCarte.add(new Card(37, R.drawable.dame_co));
		listeCarte.add(new Card(38, R.drawable.roi_co));

		listeCarte.add(new Card(39, R.drawable.as_t));
		listeCarte.add(new Card(40, R.drawable.deux_t));
		listeCarte.add(new Card(41, R.drawable.trois_t));
		listeCarte.add(new Card(42, R.drawable.quatre_t));
		listeCarte.add(new Card(43, R.drawable.cinq_t));
		listeCarte.add(new Card(44, R.drawable.six_t));
		listeCarte.add(new Card(45, R.drawable.sept_t));
		listeCarte.add(new Card(46, R.drawable.huit_t));
		listeCarte.add(new Card(47, R.drawable.neuf_t));
		listeCarte.add(new Card(48, R.drawable.dix_t));
		listeCarte.add(new Card(49, R.drawable.valet_t));
		listeCarte.add(new Card(50, R.drawable.dame_t));
		listeCarte.add(new Card(51, R.drawable.roi_t));

	}

	public ArrayList<Card> listeCarte=new ArrayList<Card>();

	public ArrayList<Card> getListeCarte() {
		return listeCarte;
	}

	public void setListeCarte(ArrayList<Card> listeCarte) {
		this.listeCarte = listeCarte;
	}

	public ArrayList<Card> getList(int nbr)
	{
		ArrayList<Card> listReturn = new ArrayList<Card>();

		int size=listeCarte.size();
		for(int i=0;i<nbr;i++)
		{
			Random r = new Random();
			int  valeur = 1 + r.nextInt(size - 1);

			try {
				Card c = (Card)(listeCarte.get(valeur).clone());
				listReturn.add(c);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return listReturn;
	}

	public ArrayList<Card> getDefaultList(int nbr)
	{
		ArrayList<Card> listReturn=new ArrayList<Card>();

		for(int i=0;i<nbr;i++)
		{
			Card c=new Card(0, R.drawable.renverser);
			listReturn.add(c);
		}
		return listReturn;
	}


}
