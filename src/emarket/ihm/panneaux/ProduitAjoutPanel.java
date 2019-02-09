package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.formulaire.Champ;

import java.util.Map;

public class ProduitAjoutPanel extends ContentPanel implements IFormulaireEnvoye {

	public ProduitAjoutPanel() { super("Ajout d'un produit"); }


	@Override
	public void init() {
		Formulaire form = new Formulaire();

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

		ch.attachPreviewPanel(form.creerPrevisuImage(120));
		form.nouvelleLigne();


		form.setActionEnvoi(this);
		form.setNombreColonne(1);
		form.creerBoutonEnvoi("Ajouter");

		this.add(form.generer());
	}


	@Override
	public void onFormSubmit(Formulaire form) {
		Map<String, String> valeurs = form.getValeurs();

		EMarket.getMetier().ajouterProduit(
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
