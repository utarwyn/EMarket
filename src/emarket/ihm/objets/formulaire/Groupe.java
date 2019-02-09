package emarket.ihm.objets.formulaire;

import emarket.ihm.objets.Formulaire;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Groupe extends ObjetFormulaire {

	private List<ObjetFormulaire> objets;


	public Groupe(Formulaire form, int displayNbCol, int width) {
		super(form, displayNbCol, width);

		this.objets = new ArrayList<>();
	}


	public void addObjet(ObjetFormulaire objet) {
		objet.setInGroup(true);
		this.objets.add(objet);
	}

	@Override
	public JPanel generer() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);

		for (ObjetFormulaire objet : this.objets)
			panel.add(objet.generer());

		return panel;
	}
}
