package com.examen;

public class Bean {
	private int idBean;
	private static int compteur= 1;
	private String nomPrenom;
	private String posteEmploi;
	private String numPoste;
	private String email;
	private String departement;
	private double salaire;
	private String sexe;
	private String etatCivil;
	private String type;
	private String statut;
	
	public Bean() {
		super();
		this.idBean=compteur;
		compteur++;
	}
	public int getIdBean() {
		return idBean;
	}
	public void setIdBean(int idBean) {
		this.idBean = idBean;
	}
	public String getNomPrenom() {
		return nomPrenom;
	}
	public void setNomPrenom(String nomPrenom) {
		this.nomPrenom = nomPrenom;
	}
	public String getPosteEmploi() {
		return posteEmploi;
	}
	public void setPosteEmploi(String posteEmploi) {
		this.posteEmploi = posteEmploi;
	}
	public String getNumPoste() {
		return numPoste;
	}
	public void setNumPoste(String numPoste) {
		this.numPoste = numPoste;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public double getSalaire() {
		return salaire;
	}
	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getEtatCivil() {
		return etatCivil;
	}
	public void setEtatCivil(String etatCivil) {
		this.etatCivil = etatCivil;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	
}
