package emarket.metier.objets;

public class CategorieProduit {

	private int    idCategProd;
	private String nom;


	public CategorieProduit(int idCategProd, String nom) {
		this.idCategProd = idCategProd;
		this.nom         = nom;
	}


	public int    getIdCategProd() { return this.idCategProd; }
	public String getNom        () { return this.nom;         }


	public void setNom(String nom) { this.nom = nom; }


	@Override
	public String toString() { return this.nom; }

}
