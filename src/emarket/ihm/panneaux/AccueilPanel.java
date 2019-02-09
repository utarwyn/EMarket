package emarket.ihm.panneaux;

import emarket.EMarket;
import emarket.ihm.ContentPanel;
import emarket.ihm.IHMConsts;
import emarket.ihm.interfaces.IActionBouton;
import emarket.ihm.objets.AccueilBouton;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.ImageButton;
import emarket.ihm.objets.formulaire.Bouton;
import emarket.ihm.util.Art;
import emarket.ihm.util.Fonts;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilPanel extends ContentPanel implements ActionListener, ChangeListener {

	private AccueilBouton clientsBtn;
	private AccueilBouton stockBtn;
	private AccueilBouton commandesBtn;
	private AccueilBouton fournisseursBtn;


	public AccueilPanel() { super("Interface de gestion"); }


	@Override
	public void init() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 50, 50));
		panel.setOpaque(false);

		this.clientsBtn      = new AccueilBouton(     "Gestion des clients",      new ImageIcon(Art.getImage("icon.clients.png")));
		this.stockBtn        = new AccueilBouton(        "Gestion du stock",        new ImageIcon(Art.getImage("icon.stock.png")));
		this.commandesBtn    = new AccueilBouton(   "Gestion des commandes",    new ImageIcon(Art.getImage("icon.commandes.png")));
		this.fournisseursBtn = new AccueilBouton("Gestion des fournisseurs", new ImageIcon(Art.getImage("icon.fournisseurs.png")));

		this.paramBouton(this.clientsBtn);
		this.paramBouton(this.stockBtn);
		this.paramBouton(this.commandesBtn);
		this.paramBouton(this.fournisseursBtn);

		this.clientsBtn.addActionListener(this);
		this.stockBtn.addActionListener(this);
		this.commandesBtn.addActionListener(this);
		this.fournisseursBtn.addActionListener(this);

		panel.add(this.clientsBtn);
		panel.add(this.stockBtn);
		panel.add(this.commandesBtn);
		panel.add(this.fournisseursBtn);

		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		this.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JButton bouton = (JButton) actionEvent.getSource();

		if (bouton == this.clientsBtn)
			EMarket.getIhm().switchContentPanel(new ClientsPanel());

		if (bouton == this.stockBtn)
			EMarket.getIhm().switchContentPanel(new ProduitsPanel());

		if (bouton == this.commandesBtn)
			EMarket.getIhm().switchContentPanel(new CommandesPanel());

		if (bouton == this.fournisseursBtn)
			EMarket.getIhm().switchContentPanel(new FournisseursPanel());
	}

	private void paramBouton(AccueilBouton bouton) {
		bouton.setBorder(BorderFactory.createLineBorder(IHMConsts.BUTTON_B_COLOR, 3));
		bouton.setBorderPainted(true);
		bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bouton.setFocusPainted(false);

		bouton.setBackground(IHMConsts.BUTTON_COLOR);
		bouton.setPressedBackgroundColor(IHMConsts.BUTTON_B_COLOR);
		bouton.setHoverBackgroundColor(IHMConsts.BUTTON_H_COLOR);
		bouton.setForeground(IHMConsts.HEADER_B_COLOR);
		bouton.setFont(Fonts.getFont("OpenSansBold").deriveFont(Font.BOLD, 19));

		bouton.addChangeListener(this);
	}


	@Override
	public void stateChanged(ChangeEvent changeEvent) {
		JButton     bouton      = (JButton) changeEvent.getSource();
		ButtonModel boutonModel = bouton.getModel();

		if (boutonModel.isPressed()) bouton.setBackground(IHMConsts.BUTTON_B_COLOR);
		else                         bouton.setBackground(IHMConsts.BUTTON_COLOR);
	}
}
