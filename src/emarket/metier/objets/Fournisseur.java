package emarket.metier.objets;

public class Fournisseur {

	private int    idFourni;
	private String prenom;
	private String nom;
	private String ville;
	private String societe;


	public Fournisseur(int idFourni, String prenom, String nom, String ville, String societe) {
		this.idFourni = idFourni;
		this.prenom   = prenom;
		this.nom      = nom;
		this.ville    = ville;
		this.societe  = societe;
	}


	public int    getIdFourni() { return this.idFourni;      }
	public String getPrenom  () { return this.prenom;        }
	public String getNom     () { return this.nom;           }
	public String getVille   () { return this.ville;         }
	public String getSociete () { return this.societe;       }


	public void setPrenom(String prenom)   { this.prenom  = prenom;  }
	public void setNom(String nom)         { this.nom     = nom;     }
	public void setVille(String ville)     { this.ville   = ville;   }
	public void setSociete(String societe) { this.societe = societe; }


	@Override
	public String toString() { return this.societe; }

}
