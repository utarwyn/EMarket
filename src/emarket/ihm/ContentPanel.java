package emarket.ihm;

import java.awt.*;

/**
 * Classe pour gérer les panneaux de contenu de l'interface
 */
public abstract class ContentPanel extends Panel {


	public ContentPanel(String title) {
		super(title);

		this.setLayout(new FlowLayout());
		this.setOpaque(false);
	}

	public abstract void init();

}

