package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IActionBouton;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.formulaire.Bouton;
import emarket.ihm.objets.formulaire.Groupe;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ConnexionPanel extends ContentPanel implements IFormulaireEnvoye, IActionBouton {

	public ConnexionPanel() {
		super("Connexion à l'interface");
	}

	@Override
	public void init() {
		Formulaire form = new Formulaire();

		form.creerChamp("login", "text", "Email");
		form.creerChamp("password", "password", "Mot de passe");

		Groupe grp = form.creerGroupe(1);

		grp.addObjet(form.creerBouton("Creer un compte", this));
		grp.addObjet(form.creerBoutonEnvoi("Connexion"));

		form.setActionEnvoi(this);

		this.add(form.generer());
	}


	@Override
	public void onFormSubmit(Formulaire formulaire) {
		String login = formulaire.getChamp("login").getValeur();
		String pass  = formulaire.getChamp("password").getValeur();

		boolean connexion = EMarket.getMetier().utilisateurExiste(login, pass);

		if (!connexion) {
			formulaire.getChamp("password").setValeur("");

			EMarket.getIhm().alerteErreur(
					"Problème de connexion",
					"Une erreur s'est produite lors de la connexion.\nVérifiez le login et le mot de passe."
			);
		} else {
			EMarket.getIhm().switchContentPanel(new AccueilPanel());
			EMarket.getIhm().resetHistorique();
		}

		EMarket.getIhm().miseAJour();
	}

	@Override
	public void onButtonAction(Bouton bouton, ActionEvent event) {
		EMarket.getIhm().switchContentPanel(new CreationComptePanel());
	}
}