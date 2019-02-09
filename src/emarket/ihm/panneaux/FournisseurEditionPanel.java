package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.formulaire.Titre;
import emarket.metier.objets.Fournisseur;

import java.util.Map;

public class FournisseurEditionPanel extends ContentPanel implements IFormulaireEnvoye {

	private Fournisseur fournisseur;

	private Formulaire form;
	private Titre      titre;


	public FournisseurEditionPanel(int idFourn) {
		super("Edition d'un fournisseur");

		this.fournisseur = EMarket.getMetier().getFournisseur(idFourn);

		// On met à jour notre IHM avec les bonnes valeurs
		this.titre.setText("Fournisseur #" + this.fournisseur.getIdFourni() + " : " + this.fournisseur.getNom() + " " + this.fournisseur.getPrenom());

		this.form.getChamp("prenom").setValeur(this.fournisseur.getPrenom());
		this.form.getChamp("nom").setValeur(this.fournisseur.getNom());
		this.form.getChamp("ville").setValeur(this.fournisseur.getVille());
		this.form.getChamp("societe").setValeur(this.fournisseur.getSociete());

		this.form.miseAjour();
	}


	@Override
	public void init() {
		this.form = new Formulaire();

		this.titre = form.creerTitre("Chargement...");

		form.setNombreColonne(2);
		form.creerChamp("prenom", "text", "Prénom");
		form.creerChamp("nom", "text", "Nom");

		form.creerChamp("ville", "text", "Ville");
		form.creerChamp("societe", "text", "Société");

		form.setActionEnvoi(this);
		form.setNombreColonne(1);
		form.creerBoutonEnvoi("Modifier");

		this.add(form.generer());
	}


	@Override
	public void onFormSubmit(Formulaire form) {
		Map<String, String> valeurs = form.getValeurs();

		EMarket.getMetier().modifierFournisseur(
				this.fournisseur.getIdFourni(),
				valeurs.get("prenom"),
				valeurs.get("nom"),
				valeurs.get("ville"),
				valeurs.get("societe")
		);

		EMarket.getIhm().switchContentPanel(new FournisseursPanel());
	}
}
