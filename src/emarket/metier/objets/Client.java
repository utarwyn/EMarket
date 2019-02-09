package emarket.metier.objets;

import java.sql.Date;

public class Client {

	private int    idClient;
	private String prenom;
	private String nom;
	private String ville;
	private Date   dateNaissance;


	public Client(int idClient, String prenom, String nom, String ville, Date dateNaissance) {
		this.idClient      = idClient;
		this.prenom        = prenom;
		this.nom           = nom;
		this.ville         = ville;
		this.dateNaissance = dateNaissance;
	}


	public int    getIdClient()      { return this.idClient;      }
	public String getPrenom()        { return this.prenom;        }
	public String getNom()           { return this.nom;           }
	public String getVille()         { return this.ville;         }
	public Date   getdateNaissance() { return this.dateNaissance; }


	public void setPrenom(String prenom)             { this.prenom        = prenom;        }
	public void setNom(String nom)                   { this.nom           = nom;           }
	public void setVille(String ville)               { this.ville         = ville;         }
	public void setdateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }
}
