package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IActionBouton;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.formulaire.Bouton;
import emarket.ihm.objets.formulaire.Groupe;

import java.awt.event.ActionEvent;
import java.util.Map;

public class CreationComptePanel extends ContentPanel implements IFormulaireEnvoye, IActionBouton {

	public CreationComptePanel() { super("Créer un compte"); }

	@Override
	public void init() {
		Formulaire form = new Formulaire();

		form.creerTitre("Créer un compte");

		form.creerChamp(  "prenom",     "text",       "Prénom : ");
		form.creerChamp(     "nom",     "text",          "Nom : ");
		form.creerChamp(   "email",     "text",       "E-mail : ");
		form.creerChamp("password", "password", "Mot de passe : ");

		Groupe grp = form.creerGroupe(1);

		grp.addObjet(form.creerBouton("Annuler", this));
		grp.addObjet(form.creerBoutonEnvoi("Créer"));


		form.setActionEnvoi(this);

		this.add(form.generer());
	}


	@Override
	public void onFormSubmit(Formulaire form) {
		Map<String, String> valeurs = form.getValeurs();

		boolean aCree = EMarket.getMetier().creerUtilisateur(
				valeurs.get("prenom"),
				valeurs.get("nom"),
				valeurs.get("email"),
				valeurs.get("password")
		);

		// Utilisateur créé, on le redirige vers l'interface de connexion
		if (aCree) EMarket.getIhm().switchContentPanel(new ConnexionPanel());
	}

	@Override
	public void onButtonAction(Bouton bouton, ActionEvent event) {
		EMarket.getIhm().retourPanel();
	}
}
