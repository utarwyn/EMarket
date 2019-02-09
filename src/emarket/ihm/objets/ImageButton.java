package emarket.ihm.objets;

import emarket.ihm.util.Art;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Classe bouton pour en afficher un sous forme d'image
 */
public class ImageButton extends JButton implements MouseListener {

	private BufferedImage image;
	private BufferedImage hoveredImage;
	private BufferedImage clickedImage;

	private boolean       hovered;
	private boolean       clicked;


	/**
	 * Créer un bouton-image
	 * @param imageName Nom de l'image à utiliser pour le bouton
	 */
	public ImageButton(String imageName) {
		this(imageName, null);
	}

	/**
	 * Créer un bouton-image
	 * @param imageName Nom de l'image à utiliser pour le bouton
	 * @param hoveredImageName Nom de l'image lors du survol
	 */
	public ImageButton(String imageName, String hoveredImageName) {
		this(imageName, hoveredImageName, null);
	}

	/**
	 * Créer un bouton-image
	 * @param imageName Nom de l'image à utiliser pour le bouton
	 * @param clickedImageName Nom de l'image lors du clic
	 */
	public ImageButton(String imageName, String hoveredImageName, String clickedImageName) {
		super("Mon bouton imagé");

		// On charge toutes les images
		this.image = Art.getImage(imageName);

		if (hoveredImageName != null) this.hoveredImage = Art.getImage(hoveredImageName);
		if (clickedImageName != null) this.clickedImage = Art.getImage(clickedImageName);

		// On modifie les infos importantes du bouton
		Dimension dim = new Dimension(this.image.getWidth(), this.image.getHeight());

		this.setBorder(null);
		this.setBorderPainted(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setSize(dim);
		this.setPreferredSize(dim);

		// On ajoute le gestionnaire pour l'animation du bouton
		this.addMouseListener(this);

		this.repaint();
	}


	protected void paintComponent(Graphics g) {
		BufferedImage image = this.image;

		// Image de survol
		if (this.hovered && this.hoveredImage != null)
			image = this.hoveredImage;

		// Image de clic
		if (this.clicked && this.clickedImage != null)
			image = this.clickedImage;

		// On dessine l'image à l'endroit du bouton
		g.drawImage(
				image,
				0, 0,
				(int) this.getSize().getWidth(),
				(int) this.getSize().getHeight(),
				null
		);

		g.dispose();
	}


	/*   Gestion de l'animation du bouton   */
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {  }
	@Override
	public void mousePressed(MouseEvent mouseEvent) { this.clicked = true; }
	@Override
	public void mouseReleased(MouseEvent mouseEvent) { this.clicked = false; }
	@Override
	public void mouseEntered(MouseEvent mouseEvent) { this.hovered = true; }
	@Override
	public void mouseExited(MouseEvent mouseEvent) { this.hovered = false; }
}
