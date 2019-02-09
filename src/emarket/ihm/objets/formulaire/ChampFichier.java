package emarket.ihm.objets.formulaire;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ChampFichier extends JButton implements ActionListener {

	private Champ        champ;
	private JFileChooser fileChooser;


	public ChampFichier(Champ champ) {
		this.champ       = champ;
		this.fileChooser = new JFileChooser();

		this.fileChooser.setAcceptAllFileFilterUsed(false);
		this.fileChooser.addChoosableFileFilter(new ImageFilter());
		this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

		this.setText("Choisissez un fichier");
		this.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		int result = this.fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = this.fileChooser.getSelectedFile();
			this.setText(selectedFile.getName());

			try {
				BufferedImage         image = ImageIO.read(selectedFile);
				ByteArrayOutputStream out   = new ByteArrayOutputStream();

				ImageIO.write(image, "PNG", out);

				this.champ.setValeur(DatatypeConverter.printBase64Binary(out.toByteArray()));

				if (this.champ.getPreviewPanel() != null)
					this.champ.getPreviewPanel().setImage(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
