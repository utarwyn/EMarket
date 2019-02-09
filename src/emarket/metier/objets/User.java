package emarket.metier.objets;

public class User
{
	private int    idUser;
	private String prenom;
	private String nom;
	private String email;
	private String password;

	public User(int idUser, String prenom, String nom, String email, String password) {
		this.idUser   = idUser;
		this.prenom   = prenom;
		this.nom      = nom;
		this.email    = email;
		this.password = password;
	}
	
	public int    getIdUser()   { return this.idUser;    }
	public String getPrenom()   { return this.prenom;    }
	public String getNom()      { return this.nom;       }
	public String getEmail()    { return this.email;     }
	public String getPassword() {  return this.password; }
	
	public void setPrenom(String prenom)     { this.prenom  = prenom;    }
	public void setNom(String nom)           { this.nom     = nom;       }
	public void setEmail(String email)       { this.email   = email;     }
	public void setPassword(String password) { this.password = password; }
}
