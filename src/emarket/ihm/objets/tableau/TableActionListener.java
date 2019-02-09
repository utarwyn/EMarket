package emarket.ihm.objets.tableau;

import emarket.ihm.interfaces.IActionBoutonTableau;
import emarket.ihm.objets.Tableau;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableActionListener implements ActionListener, ListSelectionListener {

	private Tableau tableau;
	private JPanel  actionPanel;
	private int     selectedRow;


	public TableActionListener(Tableau tableau, JPanel actionPanel) {
		this.tableau     = tableau;
		this.actionPanel = actionPanel;
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		JButton              bouton = (JButton) event.getSource();
		IActionBoutonTableau action = this.tableau.getBoutonAction(bouton);

		if (action == null) return;
		action.onBoutonAction(this.selectedRow, bouton.getText());
	}

	@Override
	public void valueChanged(ListSelectionEvent listSelectionEvent) {
		this.selectedRow = this.tableau.getSelectedRow();

		// On rend accessible tous les éléments du panel d'action
		for (Component c : this.actionPanel.getComponents())
			c.setEnabled(true);
	}
}
