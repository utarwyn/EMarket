package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IActionBouton;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.Tableau;
import emarket.ihm.objets.tableau.TableauDonnees;
import emarket.ihm.objets.formulaire.Bouton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FournisseursPanel extends ContentPanel implements IActionBouton {

	public FournisseursPanel() { super("Gestion des fournisseurs"); }


	@Override
	public void init() {
		JPanel  tabPan  = new JPanel(new BorderLayout());
		Tableau tableau = new Tableau(TableauDonnees.fromClassList(EMarket.getMetier().getFournisseurs()));

		// Actions des boutons du tableau
		tableau.ajouterActionBouton("Modifier", (row, action) ->
				EMarket.getIhm().switchContentPanel(new FournisseurEditionPanel((int) tableau.getValeur(row, 0))));

		tableau.ajouterActionBouton("Supprimer", (row, action) -> {
			int idClient = (int) tableau.getValeur(row, 0);

			if (EMarket.getIhm().confirmation("Voulez-vous vraiment supprimer ce fournisseur ?")) {
				if (!EMarket.getMetier().supprimerFournisseur(idClient))
					EMarket.getIhm().alerteErreur("Action impossible", "Le fournisseur n'a pas pu être supprimé.\nVeuillez réessayer.");

				EMarket.getIhm().rechargerPanel();
			}
		});

		tableau.setNomsColonnes("N° fournisseur", "Prénom", "Nom", "Ville", "Société");

		tableau.setLargeurColonne(0, 60);


		tabPan.add(tableau);
		tabPan.setOpaque(false);

		Formulaire form = new Formulaire();
		form.creerBouton("Ajouter un fournisseur", this);
		tabPan.add(form.generer(), BorderLayout.SOUTH);

		this.add(tabPan);
	}


	@Override
	public void onButtonAction(Bouton bouton, ActionEvent event) {
		EMarket.getIhm().switchContentPanel(new FournisseurAjoutPanel());
	}
}
