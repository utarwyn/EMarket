package emarket.ihm.interfaces;

import javax.swing.*;

/**
 * Interface pour gérer le changement de valeur sur une JComboBox
 */
public interface ISelectionChanged {

	void onSelectionChanged(JComboBox comboBox, Object itemSelected);

}
