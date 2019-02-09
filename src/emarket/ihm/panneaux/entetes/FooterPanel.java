package emarket.ihm.panneaux.entetes;

import emarket.EMarket;
import emarket.ihm.IHMConsts;
import emarket.ihm.Panel;
import emarket.ihm.objets.ImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FooterPanel extends Panel {

	private JButton backBtn;


	public FooterPanel() {
		super(null);

		this.setLayout(new GridLayout(1, 2));
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(IHMConsts.FOOTER_COLOR);
		this.setOpaque(true);
	}


	public void showBackButton(boolean b) {
		this.backBtn.setVisible(b);
		this.setVisible(true);
	}

	@Override
	public void init() {
		JButton btn   = new ImageButton("quitter.jpg", "quitter.survol.jpg");
		JPanel  left  = new JPanel(new FlowLayout(FlowLayout.LEFT , 0, 0));
		JPanel  right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		this.backBtn = new ImageButton("precedent.jpg", "precedent.survol.jpg");
		this.backBtn.addActionListener(actionEvent -> EMarket.getIhm().retourPanel());
		this.backBtn.setVisible(false);

		btn.addActionListener(actionEvent -> System.exit(0));

		left.setOpaque(false);
		right.setOpaque(false);

		left.add(this.backBtn);
		right.add(btn);

		this.add(left);
		this.add(right);
	}
}
