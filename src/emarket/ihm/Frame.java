package emarket.ihm;

import emarket.ihm.panneaux.AccueilPanel;
import emarket.ihm.panneaux.IndexPanel;
import emarket.ihm.panneaux.entetes.FooterPanel;
import emarket.ihm.panneaux.entetes.HeaderPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Frame principale, gère les barres d'entête / de pied de page et le panneau de contenu
 */
public class Frame extends JFrame {

	private HeaderPanel headerPanel;
	private FooterPanel footerPanel;

	private Panel  contentPanel;
	private JPanel containerPanel;


	public Frame() {
		this.setSize(IHMConsts.FRAME_MIN_SIZE);
		this.setMinimumSize(IHMConsts.FRAME_MIN_SIZE);
		this.setLocationRelativeTo(null);

		this.getContentPane().setBackground(IHMConsts.BG_COLOR);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.headerPanel = new HeaderPanel();
		this.footerPanel = new FooterPanel();


		this.containerPanel = new JPanel();
		this.containerPanel.setLayout(new BorderLayout());
		this.containerPanel.setOpaque(false);

		this.add(this.containerPanel);

		this.add(this.headerPanel, BorderLayout.NORTH);
		this.setPanel(new AccueilPanel());
		this.add(this.footerPanel, BorderLayout.SOUTH);
	}


	/**
	 * Retourne le panneau d'en-tête
	 * @return Le panneau en en-tête
	 */
	public HeaderPanel getHeaderPanel () { return this.headerPanel;  }

	/**
	 * Retourne le panneau du bas de la fenêtre
	 * @return Le panneau pied de page
	 */
	public FooterPanel getFooterPanel () { return this.footerPanel;  }

	/**
	 * Retourne le panneau de contenu courant
	 * @return Panneau de contenu courant
	 */
	public Panel       getContentPanel() {
		return this.contentPanel;
	}


	/**
	 * Définit un panneau comme le contenu courant
	 * @param panel Panneau à mettre en avant
	 */
	public void setPanel(Panel panel) {
		String newTitle = IHMConsts.FRAME_TITLE.replace("%titre%", panel.getTitle());

		this.headerPanel.setTitreBarre(panel.getTitle());
		this.setTitle(newTitle);

		// On supprime l'ancien panel s'il y en a un
		if (this.contentPanel != null) this.containerPanel.remove(this.contentPanel);

		// on défini le nouveau panel, et on le paramètre correctement
		this.contentPanel = panel;
		panel.setLayout(new GridLayout(1, 1));

		this.containerPanel.add(this.contentPanel);

		this.setVisible(true);
	}

}