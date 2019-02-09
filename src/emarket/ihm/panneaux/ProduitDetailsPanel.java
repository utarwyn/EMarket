package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.objets.ImagePreviewPanel;
import emarket.metier.objets.Produit;

import javax.swing.*;
import java.awt.*;

public class ProduitDetailsPanel extends ContentPanel {

	private Produit           produit;
	private JPanel            panInfos;
	private ImagePreviewPanel previewPanel;


	public ProduitDetailsPanel(int idProd) {
		super("Détails du produit");

		this.produit = EMarket.getMetier().getProduit(idProd);

		panInfos.add(new JLabel("N° produit : " + produit.getIdProd()));
		panInfos.add(new JLabel("Libellé produit : " + produit.getLib()));
		panInfos.add(new JLabel("Catégorie : " + produit.getCatP().getNom()));
		panInfos.add(new JLabel("Prix : " + String.format("%.2f", produit.getPrix())));
		panInfos.add(new JLabel("Quantité en stock : " + produit.getQs()));
		panInfos.add(new JLabel("Fournisseur : " + produit.getFourn().getSociete()));

		this.previewPanel.setStringImage(produit.getPhoto());
	}


	@Override
	public void init() {
		JPanel pan = new JPanel(new BorderLayout());
		pan.setOpaque(false);

		this.panInfos = new JPanel(new GridLayout(3, 2));
		panInfos.setOpaque(false);

		JPanel panPhoto = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 30));
		panPhoto.setOpaque(false);

		panPhoto.add(new JLabel("Photo du produit :"));
		this.previewPanel = new ImagePreviewPanel(null, 200);
		panPhoto.add(this.previewPanel.generer());

		this.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

		pan.add(panInfos, BorderLayout.NORTH);
		pan.add(panPhoto, BorderLayout.CENTER);
		this.add(pan);
	}
}
