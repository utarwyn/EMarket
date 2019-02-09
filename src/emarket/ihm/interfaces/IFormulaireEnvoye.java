package emarket.ihm.interfaces;

import emarket.ihm.objets.Formulaire;

/**
 * Interface pour gérer l'envoi de données via un formulaire
 */
public interface IFormulaireEnvoye {

	void onFormSubmit(Formulaire formulaire);

}
