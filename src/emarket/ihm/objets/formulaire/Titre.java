package emarket.ihm.objets.formulaire;

import emarket.ihm.IHMConsts;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.util.Fonts;

import javax.swing.*;
import java.awt.*;

public class Titre extends ObjetFormulaire {

	private String text;
	private int    size;
	private Color  color;

	private int    alignment;


	public Titre(Formulaire form, String text, int displayNbCol) {
		super(form, displayNbCol, 0);

		this.text      = text;
		this.size      =   24;
		this.color     = IHMConsts.TITLE_COLOR;
		this.alignment = SwingConstants.CENTER;
	}

	public Titre setSize (int   size ) { this.size  = size;  return this; }
	public Titre setcolor(Color color) { this.color = color; return this; }
	public Titre setText (String text) { this.text  =  text; return this; }

	public Titre setAlignment(int alignment) { this.alignment = alignment; return this; }


	@Override
	public JPanel generer() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(this.text.toUpperCase(), this.alignment);

		label.setFont(Fonts.getFont("OpenSansBold").deriveFont(Font.PLAIN, this.size));
		label.setForeground(this.color);

		panel.setOpaque(false);
		panel.add(label);

		return panel;
	}
}
