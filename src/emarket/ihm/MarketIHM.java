package emarket.ihm;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale de gestion de l'IHM
 */
public class MarketIHM {

	private List<Panel> derniersPanels;
	private Frame       frame;


	public MarketIHM() {
		this.derniersPanels = new ArrayList<>();

		SwingUtilities.invokeLater(() -> this.frame = new Frame());
	}


	/**
	 * Permet de retourner au panneau précédent (s'il existe)
	 */
	public void  retourPanel() {
		if (this.derniersPanels.size() == 0) return;
		Panel dernier = this.derniersPanels.get(this.derniersPanels.size() - 1);

		this.derniersPanels.remove(dernier);

		try {
			this.frame.setPanel(dernier.getClass().newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			// Impossible d'appeler l'instance du panel, on tente celui d'avant.
			this.retourPanel();
		}

		this.frame.getFooterPanel().showBackButton(this.derniersPanels.size() != 0);
	}

	/**
	 * Réinitialise l'historique des précédents panneaux
	 */
	public void  resetHistorique() {
		this.derniersPanels.clear();

		if (this.frame != null && this.frame.getFooterPanel() != null)
			this.frame.getFooterPanel().showBackButton(false);
	}

	/**
	 * Retourne en arrière de X panneaux
	 * @param nb Nombre de panneaux à supprimer
	 */
	public void  retourArriereHistorique(int nb) {
		for (int i = nb; i > 0; i--)
			if (this.derniersPanels.size() > 0)
				this.derniersPanels.remove(this.derniersPanels.size() - 1);

		this.frame.getFooterPanel().showBackButton(this.derniersPanels.size() != 0);
	}

	/**
	 * Retourne le dernier panneau dans l'historique
	 * @return Le dernier panneau
	 */
	public Panel getDernierPanel() {
		if (this.derniersPanels.size() == 0) return null;
		return this.derniersPanels.get(this.derniersPanels.size() - 1);
	}


	/**
	 * Changer de panneau de contenu et mettre l'ancien panneau dans l'historique
	 * @param contentPan Le nouveau panneau à afficher
	 */
	public void switchContentPanel(ContentPanel contentPan) {
		// On enregistre le dernier panneau dans l'historique
		if (this.getDernierPanel() != this.frame.getContentPanel())
			this.derniersPanels.add(this.frame.getContentPanel());

		// On définit le nouveau panneau
		this.frame.setPanel(contentPan);
		this.frame.getFooterPanel().showBackButton(true);
	}


	/**
	 * Recharge le panneau en cours
	 */
	public void rechargerPanel() {
		try {
			this.frame.setPanel(this.frame.getContentPanel().getClass().newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de mettre à jour la totalité de l'IHM (La frame principale)
	 */
	public void miseAJour() { this.frame.setVisible(true); }


	/**
	 * Enovie une alerte à l'utilisateur
	 * @param titre Le titre de l'alerte
	 * @param message Le message de l'alerte
	 */
	public void    alerteErreur(String titre, String message) {
		JOptionPane.showMessageDialog(null, message, titre, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Envoie une fenêtre de confirmation à l'utilisateur
	 * @param message Le message à afficher dans la fenêtre
	 * @return L'accord (ou non) de l'utilisateur
	 */
	public boolean confirmation(String message) {
		return JOptionPane.showConfirmDialog (null, message, "Confirmation", JOptionPane.YES_NO_OPTION) ==
				JOptionPane.YES_OPTION;
	}
}