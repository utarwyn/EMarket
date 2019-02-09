package emarket.metier;

import emarket.EMarket;
import emarket.metier.objets.*;
import emarket.metier.sql.Database;
import emarket.metier.sql.SQLDataSet;
import emarket.metier.sql.tables.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe métier, fait toute la logique du programme,
 * communique avec la base de données, et renvoie les
 * informations demandées par la partie IHM.
 */
public class MarketMetier {


	private List<Client>           clients;
	private List<Produit>          produits;
	private List<Fournisseur>      fournisseurs;
	private List<CategorieProduit> categorieProduits;
	private List<Commande>         commandes;


	public MarketMetier() {
		this.chargerCategoriesProduits();
	}


	/*   Gestion des utilisateurs   */
	public boolean utilisateurExiste(String login, String password) {
		try {
			return new UserTable().findUser(login, password).next();
		} catch (SQLException e) {
			return false;
		}
	}
	public boolean creerUtilisateur(String prenom, String nom, String email, String password) {
		return new UserTable().createUser(prenom, nom, email, password);
	}


	/*   Gestion des clients   */
	public boolean      ajouterClient(String prenom, String nom, String ville, Date dateNaissance) {
		boolean b = new ClientTable().createClient(prenom, nom, ville, dateNaissance);

		if (b)
			this.clients.add(new Client(Database.getLastInsertId(), prenom, nom, ville, dateNaissance));

		return b;
	}
	public List<Client> getClients() {
		if (this.clients != null) return this.clients;
		List<Client> clients = new ArrayList<>();

		for(SQLDataSet dataSet : new ClientTable().all())
			clients.add(new Client(
				dataSet.getInteger("id_c"),
				dataSet.getString("prenom_c"),
				dataSet.getString("nom_c"),
				dataSet.getString("ville_c"),
				dataSet.getDate("date_naissance")
			));

		this.clients = clients;
		return clients;
	}
	public Client       getClient(int idClient) {
		for (Client client : this.getClients())
			if (client.getIdClient() == idClient)
				return client;

		return null;
	}
	public Client       getClient(String prenom, String nom) {
		for (Client client : this.getClients())
			if (client.getPrenom().equals(prenom) && client.getNom().equals(nom))
				return client;

		return null;
	}
	public void         modifierClient(int idClient, String prenom, String nom, String ville, Date dateNaissance) {
		Client client = this.getClient(idClient);
		if (client == null) return;

		client.setPrenom(prenom);
		client.setNom(nom);
		client.setVille(ville);
		client.setdateNaissance(dateNaissance);

		new ClientTable().modifierClient(idClient, prenom, nom, ville, dateNaissance);
	}
	public boolean      supprimerClient(int idClient) {
		if (this.getClient(idClient) == null) return false;

		for (int i = 0; i < this.clients.size(); i++)
			if (this.clients.get(i).getIdClient() == idClient)
				this.clients.remove(i);

		return new ClientTable().supprimerClient(idClient);
	}


