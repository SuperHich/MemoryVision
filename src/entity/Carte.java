package entity;

public class Carte 
{
	private int id;
	private int image;
	
	public Carte(int id, int image) {
		super();
		this.id = id;
		this.image = image;
	}

	public Carte() {
		super();
		// TODO Auto-generated constructor stub
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
	
	

}
