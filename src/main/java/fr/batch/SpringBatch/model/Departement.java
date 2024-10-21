package fr.batch.SpringBatch.model;

import jakarta.persistence.Entity;

@Entity
public class Departement {

	private String code;
	private String nom;
	
	public Departement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Departement(String code, String nom) {
		super();
		this.code = code;
		this.nom = nom;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return "Departement [code=" + code + ", nom=" + nom + "]";
	}
	
	
}
