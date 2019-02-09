package emarket.metier.objets;

public class Produit {

	private int              idProd;
	private String           lib;
	private float            prix;
	private CategorieProduit catP;
	private int              qs;
	private Fournisseur      fourn;
	private String           photo;


	public Produit(int idProd, String lib, float prix, int qs, CategorieProduit catP, Fournisseur fournisseur, String photo) {
		this.idProd = idProd;
		this.lib    = lib;
		this.prix   = prix;
		this.qs     = qs;
		this.catP   = catP;
		this.fourn  = fournisseur;
		this.photo  = photo;
	}


	public int              getIdProd() { return this.idProd; }
	public String           getLib   () { return this.lib;    }
	public float            getPrix  () { return this.prix;   }
	public int              getQs    () { return this.qs;     }
	public CategorieProduit getCatP  () { return this.catP;   }
	public Fournisseur      getFourn () { return this.fourn;  }
	public String           getPhoto () { return this.photo;  }


	public void setLib(String lib)             { this.lib   = lib;   }
	public void setPrix(float prix)            { this.prix  = prix;  }
	public void setQs(int qs)                  { this.qs    = qs;    }
	public void setCatP(CategorieProduit catP) { this.catP  = catP;  }
	public void setFourn(Fournisseur fourn)    { this.fourn = fourn; }
	public void setPhoto(String photo)         { this.photo = photo; }
}
