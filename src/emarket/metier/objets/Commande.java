package emarket.metier.objets;

import java.sql.Date;

public class Commande {

	private int     idCommande;
	private Date    date;
	private int     numCommande;

	/* Permet de designer le tableau */
	private int     idClient;
	private int     idProduit;
	private String  libProduit;
	private float   prixUnitaire;

	private Client  client;
	private Produit produit;
	private int     qteAchete;
	private float   prixTotal;


	public Commande(int idCommande, Date date, int numCommande, int qteAchete,
	                float prixTotal,  Produit produit, Client client ) {
		this.idCommande  = idCommande;
		this.date        = date;
		this.numCommande = numCommande;
		this.qteAchete   = qteAchete;
		this.prixTotal   = prixTotal;
		this.produit     = produit;
		this.client      = client;

		// Permet de désigner le tableau
		this.idClient     = client.getIdClient();
		this.idProduit    = produit.getIdProd();
		this.libProduit   = produit.getLib();
		this.prixUnitaire = produit.getPrix();
	}
	
	public int     getIdCommande()  { return this.idCommande;  }
	public int     getNumCommande() { return this.numCommande; }
	public int     getQteAchete()   { return this.qteAchete;   }
	public float   getPrixTotal()   { return this.prixTotal;   }
	public Date    getDate()        { return this.date;        }
	public Produit getProduit()     { return this.produit;     }
	public Client  getClient()      { return this.client;      }
	
	public void setNumCommande(int numCommande) { this.numCommande = numCommande; }
	public void setQteAchete(int qteAchete)     { this.qteAchete   = qteAchete;   }
	public void setPrixTotal(float prixTotal)   { this.prixTotal   = prixTotal;   }
	public void setProduit(Produit produit)     { this.produit     = produit;     }
	public void setClient(Client client)        { this.client      = client;      }
}
