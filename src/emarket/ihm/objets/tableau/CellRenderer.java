package emarket.ihm.objets.tableau;

import emarket.ihm.IHMConsts;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/*   Gère le rendu des cellules d'un tableau   */
public class CellRenderer extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value,
												   boolean isSelected, boolean hasFocus, int row, int col) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		// On modifie les couleurs du tableau
		if (row % 2 == 0)
			c.setBackground(new Color(250, 251, 252));
		else
			c.setBackground(new Color(234, 239, 240));

		c.setForeground(Color.BLACK);

		// On change la couleur de fond lorsque la cellule est séléctionnée
		if (isSelected) c.setBackground(IHMConsts.SELECT_COLOR);


		// On modifie la bordure de la cellule
		if (row > 0)
			this.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
					BorderFactory.createEmptyBorder(5, 10, 5, 10)
			));
		else
			this.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		return c;
	}

}