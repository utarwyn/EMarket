package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.objets.Formulaire;
import emarket.metier.util.DateUtil;

import java.text.ParseException;
import java.util.Map;

public class FournisseurAjoutPanel extends ContentPanel implements IFormulaireEnvoye {

	public FournisseurAjoutPanel() { super("Ajout d'un fournisseur"); }

	@Override
	public void init() {
		Formulaire form = new Formulaire();

		form.setNombreColonne(2);
		form.creerChamp("prenom", "text", "Prénom");
		form.creerChamp("nom", "text", "Nom");

		form.creerChamp("ville", "text", "Ville");
		form.creerChamp("societe", "text", "Société");

		form.setActionEnvoi(this);
		form.setNombreColonne(1);
		form.creerBoutonEnvoi("Ajouter");

		this.add(form.generer());
	}


	@Override
	public void onFormSubmit(Formulaire form) {
		Map<String, String> valeurs = form.getValeurs();

		EMarket.getMetier().ajouterFounisseur(
				valeurs.get("prenom"),
				valeurs.get("nom"),
				valeurs.get("ville"),
				valeurs.get("societe")
		);

		EMarket.getIhm().switchContentPanel(new FournisseursPanel());
	}
}
