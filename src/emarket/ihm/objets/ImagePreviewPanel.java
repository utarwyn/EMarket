package emarket.ihm.objets;

import emarket.ihm.objets.formulaire.ObjetFormulaire;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Classe pour afficher une image (sous forme de prévisualisation) dans un panneau
 */
public class ImagePreviewPanel extends ObjetFormulaire {

	private int           height;
	private BufferedImage image;

	private JPanel        panel;
	private JLabel        imageLabel;


	public ImagePreviewPanel(Formulaire form, int height) {
		this(form, height, null);
	}
	public ImagePreviewPanel(Formulaire form, int height, BufferedImage image) {
		super(form, 1, 1);

		this.height = height;
		this.image  = image;

		this.update();
	}


	/**
	 * Permet de lier une image à la prévisualisation
	 * @param image Image à attacher
	 */
	public void setImage(BufferedImage image) { this.image = image; this.update(); }

	/**
	 * Permet de lier une image sous forme de chaîne de caractères à la prévisualisation
	 * @param imageStr Image à attacher sous forme de chaîne
	 */
	public void setStringImage(String imageStr) {
		byte[]               b   = DatatypeConverter.parseBase64Binary(imageStr);
		ByteArrayInputStream bis = new ByteArrayInputStream(b);

		try {
			this.setImage(ImageIO.read(bis));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void update() {
		if (this.image == null || this.imageLabel == null) return;

		int   panelHeight = (int) this.panel.getPreferredSize().getHeight();
		int   newWidth    = this.image.getWidth() / this.image.getHeight() * panelHeight;

		Image newImage    = this.image.getScaledInstance(newWidth, panelHeight, Image.SCALE_SMOOTH);

		this.imageLabel.setIcon(new ImageIcon(newImage));
		this.panel.setVisible(true);
	}

	/**
	 * Permet de générer un panneau contenant l'image
	 * @return Le panneau généré
	 */
	@Override
	public JPanel generer() {
		this.panel      = new JPanel();
		this.imageLabel = new JLabel(new ImageIcon(), SwingConstants.CENTER);

		panel.setPreferredSize(new Dimension(this.height, this.height));
		panel.setBackground(new Color(200, 200, 200));
		panel.setOpaque(true);

		panel.add(this.imageLabel);
		return panel;
	}
}
