package utils;

import java.util.ArrayList;
import java.util.Random;

import com.example.memocard.R;

import entity.Carte;

public class CardManager {

	static CardManager myInstance;
	public static CardManager getInstance(){
		if(myInstance == null)
			myInstance = new CardManager();

		return myInstance;
	}

	public CardManager() {

		listeCarte.add(new Carte(1,	R.drawable.as_c));
		listeCarte.add(new Carte(2, R.drawable.deux_c));
		listeCarte.add(new Carte(3,R.drawable.trois_c));
		listeCarte.add(new Carte(4,R.drawable.quatre_c));
		listeCarte.add(new Carte(5,R.drawable.cinq_c));
		listeCarte.add(new Carte(6, R.drawable.six_c));
		listeCarte.add(new Carte(7, R.drawable.sept_c));
		listeCarte.add(new Carte(8,R.drawable.huit_c));
		listeCarte.add(new Carte(9, R.drawable.neuf_c));
		listeCarte.add(new Carte(10, R.drawable.dix_c));
		listeCarte.add(new Carte(11,R.drawable.valet_c));
		listeCarte.add(new Carte(12,R.drawable.dame_c));
		listeCarte.add(new Carte(13,R.drawable.roi_c));

		listeCarte.add(new Carte(14,R.drawable.as_p));
		listeCarte.add(new Carte(15,R.drawable.deux_p));
		listeCarte.add(new Carte(16,R.drawable.trois_p));
		listeCarte.add(new Carte(17,R.drawable.quatre_p));
		listeCarte.add(new Carte(18,R.drawable.cinq_p));
		listeCarte.add(new Carte(19,R.drawable.six_p));
		listeCarte.add(new Carte(20,R.drawable.huit_p));
		listeCarte.add(new Carte(21,R.drawable.neuf_p));
		listeCarte.add(new Carte(22,R.drawable.dix_p));
		listeCarte.add(new Carte(23,R.drawable.valet_p));
		listeCarte.add(new Carte(24, R.drawable.dame_p));
		listeCarte.add(new Carte(25, R.drawable.roi_p));

		listeCarte.add(new Carte(26, R.drawable.as_co));
		listeCarte.add(new Carte(27,R.drawable.deux_co));
		listeCarte.add(new Carte(28,R.drawable.trois_co));
		listeCarte.add(new Carte(29,R.drawable.quatre_co));
		listeCarte.add(new Carte(30,R.drawable.cinq_co));
		listeCarte.add(new Carte(31,R.drawable.six_co));
		listeCarte.add(new Carte(32,R.drawable.sept_co));
		listeCarte.add(new Carte(33,R.drawable.huit_co));
		listeCarte.add(new Carte(34,R.drawable.neuf_co));
		listeCarte.add(new Carte(35,R.drawable.dix_co));
		listeCarte.add(new Carte(36,R.drawable.valet_co));
		listeCarte.add(new Carte(37,R.drawable.dame_co));
		listeCarte.add(new Carte(38,R.drawable.roi_co));

		listeCarte.add(new Carte(39,R.drawable.as_t));
		listeCarte.add(new Carte(40, R.drawable.deux_t));
		listeCarte.add(new Carte(41,R.drawable.trois_t));
		listeCarte.add(new Carte(42, R.drawable.quatre_t));
		listeCarte.add(new Carte(43,R.drawable.cinq_t));
		listeCarte.add(new Carte(44,  R.drawable.six_t));
		listeCarte.add(new Carte(45,R.drawable.sept_t));
		listeCarte.add(new Carte(46,R.drawable.huit_t));
		listeCarte.add(new Carte(47,R.drawable.neuf_t));
		listeCarte.add(new Carte(48,R.drawable.dix_t));
		listeCarte.add(new Carte(49,R.drawable.valet_t));
		listeCarte.add(new Carte(50,R.drawable.dame_t));
		listeCarte.add(new Carte(51,R.drawable.roi_t));

	}

	public ArrayList<Carte> listeCarte=new ArrayList<Carte>();

	public ArrayList<Carte> getListeCarte() {
		return listeCarte;
	}

	public void setListeCarte(ArrayList<Carte> listeCarte) {
		this.listeCarte = listeCarte;
	}

	public ArrayList<Carte> getList(int nbr)
	{
		ArrayList<Carte> listReturn=new ArrayList<Carte>();

		int size=listeCarte.size();
		for(int i=0;i<nbr;i++)
		{
			Random r = new Random();
			int  valeur = 1 + r.nextInt(size - 1);

			listReturn.add(listeCarte.get(valeur));
		}
		return listReturn;
	}



}
