package emarket.ihm.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire pour gérer le chargement d'image interne au programme
 */
public class Art {

	private static Map<String, BufferedImage> images;

	private Art() {}


	/**
	 * Retourne l'image localisée dans le dossier /res/images/ (Dans le programme!)
	 * @param name Le fichier image à charger (avec l'extension)
	 * @return L'image chargée
	 */
	public static BufferedImage getImage(String name) {
		if (Art.images == null)           Art.images = new HashMap<>();
		if (Art.images.containsKey(name)) return Art.images.get(name);

		InputStream   is  = Art.class.getResourceAsStream("/res/images/" + name);
		BufferedImage img = null;

		try {
			img = ImageIO.read(is);
			Art.images.put(name, img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

}
