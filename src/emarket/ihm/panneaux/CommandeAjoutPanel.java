package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.interfaces.ISelectionChanged;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.formulaire.Champ;
import emarket.metier.objets.Client;
import emarket.metier.objets.Produit;
import emarket.metier.util.DateUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandeAjoutPanel extends ContentPanel implements IFormulaireEnvoye, ISelectionChanged {

	private Formulaire form;
	private Champ      selectProduits;
	private Champ      selectCateg;
	private Champ      quantite;

	private Produit    produit;


	public CommandeAjoutPanel() { super("Ajout d'une commande"); }

	@Override
	public void init() {
		this.form = new Formulaire();

		form.setNombreColonne(2);
		form.creerTitre("Informations produit", 14, SwingConstants.LEFT);
		form.creerTitre( "Informations client", 14, SwingConstants.LEFT);

		this.selectCateg = form.creerChamp("categ", "select", "Catégorie")
				.setSelectDatas(EMarket.getMetier().getNomsCategoriesProduits().toArray(new String[0]))
				.setSelectionChanged(this);

		form.creerChamp("prenom", "text", "Prénom");

		this.selectProduits = form.creerChamp("libelle", "select", "Libellé")
				.setSelectionChanged(this);
		form.creerChamp("nom", "text", "Nom");

		this.quantite = form.creerChamp("qte", "text", "Qte à acheter")
				.setRegexVerification("^\\d+$");
		form.nouvelleLigne();

		form.setActionEnvoi(this);
		form.setNombreColonne(1);
		form.creerBoutonEnvoi("Ajouter");

		this.add(form.generer());

		this.quantite.getField().setEnabled(false);
	}


	@Override
	public void onFormSubmit(Formulaire formulaire) {
		Map<String, String> valeurs = form.getValeurs();

		String cPrenom = valeurs.get("prenom");
		String cNom    = valeurs.get("nom");

		Client client = EMarket.getMetier().getClient(cPrenom, cNom);
		if (client == null) {
			if (EMarket.getIhm().confirmation("Le client '" + cPrenom + " " + cNom + "' n'existe pas !\nVoulez-vous le créer ?"))
				EMarket.getIhm().switchContentPanel(new ClientAjoutPanel());

			return;
		}

		int qte = Integer.parseInt(valeurs.get("qte"));

		EMarket.getMetier().ajouterCommande(
				(int) (System.currentTimeMillis() / 1000),
				this.produit, client,
				qte, this.produit.getPrix() * qte
		);

		EMarket.getIhm().switchContentPanel(new CommandesPanel());
	}

	@Override
	public void onSelectionChanged(JComboBox comboBox, Object itemSelected) {
		if (comboBox == this.selectCateg.getField()) {
			List<Produit> produits = EMarket.getMetier().getProduitsDansCategorie(itemSelected.toString());
			List<String>  noms     = new ArrayList<>();

			if (produits.size() == 0) return;

			for (Produit prod : produits)
				noms.add(prod.getLib());

			this.selectProduits.setSelectDatas(noms.toArray(new String[0]));
			this.form.miseAjour();

			return;
		}

		if (comboBox == this.selectProduits.getField()) {
			if (comboBox.getSelectedItem() != null) {
				this.produit = EMarket.getMetier().getProduit(comboBox.getSelectedItem().toString());

				this.quantite.addPredicate(o -> Integer.parseInt(o.toString()) <= this.produit.getQs());
				this.quantite.getField().setEnabled(true);
			}
		}
	}
}
