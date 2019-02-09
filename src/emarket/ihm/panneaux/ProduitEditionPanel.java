package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.ImagePreviewPanel;
import emarket.ihm.objets.formulaire.Champ;
import emarket.ihm.objets.formulaire.Titre;
import emarket.metier.objets.Produit;

import java.util.Map;

public class ProduitEditionPanel extends ContentPanel implements IFormulaireEnvoye {

	private Produit produit;

	private Formulaire form;
	private Titre      titre;

	private ImagePreviewPanel previewPanel;


	public ProduitEditionPanel(int idProd) {
		super("Edition un produit");

		this.produit = EMarket.getMetier().getProduit(idProd);

		this.titre.setText("Produit n°" + this.produit.getIdProd() + " : " + this.produit.getLib());

		this.form.getChamp("libelle").setValeur(this.produit.getLib());
		this.form.getChamp("quantite").setValeur(String.valueOf(this.produit.getQs()));
		this.form.getChamp("prix").setValeur(String.valueOf(this.produit.getPrix()));

		this.form.getChamp("categ").setValeur(this.produit.getCatP().getNom());
		this.form.getChamp("fourn").setValeur(this.produit.getFourn().getSociete());

		this.form.getChamp("photo").setValeur(this.produit.getPhoto());

		this.form.miseAjour();

		this.previewPanel.setStringImage(this.produit.getPhoto());
	}

	@Override
	public void init() {
		this.form = new Formulaire();

		this.titre = form.creerTitre("Chargement...");

		form.setNombreColonne(2);

		form.creerChamp("libelle", "text", "Libellé");
		form.creerChamp("quantite", "text", "Quantité en stock")
				.setRegexVerification("^\\d+$");
		form.creerChamp("prix", "text", "Prix")
				.setRegexVerification("^(\\d+\\.)?\\d+$");

		form.nouvelleLigne();

		form.creerChamp("categ", "select", "Catégorie")
				.setSelectDatas(EMarket.getMetier().getNomsCategoriesProduits().toArray(new String[0]));
		form.creerChamp("fourn", "select", "Fournisseur")
				.setSelectDatas(EMarket.getMetier().getSocietesFournisseurs().toArray(new String[0]));


		// Upload et affichage d'une photo
		Champ ch = form.creerChamp("photo", "file", "Photo");
		form.nouvelleLigne();

		this.previewPanel = form.creerPrevisuImage(120);

		ch.attachPreviewPanel(this.previewPanel);
		form.nouvelleLigne();


		form.setActionEnvoi(this);
		form.setNombreColonne(1);
		form.creerBoutonEnvoi("Modifier");

		this.add(form.generer());
	}


	@Override
	public void onFormSubmit(Formulaire formulaire) {
		Map<String, String> valeurs = form.getValeurs();

		EMarket.getMetier().modifierProduit(
			this.produit.getIdProd(),
			valeurs.get("libelle"),
			Float.parseFloat(valeurs.get("prix")),
			Integer.parseInt(valeurs.get("quantite")),
			EMarket.getMetier().getCategorieProduit(valeurs.get("categ")),
			EMarket.getMetier().getFournisseurParSociete(valeurs.get("fourn")),
			valeurs.get("photo")
		);

		EMarket.getIhm().switchContentPanel(new ProduitsPanel());
	}
}
