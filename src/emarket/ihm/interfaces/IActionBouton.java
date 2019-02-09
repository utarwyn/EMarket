package emarket.ihm.interfaces;

import emarket.ihm.objets.formulaire.Bouton;

import java.awt.event.ActionEvent;

/**
 * Interface pour gérer le clic sur les boutons
 */
public interface IActionBouton {

	void onButtonAction(Bouton bouton, ActionEvent event);

}
