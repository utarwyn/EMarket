package emarket.ihm.objets;

import emarket.EMarket;
import emarket.ihm.interfaces.IActionBouton;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.objets.formulaire.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe pour générer rapidement un formulaire formaté
 */
public class Formulaire {

	private int                   nbCol;
	private List<ObjetFormulaire> objets;

	private Bouton                boutonEnvoi;
	private IFormulaireEnvoye     actionEnvoi;

	private JPanel        panel;
	private GridBagLayout gridBagLayout;


	public Formulaire() {
		this.objets = new ArrayList<>();
		this.nbCol  = 1;

		this.gridBagLayout = new GridBagLayout();
	}

	/**
	 * Permet de créer un champ spécial dans le formulaire
	 * @param name Le nom du champ
	 * @param type Le type du champ (text, password, select, file)
	 * @param label Le texte de présentation du champ
	 * @return Le champ créé par la méthode
	 */
	public Champ creerChamp(String name, String type, String label) {
		Champ ch = new Champ(this, name, nbCol, label);
		ch.setType(type);

		this.objets.add(ch);
		return ch;
	}

	/**
	 * Permet de créer un groupe pouvant contenir des champs
	 * @param taille La taille du groupe
	 * @return Le groupe créé par la méthode
	 */
	public Groupe creerGroupe(int taille) {
		Groupe grp = new Groupe(this, 1, taille);
		this.objets.add(grp);
		return grp;
	}

	/**
	 * Permet de créer un titre
	 * @param titre Le titre
	 * @return L'objet titre précédemment créé
	 */
	public Titre creerTitre(String titre) {
		Titre tr = new Titre(this, titre, nbCol);

		this.objets.add(tr);
		return tr;
	}

	/**
	 * Permet de créer un titre
	 * @param titre Le titre
	 * @param taille La taille de la police du titre
	 * @return L'objet titre précédemment créé
	 */
	public Titre creerTitre(String titre, int taille) {
		return this.creerTitre(titre).setSize(taille);
	}

	/**
	 * Permet de créer un titre
	 * @param titre Le titre
	 * @param taille La taille de la police du titre
	 * @param couleur La couleur du texte
	 * @return L'objet titre précédemment créé
	 */
	public Titre creerTitre(String titre, int taille, Color couleur) {
		return this.creerTitre(titre, taille).setcolor(couleur);
	}

	/**
	 * Permet de créer un titre
	 * @param titre Le titre
	 * @param taille La taille de la police du titre
	 * @param alignement L'alignement du titre
	 * @return L'objet titre précédemment créé
	 */
	public Titre creerTitre(String titre, int taille, int alignement) {
		return this.creerTitre(titre, taille).setAlignment(alignement);
	}

	/**
	 * Permet de créer un bouton d'envoi
	 * @param titre Titre affiché dans le bouton
	 * @return Le bouton précédemment créé
	 */
	public Bouton creerBoutonEnvoi(String titre) {
		if (this.boutonEnvoi != null) return null;
		this.boutonEnvoi = new Bouton(this, nbCol, titre);

		this.boutonEnvoi.setAction((bouton, event) -> this.envoyer());

		this.objets.add(this.boutonEnvoi);
		return this.boutonEnvoi;
	}

	/**
	 * Permet de créer un bouton classique
	 * @param titre Titre affiché dans le bouton
	 * @param actionBouton Action à executer lors du clic sur le bouton
	 * @return Le bouton précédemment créé
	 */
	public Bouton creerBouton(String titre, IActionBouton actionBouton) {
		Bouton bouton = new Bouton(this, nbCol, titre);
		bouton.setAction(actionBouton);

		this.objets.add(bouton);
		return bouton;
	}

	/**
	 * Permet de passer à la ligne dans la mise en page du formulaire
	 */
	public void nouvelleLigne() {
		this.objets.add(new Separateur(this));
	}

	/**
	 * Permet de créer un visuel d'image dans le formulaire
	 * @param height Hauteur du composant
	 * @return Le panneau de prévisualisation
	 */
	public ImagePreviewPanel creerPrevisuImage(int height) {
		return this.creerPrevisuImage(height, null);
	}

	/**
	 * Permet de créer un visuel d'image dans le formulaire
	 * @param height Hauteur du composant
	 * @param image Image à afficher dans le composant
	 * @return Le panneau de prévisualisation
	 */
	public ImagePreviewPanel creerPrevisuImage(int height, BufferedImage image) {
		ImagePreviewPanel previ = new ImagePreviewPanel(this, height, image);

		this.objets.add(previ);
		return previ;
	}

