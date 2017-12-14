package cal.itext.bean;

import java.util.List;

public class Commande {

	private Client client; // suite à la relation OneToOne entre Client et Commande
	private List<Produit> listeProduits; // suite à la relation OneToMany entre Commande et Produit
	private double totalCommande = 0;
	
	public Commande() {
		super();
	}

	public Commande(Client client, List<Produit> listeProduits) {
		super();
		this.client = client;
		this.listeProduits = listeProduits;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the listeProduits
	 */
	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	/**
	 * @param listeProduits the listeProduits to set
	 */
	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public double getTotalCommande() {
		for (Produit produit : this.getListeProduits()) {
			totalCommande += produit.getPrix();
		}
		return totalCommande;
	}
}
