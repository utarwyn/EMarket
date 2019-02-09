package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.interfaces.IFormulaireEnvoye;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.formulaire.Titre;
import emarket.metier.objets.Client;
import emarket.metier.util.DateUtil;

import java.text.ParseException;
import java.util.Map;

public class ClientEditionPanel extends ContentPanel implements IFormulaireEnvoye {

	private Client client;

	private Formulaire form;
	private Titre      titre;


	public ClientEditionPanel(int idClient) {
		super("Edition d'un client");

		this.client = EMarket.getMetier().getClient(idClient);

		// On met à jour notre IHM avec les bonnes valeurs
		this.titre.setText("Client n°" + this.client.getIdClient() + " : " + this.client.getNom() + " " + this.client.getPrenom());

		this.form.getChamp("prenom").setValeur(this.client.getPrenom());
		this.form.getChamp("nom").setValeur(this.client.getNom());
		this.form.getChamp("ville").setValeur(this.client.getVille());
		this.form.getChamp("naissance").setValeur(DateUtil.dateToString(this.client.getdateNaissance()));

		this.form.miseAjour();
	}


	@Override
	public void init() {
		this.form = new Formulaire();

		this.titre = form.creerTitre("Chargement...");

		form.setNombreColonne(2);
		form.creerChamp("prenom", "text", "Prénom");
		form.creerChamp("nom", "text", "Nom");

		form.creerChamp("ville", "text", "Ville");
		form.creerChamp("naissance", "text", "Date de naissance").setRegexVerification(DateUtil.DATE_REGEX);

		form.setActionEnvoi(this);
		form.setNombreColonne(1);
		form.creerBoutonEnvoi("Modifier");

		this.add(form.generer());
	}


	@Override
	public void onFormSubmit(Formulaire form) {
		Map<String, String> valeurs = form.getValeurs();

		try {
			EMarket.getMetier().modifierClient(
					this.client.getIdClient(),
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
