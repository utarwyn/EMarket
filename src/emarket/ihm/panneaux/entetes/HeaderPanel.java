package emarket.ihm.panneaux.entetes;

import emarket.ihm.IHMConsts;
import emarket.ihm.Panel;
import emarket.ihm.util.Art;
import emarket.ihm.util.Fonts;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

public class HeaderPanel extends Panel implements ActionListener {

	private JPanel barreHaute;
	private JPanel barreBasse;

	private JLabel     heure;
	private DateFormat presentation;

	private JLabel     titreBarre;


	public HeaderPanel() {
		super(null);

		this.setLayout(new BorderLayout());

		this.barreHaute.setBackground(IHMConsts.HEADER_COLOR);
		this.barreHaute.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(30, 56, 68)),
				BorderFactory.createEmptyBorder(10, 20, 10, 20)
		));
		this.barreBasse.setBackground(IHMConsts.HEADER_B_COLOR);
		this.barreBasse.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 4, 0, new Color(235, 238, 242)),
				BorderFactory.createEmptyBorder(5, 20, 5, 20)
		));


		this.add(this.barreHaute, BorderLayout.NORTH);
		this.add(this.barreBasse, BorderLayout.SOUTH);
	}


	public void setTitreBarre(String titre) { this.titreBarre.setText(titre); }

	@Override
	public void init() {
		this.barreHaute = new JPanel(new GridLayout(1, 2));
		this.barreBasse = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// On créé tout d'abord le logo
		JLabel picLabel = new JLabel(new ImageIcon(Art.getImage("logo.png")), SwingConstants.LEFT);
		picLabel.setPreferredSize(new Dimension(201, 50));
		picLabel.setSize(new Dimension(201, 50));

		this.barreHaute.add(picLabel);

		// On affiche l'heure à droite du panel
		this.heure        = new JLabel("", SwingConstants.RIGHT);
		this.presentation = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);

		this.heure.setFont(Fonts.getFont("OpenSansBold").deriveFont(Font.BOLD, 15));
		this.heure.setForeground(IHMConsts.TEXT_COLOR.brighter().brighter());

		this.barreHaute.add(this.heure);


		this.titreBarre = new JLabel("", SwingConstants.LEFT);
		this.titreBarre.setFont(Fonts.getFont("OpenSansSemibold").deriveFont(Font.PLAIN, 18));
		this.titreBarre.setForeground(IHMConsts.TITLE_COLOR);

		this.barreBasse.add(this.titreBarre);

		new Timer(500, this).start();
	}


	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		this.heure.setText(this.presentation.format(new Date()));
	}
}
