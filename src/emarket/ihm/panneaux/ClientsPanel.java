package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IActionBouton;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.Tableau;
import emarket.ihm.objets.tableau.TableauDonnees;
import emarket.ihm.objets.formulaire.Bouton;

import java.awt.event.ActionEvent;

public class ClientsPanel extends ContentPanel implements IActionBouton {

	public ClientsPanel() { super("Gestion des clients"); }


	@Override
	public void init() {
		Tableau tableau = new Tableau(TableauDonnees.fromClassList(EMarket.getMetier().getClients()));

		// Actions des boutons du tableau
		tableau.ajouterActionBouton("Modifier", (row, action) ->
				EMarket.getIhm().switchContentPanel(new ClientEditionPanel((int) tableau.getValeur(row, 0))));

		tableau.ajouterActionBouton("Supprimer", (row, action) -> {
			int idClient = (int) tableau.getValeur(row, 0);

			if (EMarket.getIhm().confirmation("Voulez-vous vraiment supprimer ce client ?")) {
				if (!EMarket.getMetier().supprimerClient(idClient))
					EMarket.getIhm().alerteErreur("Action impossible", "Le client n'a pas pu être supprimé.\nVeuillez réessayer.");

				EMarket.getIhm().rechargerPanel();
			}
		});


		// Paramétrage du reste du tableau
		tableau.setNomsColonnes("N° client", "Prénom", "Nom", "Ville", "Date de naissance");
		tableau.setLargeurColonne(0, 40);


		// Bouton en bas pour ajouter un client
		Formulaire form = new Formulaire();
		form.creerBouton("Ajouter un client", this);
		tableau.ajouterPiedPanel(form.generer());

		this.add(tableau);
	}


	@Override
	public void onButtonAction(Bouton bouton, ActionEvent event) {
		EMarket.getIhm().switchContentPanel(new ClientAjoutPanel());
	}
}
