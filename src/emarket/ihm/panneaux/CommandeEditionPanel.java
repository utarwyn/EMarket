package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.interfaces.ISelectionChanged;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.formulaire.Champ;
import emarket.ihm.util.Fonts;
import emarket.metier.objets.Client;
import emarket.metier.objets.Commande;
import emarket.metier.objets.Produit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class CommandeEditionPanel extends ContentPanel implements IFormulaireEnvoye, ISelectionChanged {

	private Commande commande;
	private JPanel   northPan;

	private Formulaire form;
	private Produit    produit;
	private Champ      selectProduits;
	private Champ      selectCateg;
	private Champ      quantite;


	public CommandeEditionPanel(int numComm) {
		super("Edition de la commande");

		this.commande = EMarket.getMetier().getCommandeByNum(numComm);


		/*   Mise en page du panneau pour afficher les infos du client et produit   */
		JLabel titreProduit = new JLabel("Informations produit");
		titreProduit.setFont(Fonts.getFont("OpenSansBold").deriveFont(Font.PLAIN, 18));

		JLabel titreClient = new JLabel("Informations client");
		titreClient.setFont(Fonts.getFont("OpenSansBold").deriveFont(Font.PLAIN, 18));

		this.northPan.add(titreProduit);
		this.northPan.add(titreClient);

		this.northPan.add(new JLabel("N° produit : " + this.commande.getProduit().getIdProd()));
		this.northPan.add(new JLabel("N° client : " + this.commande.getClient().getIdClient()));
		this.northPan.add(new JLabel("Libellé / Catégorie : " + this.commande.getProduit().getLib() + " / " + this.commande.getProduit().getCatP().getNom()));
		this.northPan.add(new JLabel("Prénom : " + this.commande.getClient().getPrenom()));
		this.northPan.add(new JLabel("Quantité : " + this.commande.getProduit().getQs()));
		this.northPan.add(new JLabel("Nom : " + this.commande.getClient().getNom()));
	}


	@Override
	public void init() {
		JPanel panBody  = new JPanel(new BorderLayout());
		this.northPan = new JPanel(new GridLayout(4, 2, 15, 10));
		this.northPan.setOpaque(false);

		/*   Formulaire pour attacher un nouveau produit à la commande   */
		this.form = new Formulaire();

		form.creerTitre("Nouveau produit");
		this.selectCateg    = form.creerChamp("categ", "select", "Catégorie")
				.setSelectDatas(EMarket.getMetier().getNomsCategoriesProduits().toArray(new String[0]))
				.setSelectionChanged(this);
		this.selectProduits = form.creerChamp("libelle", "select", "Libellé")
				.setSelectionChanged(this);
		this.quantite       = form.creerChamp("qte", "text", "Qte à acheter")
				.setRegexVerification("^\\d+$");

		form.nouvelleLigne();
		form.setActionEnvoi(this);
		form.creerBoutonEnvoi("Modifier");

		this.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

		panBody.add(this.northPan, BorderLayout.NORTH);
		panBody.add(form.generer(), BorderLayout.CENTER);
		panBody.setOpaque(false);
		this.add(panBody);

		this.quantite.getField().setEnabled(false);
	}


	@Override
	public void onFormSubmit(Formulaire form) {
		Map<String, String> valeurs = form.getValeurs();

		int qte = Integer.parseInt(valeurs.get("qte"));

		EMarket.getMetier().modifierCommande(
				this.commande.getIdCommande(),
				this.produit, qte,
				this.produit.getPrix() * qte
		);

		EMarket.getIhm().switchContentPanel(new CommandesPanel());
	}

	@Override
	public void onSelectionChanged(JComboBox comboBox, Object itemSelected) {
		if (comboBox == this.selectCateg.getField()) {
			java.util.List<Produit> produits = EMarket.getMetier().getProduitsDansCategorie(itemSelected.toString());
			java.util.List<String> noms     = new ArrayList<>();

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
