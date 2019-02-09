package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.objets.Formulaire;

public class IndexPanel extends ContentPanel implements IFormulaireEnvoye {

	public IndexPanel() {
		super("Entrez dans l'interface de gestion");
	}


	@Override
	public void init() {
		Formulaire form = new Formulaire();

		form.creerBoutonEnvoi("Entrer");
		form.setActionEnvoi(this);

		this.add(form.generer());
	}


	@Override
	public void onFormSubmit(Formulaire formulaire) {
		EMarket.getIhm().switchContentPanel(new ConnexionPanel());
		EMarket.getIhm().resetHistorique();
	}
}