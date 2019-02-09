package emarket.ihm.util;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire pour gérer le chargement de polices internes au programme
 */
public class Fonts {

	private static Map<String, Font> fonts;

	private Fonts() {}


	/**
	 * Retourne la police localisée dans /res/fonts (dans le programme!)
	 * @param name Le fichier de police à charger (avec l'extension)
	 * @return La police chargée
	 */
	public static Font getFont(String name) {
		if (Fonts.fonts == null)           Fonts.fonts = new HashMap<>();
		if (Fonts.fonts.containsKey(name)) return Fonts.fonts.get(name);

		InputStream is   = Fonts.class.getResourceAsStream("/res/fonts/" + name + ".ttf");
		Font        font = null;

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			Fonts.fonts.put(name, font);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		return font;
	}

}
