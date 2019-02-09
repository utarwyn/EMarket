package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.ImagePreviewPanel;
import emarket.metier.util.DateUtil;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class ClientAjoutPanel extends ContentPanel implements IFormulaireEnvoye {


	public ClientAjoutPanel() { super("Ajouter un client"); }


	@Override
	public void init() {
		Formulaire form = new Formulaire();

		form.setNombreColonne(2);
		form.creerChamp("prenom", "text", "Prénom");
		form.creerChamp("nom", "text", "Nom");

		form.creerChamp("ville", "text", "Ville");
		form.creerChamp("naissance", "text", "Date de naissance").setRegexVerification(DateUtil.DATE_REGEX);

		form.setActionEnvoi(this);
		form.setNombreColonne(1);
		form.creerBoutonEnvoi("Ajouter");

		this.add(form.generer());
	}


	@Override
	public void onFormSubmit(Formulaire form) {
		Map<String, String> valeurs = form.getValeurs();

		try {
			EMarket.getMetier().ajouterClient(
					valeurs.get("prenom"),
					valeurs.get("nom"),
					valeurs.get("ville"),
					DateUtil.stringToDate(valeurs.get("naissance"))
			);
		} catch (ParseException e) {
			EMarket.getIhm().alerteErreur(
					"Problème de formatage",
					"Ajout impossible, la date est mal formatée.\nFormat: JJ/MM/AAAA"
			);

			form.getChamp("naissance").vider();
			return;
		}

		EMarket.getIhm().switchContentPanel(new ClientsPanel());
	}
}
