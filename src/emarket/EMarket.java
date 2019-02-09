package emarket;

import emarket.ihm.MarketIHM;
import emarket.metier.MarketMetier;

/**
 * Classe principale, s'occupe de charger la partie IHM et métier.
 */
public class EMarket {

	private static MarketIHM    ihm;
	private static MarketMetier metier;


	public EMarket() {
		ihm    = new MarketIHM();
		metier = new MarketMetier();
	}

	/**
	 * Retourne la classe IHM du programme
	 * @return Classe de gestion IHM
	 */
	public static MarketIHM    getIhm   () { return ihm;    }

	/**
	 * Retourne la classe Metier du programme
	 * @return Classe de gestion Métier
	 */
	public static MarketMetier getMetier() { return metier; }


	/**
	 * Méthode lancée au début du programme
	 * @param args Arguments de lancement
	 */
	public static void main(String[] args) { new EMarket(); }

}