	/*   Gestion des fournisseurs   */
	public boolean           ajouterFounisseur(String prenom, String nom, String ville, String societe) {
		boolean b = new FournisseurTable().creerFournisseur(prenom, nom, ville, societe);

		if (b)
			this.fournisseurs.add(new Fournisseur(Database.getLastInsertId(), prenom, nom, ville, societe));

		return b;
	}
	public List<Fournisseur> getFournisseurs() {
		if (this.fournisseurs != null) return this.fournisseurs;
		List<Fournisseur> fournisseurs = new ArrayList<>();

		for(SQLDataSet dataSet : new FournisseurTable().all()) {
			fournisseurs.add(new Fournisseur(
					dataSet.getInteger("id_f"),
					dataSet.getString("prenom_f"),
					dataSet.getString("nom_f"),
					dataSet.getString("ville_f"),
					dataSet.getString("societe")
			));
		}

		this.fournisseurs = fournisseurs;
		return fournisseurs;
	}
	public List<String>      getSocietesFournisseurs() {
		List<String> noms = new ArrayList<>();

		for (Fournisseur fournisseur : this.getFournisseurs())
			noms.add(fournisseur.getSociete());

		return noms;
	}
	public Fournisseur       getFournisseur(int idFourn) {
		for (Fournisseur fournisseur : this.getFournisseurs())
			if (fournisseur.getIdFourni() == idFourn)
				return fournisseur;

		return null;
	}
	public Fournisseur       getFournisseurParSociete(String societe) {
		for (Fournisseur fournisseur : this.getFournisseurs())
			if (fournisseur.getSociete().equals(societe))
				return fournisseur;

		return null;
	}
	public void              modifierFournisseur(int idFourn, String prenom, String nom, String ville, String societe) {
		Fournisseur fourn = this.getFournisseur(idFourn);
		if (fourn == null) return;

		fourn.setPrenom(prenom);
		fourn.setNom(nom);
		fourn.setVille(ville);
		fourn.setSociete(societe);

		new FournisseurTable().modifierFournisseur(idFourn, prenom, nom, ville, societe);
	}
	public boolean           supprimerFournisseur(int idFourn) {
		Fournisseur fourn = this.getFournisseur(idFourn);
		if (fourn == null) return false;

		// On supprime d'abord les produits liés à ce fournisseur
		this.supprimerProduitsFournisseur(fourn);

		for (int i = 0; i < this.fournisseurs.size(); i++)
			if (this.fournisseurs.get(i).getIdFourni() == idFourn)
				this.fournisseurs.remove(i);

		return new FournisseurTable().supprimerFournisseur(idFourn);
	}


	/*   Gestion des catégories de produits   */
	private void                   chargerCategoriesProduits() {
		this.categorieProduits = new ArrayList<>();

		for (SQLDataSet dataSet : new CategorieProduitTable().all())
			this.categorieProduits.add(new CategorieProduit(
				dataSet.getInteger("id_categ"),
				dataSet.getString("nom_categ")
			));
	}
	public  List<CategorieProduit> getCategoriesProduits() {
		return new ArrayList<>(this.categorieProduits);
	}
	public  List<String>           getNomsCategoriesProduits() {
		List<String> noms = new ArrayList<>();

		for (CategorieProduit categ : this.getCategoriesProduits())
			noms.add(categ.getNom());

		return noms;
	}
	public  CategorieProduit       getCategorieProduit(int idCateg) {
		for (CategorieProduit categProd : this.getCategoriesProduits())
			if (categProd.getIdCategProd() == idCateg)
				return categProd;

		return null;
	}
	public  CategorieProduit       getCategorieProduit(String nomCateg) {
		for (CategorieProduit categProd : this.getCategoriesProduits())
			if (categProd.getNom().equals(nomCateg))
				return categProd;

		return null;
	}


