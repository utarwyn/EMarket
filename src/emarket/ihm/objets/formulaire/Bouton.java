package emarket.ihm.objets.formulaire;

import emarket.ihm.interfaces.IActionBouton;
import emarket.ihm.objets.Formulaire;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bouton extends ObjetFormulaire implements ActionListener {

	private String        titre;
	private IActionBouton action;

	/* Classe pour afficher un bouton dans un formulaire */
	public Bouton(Formulaire form, int displayNbCol, String titre) {
		super(form, displayNbCol, 0);

		this.titre = titre;
	}


	public String getTitre() { return this.titre; }
	public void   setAction(IActionBouton actionBouton) { this.action = actionBouton; }


	@Override // Génération du bouton dans un JPanel
	public JPanel generer() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);

		JButton button = new JButton(this.titre);
		button.addActionListener(this);


		panel.add(button);

		return panel;
	}


	@Override // Une action a eu lieu sur le bouton
	public void actionPerformed(ActionEvent actionEvent) {
		if (this.action != null) this.action.onButtonAction(this, actionEvent);
	}
}
