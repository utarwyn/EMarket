package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IActionBouton;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.Tableau;
import emarket.ihm.objets.formulaire.Bouton;
import emarket.ihm.objets.tableau.TableauDonnees;

import java.awt.event.ActionEvent;

public class ProduitsPanel extends ContentPanel implements IActionBouton {

	public ProduitsPanel() { super("Gestion du stock"); }


	@Override
	public void init() {
		Tableau tableau = new Tableau(
				TableauDonnees.fromClassList(EMarket.getMetier().getProduits()),
				"photo", "fourn"
		);

		// Actions des boutons du tableau
		tableau.ajouterActionBouton("Détails", (row, action) ->
				EMarket.getIhm().switchContentPanel(new ProduitDetailsPanel((int) tableau.getValeur(row, 0))));

		tableau.ajouterActionBouton("Modifier", (row, action) ->
				EMarket.getIhm().switchContentPanel(new ProduitEditionPanel((int) tableau.getValeur(row, 0))));

		tableau.ajouterActionBouton("Supprimer", (row, action) -> {
			int idProduit = (int) tableau.getValeur(row, 0);

			if (EMarket.getIhm().confirmation("Voulez-vous vraiment supprimer ce produit ?")) {
				if (!EMarket.getMetier().supprimerProduit(idProduit))
					EMarket.getIhm().alerteErreur("Action impossible", "Le produit n'a pas pu être supprimé.\nVeuillez réessayer.");

				EMarket.getIhm().rechargerPanel();
			}
		});


		// Paramétrage du reste du tableau
		tableau.setNomsColonnes("N° produit", "Libellé", "Prix unitaire", "Catégorie", "Qte en stock");
		tableau.setLargeurColonne(0, 45);


		// Bouton en bas pour ajouter un client
		Formulaire form = new Formulaire();
		form.creerBouton("Ajouter un produit", this);
		tableau.ajouterPiedPanel(form.generer());

		this.add(tableau);
	}


	@Override
	public void onButtonAction(Bouton bouton, ActionEvent event) {
		EMarket.getIhm().switchContentPanel(new ProduitAjoutPanel());
	}
}
