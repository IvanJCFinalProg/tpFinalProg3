package cal.itext.bean;

public class Produit {
	
	private String description;
	private double prix;
	
	public Produit() {
		super();
	}
	
	public Produit(String description, double prix) {
		super();
		this.description = description;
		this.prix = prix;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the prix
	 */
	public double getPrix() {
		return prix;
	}
	/**
	 * @param prix the prix to set
	 */
	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	
}
