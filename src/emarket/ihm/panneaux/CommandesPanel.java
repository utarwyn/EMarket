package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IActionBouton;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.Tableau;
import emarket.ihm.objets.formulaire.Bouton;
import emarket.ihm.objets.tableau.TableauDonnees;

import java.awt.event.ActionEvent;

public class CommandesPanel extends ContentPanel implements IActionBouton {

	public CommandesPanel() { super("Gestion des commandes"); }


	@Override
	public void init() {
		Tableau tableau = new Tableau(
				TableauDonnees.fromClassList(EMarket.getMetier().getCommandes()),
				"idCommande", "client", "produit"
		);

		// Actions des boutons du tableau
		tableau.ajouterActionBouton("Modifier", (row, action) ->
				EMarket.getIhm().switchContentPanel(new CommandeEditionPanel((int) tableau.getValeur(row, 1))));

		tableau.ajouterActionBouton("Supprimer", (row, action) -> {
			int numComm = (int) tableau.getValeur(row, 1);

			if (EMarket.getIhm().confirmation("Voulez-vous vraiment supprimer cette commande ?")) {
				if (!EMarket.getMetier().supprimerCommande(numComm))
					EMarket.getIhm().alerteErreur("Action impossible", "La commande n'a pas pu être supprimée.\nVeuillez réessayer.");

				EMarket.getIhm().rechargerPanel();
			}
		});


		// Paramétrage du reste du tableau
		tableau.setNomsColonnes("Date", "N°commande", "N°client", "N°produit", "Libellé produit", "Prix unitaire", "Quantité", "Total");


		// Bouton en bas pour ajouter un client
		Formulaire form = new Formulaire();
		form.creerBouton("Ajouter une commande", this);
		tableau.ajouterPiedPanel(form.generer());

		this.add(tableau);
	}


	@Override
	public void onButtonAction(Bouton bouton, ActionEvent event) {
		EMarket.getIhm().switchContentPanel(new CommandeAjoutPanel());
	}
}