	/*   Gestion des produits   */
	public boolean       ajouterProduit(String lib, float prix, int qs, CategorieProduit catP, Fournisseur fourn, String photo) {
		boolean b = new ProduitTable().creerProduit(lib, prix, qs, catP.getIdCategProd(), fourn.getIdFourni(), photo);

		if (b)
			this.produits.add(new Produit(Database.getLastInsertId(), lib, prix, qs, catP, fourn, photo));

		return b;
	}
	public List<Produit> getProduits() {
		if (this.produits != null) return this.produits;
		List<Produit> produits = new ArrayList<>();

		for(SQLDataSet dataSet : new ProduitTable().all())
			produits.add(new Produit(
					dataSet.getInteger("id_p"),
					dataSet.getString("libelle"),
					dataSet.getFloat("prix"),
					dataSet.getInteger("quantite"),
					this.getCategorieProduit(dataSet.getInteger("id_categ")),
					this.getFournisseur(dataSet.getInteger("id_fournisseur")),
					dataSet.getString("photo")
			));

		this.produits = produits;
		return produits;
	}
	public List<Produit> getProduitsDansCategorie(String categName) {
		List<Produit> produits = new ArrayList<>();

		for (Produit produit : this.getProduits())
			if (produit.getCatP().getNom().equals(categName))
				produits.add(produit);

		return produits;
	}
	public Produit       getProduit(int idProd) {
		for (Produit produit : this.getProduits())
			if (produit.getIdProd() == idProd)
				return produit;

		return null;
	}
	public Produit       getProduit(String libelle) {
		for (Produit produit : this.getProduits())
			if (produit.getLib().equals(libelle))
				return produit;

		return null;
	}
	public void          supprimerStockPour(Produit produit, int stock) {
		produit.setQs(produit.getQs() - stock);

		new ProduitTable().modifierProduit(
				produit.getIdProd(),
				produit.getLib(), produit.getPrix(), produit.getQs(),
				produit.getCatP().getIdCategProd(),
				produit.getFourn().getIdFourni(),
				produit.getPhoto()
		);
	}
	public void          modifierProduit(int idProd, String lib, float prix, int qs, CategorieProduit catP, Fournisseur fourn, String photo) {
		Produit prod = this.getProduit(idProd);
		if (prod == null) return;

		prod.setLib(lib);
		prod.setPrix(prix);
		prod.setQs(qs);
		prod.setCatP(catP);
		prod.setFourn(fourn);
		prod.setPhoto(photo);

		new ProduitTable().modifierProduit(idProd, lib, prix, qs, catP.getIdCategProd(), fourn.getIdFourni(), photo);
	}
	public void          supprimerProduitsFournisseur(Fournisseur fourn) {
		for (int i = 0; i < this.getProduits().size(); i++) {
			Produit prod = this.getProduits().get(i);

			if (prod.getFourn().getIdFourni() == fourn.getIdFourni())
				this.supprimerProduit(prod.getIdProd());
		}
	}
	public boolean       supprimerProduit(int idProd) {
		if (this.getProduit(idProd) == null)              return false;
		if (!new ProduitTable().supprimerProduit(idProd)) return false;

		for (int i = 0; i < this.produits.size(); i++)
			if (this.produits.get(i).getIdProd() == idProd) {
				this.produits.remove(i);
				return true;
			}

		return false;
	}


	/*   Gestion des commandes   */
	public boolean        ajouterCommande(int num, Produit produit, Client client, int qteAchete, float prixTotal) {
		boolean b = new CommandeTable().creerCommande(num, produit.getIdProd(), client.getIdClient(), qteAchete, prixTotal);

		if (b) {
			this.commandes.add(new Commande(
					Database.getLastInsertId(), new Date(System.currentTimeMillis()),
					num, qteAchete, prixTotal, produit, client
			));

			// On diminue le nombre de ce produit en stock
			// this.supprimerStockPour(produit, qteAchete);
		}

		return b;
	}
	public List<Commande> getCommandes() {
		if (this.commandes != null) return this.commandes;
		List<Commande> commandes = new ArrayList<>();

		for(SQLDataSet dataSet : new CommandeTable().all())
			commandes.add(new Commande(
					dataSet.getInteger("id_comm"),
					dataSet.getDate("date"),
					dataSet.getInteger("num_comm"),
					dataSet.getInteger("qte_achete"),
					dataSet.getFloat("prix_total"),
					EMarket.getMetier().getProduit(dataSet.getInteger("id_p")),
					EMarket.getMetier().getClient(dataSet.getInteger("id_c"))
			));

		this.commandes = commandes;
		return commandes;
	}
	public Commande       getCommande(int idComm) {
		for (Commande commande : this.commandes)
			if (commande.getIdCommande() == idComm)
				return commande;

		return null;
	}
	public Commande       getCommandeByNum(int numComm) {
		for (Commande commande : this.commandes)
			if (commande.getNumCommande() == numComm)
				return commande;

		return null;
	}
	public void           modifierCommande(int idComm, Produit produit, int qteAchete, float prixTotal) {
		Commande commande = this.getCommande(idComm);
		if (commande == null) return;

		commande.setProduit(produit);
		commande.setQteAchete(qteAchete);
		commande.setPrixTotal(prixTotal);

		new CommandeTable().modifierCommande(idComm, produit.getIdProd(), qteAchete, prixTotal);
	}
	public boolean        supprimerCommande(int numComm) {
		if (this.getCommandeByNum(numComm) == null) return false;

		for (int i = 0; i < this.commandes.size(); i++)
			if (this.commandes.get(i).getNumCommande() == numComm)
				this.commandes.remove(i);

		return new CommandeTable().supprimerCommande(numComm);
	}
}