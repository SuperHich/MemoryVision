package com.bemyapp.memocard.entity;

public class Card implements Cloneable
{
	private int id;
	private int image;
	private boolean isToShow;
	
	public Card(int id, int image) {
		this.id = id;
		this.image = image;
		this.isToShow = true;
	}

	public Card() {
		this.isToShow = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public boolean isToShow() {
		return isToShow;
	}

	public void setToShow(boolean isToShow) {
		this.isToShow = isToShow;
	}
	
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