	/**
	 * Retourne l'objet présent à un certain indice dans le formulaire
	 * @param index L'indice où se situe l'objet à chercher
	 * @return L'objet trouvé
	 */
	public ObjetFormulaire     getObjet      (int    index) { return this.objets.get(index); }

	/**
	 * Retourne le bouton d'envoi du fomrulaire (s'il existe)
	 * @return Le bouton d'envoi
	 */
	public Bouton              getBoutonEnvoi            () { return this.boutonEnvoi;       }

	/**
	 * Retourne un champ particulier suivant son nom
	 * @param name Nom du champ à retourner
	 * @return Le champ trouvé
	 */
	public Champ               getChamp      (String name ) {
		for (ObjetFormulaire objet : this.objets)
			if (objet instanceof Champ && ((Champ) objet).getName().equals(name))
				return (Champ) objet;

		return null;
	}

	/**
	 * Retourne toutes les valeurs des champs du formulaire
	 * @return Un objet clés/valeurs des champs
	 */
	public Map<String, String> getValeurs() {
		Map<String, String> valeurs = new HashMap<>();

		for (ObjetFormulaire objet : this.objets)
			if (objet instanceof Champ) {
				Champ ch = (Champ) objet;
				valeurs.put(ch.getName(), ch.getValeur());
			}

		return valeurs;
	}

	/**
	 * Définir le nombre courant de colonne pour la mise en page
	 * @param nbCol Nombre de colonne
	 */
	public void setNombreColonne(int nbCol) {
		this.nbCol = nbCol;
	}

	/**
	 * Définir l'action à effectuer lors du clic sur le bouton d'envoi
	 * @param actionEnvoi Action à executer
	 */
	public void setActionEnvoi(IFormulaireEnvoye actionEnvoi) {
		this.actionEnvoi = actionEnvoi;
	}


	/**
	 * Génère et retourne le panneau contenant l'ensemble du formulaire
	 * @return Le JPanel du formulaire
	 */
	public JPanel generer  () {
		JPanel panel;
		int x = 0, y = 0;

		if (this.panel != null) {
			panel = this.panel;
			panel.removeAll();
		} else {
			panel = new JPanel(this.gridBagLayout);
		}

		for (ObjetFormulaire obj : this.objets) {
			// On traite les objets dans les groupes autrepart dans le programme
			if (obj.isInGroup()) continue;

			if (obj instanceof Separateur) {
				x = 0; y++;
				continue;
			}

			JPanel gch   = obj.generer();
			int    width = obj.getWidth();

			if (width < 1) width = this.getMaxColumns() / obj.getDisplayNbCol();

			this.gridBagLayout.setConstraints(gch, this.makeGbc(x, y, width));
			panel.add(gch);

			x = (x + 1) % obj.getDisplayNbCol();

			if (x == 0) {
				x = 0;
				y++;
			}
		}

		panel.setVisible(true);
		panel.setOpaque(false);
		this.panel = panel;

		return panel;
	}

	/**
	 * Lancer l'envoi du fomulaire
	 */
	public void   envoyer  () {
		for (ObjetFormulaire objet : this.objets)
			if (objet instanceof Champ && !((Champ) objet).isVerified()) {
				EMarket.getIhm().alerteErreur("Formulaire incorrect", "Un des champs a mal été rempli !\nVeuillez corriger ces derniers.");
				return;
			}

		if (this.actionEnvoi != null) this.actionEnvoi.onFormSubmit(this);
	}

	/**
	 * Permet de mettre à jour le formulaire
	 */
	public void   miseAjour() { this.generer(); }


	private GridBagConstraints makeGbc(int x, int y, int width) {
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridwidth  = width;
		gbc.gridheight = 1;
		gbc.gridx      = x;
		gbc.gridy      = y;
		gbc.weighty    = 0;
		gbc.insets     = new Insets(5, 5, 5, 5);
		gbc.anchor     = GridBagConstraints.PAGE_START;
		gbc.fill       = GridBagConstraints.HORIZONTAL;

		return gbc;
	}
	private int                getMaxColumns() {
		int maxC = 0;

		for (ObjetFormulaire obj : this.objets)
			if (obj.getDisplayNbCol() > maxC)
				maxC = obj.getDisplayNbCol();

		return maxC;
	}

}
