package emarket.ihm.objets;

import emarket.ihm.IHMConsts;
import emarket.ihm.interfaces.IActionBoutonTableau;
import emarket.ihm.objets.tableau.CellRenderer;
import emarket.ihm.objets.tableau.TableActionListener;
import emarket.ihm.objets.tableau.TableauDonnees;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Classe pour générer rapidement un tableau
 */
public class Tableau extends JPanel {

	private JTable            table;
	private JPanel            actionPanel;
	private DefaultTableModel modele;

	private TableActionListener                    actionListener;
	private HashMap<JButton, IActionBoutonTableau> actionBoutonTableau;


	public Tableau(TableauDonnees donnees, String... excludeColumns) {
		// On instancie tout ce qu'il faut pour créer notre tableau (dont les données)
		this.modele      = new DefaultTableModel(donnees.toObjectArray(), donnees.getColonnes()) {

			@Override
			public Class getColumnClass(int col) {
				Object o = this.getValueAt(0, col);
				if (o == null) return Object.class;

				if (o instanceof Integer) return Integer.class;
				if (o instanceof  String) return String.class;

				return Object.class;
			}

			@Override
			public boolean isCellEditable(int row, int col) { return false; }

		};
		this.table       = new JTable(this.modele);
		this.actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// Actions du tableau
		this.actionBoutonTableau = new HashMap<>();
		this.actionListener      = new TableActionListener(this, this.actionPanel);


		// On supprime les colonnes à exclure
		java.util.List<String> excludeList = Arrays.asList(excludeColumns);
		for (int col = 0; col < this.table.getColumnCount(); col++) {
			String name = this.table.getColumnName(col);

			if (excludeList.contains(name)) {
				this.table.removeColumn(this.table.getColumn(name));
				col--;
			}
		}


		// On paramètre notre tableau pour qu'il soit fonctionnel et sympa
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.table.setIntercellSpacing(new Dimension(0, 0));
		this.table.setRowSelectionAllowed(true);
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.setAutoCreateRowSorter(true);
		this.table.setAutoCreateColumnsFromModel(false);
		this.table.setPreferredScrollableViewportSize(this.table.getPreferredSize());
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getSelectionModel().addListSelectionListener(this.actionListener);

		if (this.table.getColumnCount() > 0)
			this.table.getRowSorter().toggleSortOrder(0);

		this.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		this.setLayout(new BorderLayout());


		// On applique les éléments de rendu sur le colonnes du tableau
		Enumeration<TableColumn> columns      = this.table.getColumnModel().getColumns();
		CellRenderer             cellRenderer = new CellRenderer();

		while (columns.hasMoreElements()) {
			TableColumn column = columns.nextElement();
			column.setCellRenderer(cellRenderer);
		}


		// On ajoute une barre de défilement au tableau, au cas-où il y ait trop de données
		JScrollPane scrollPan = new JScrollPane(
				this.table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);
		scrollPan.setBorder(BorderFactory.createEmptyBorder());

		this.actionPanel.setOpaque(false);
		scrollPan.setOpaque(false);
		scrollPan.getViewport().setBackground(IHMConsts.TABLE_BG_COLOR);
		this.table.setOpaque(false);
		this.setOpaque(false);


		// On met à jour les lignes et on l'ajoute au panel final
		this.updateRowHeight();
		this.add(this.actionPanel, BorderLayout.NORTH);
		this.add(scrollPan);
	}


	/**
	 * Retourne l'action à lancer sur le bouton d'action passé en paramètre
	 * @param bouton Un bouton d'action ajouté au tableau
	 * @return L'action à executer lors du clic sur le bouton
	 */
	public IActionBoutonTableau getBoutonAction(JButton bouton)   { return this.actionBoutonTableau.get(bouton);  }

	/**
	 * Retourne la valeur à un endroit donné du tableau
	 * @param ligne Ligne où chercher la valeur
	 * @param colonne Colonne où chercher la valeur
	 * @return L'objet du tableau à l'endroit de recherche
	 */
	public Object getValeur(int ligne, int colonne) { return this.table.getValueAt(ligne, colonne); }

	/**
	 * Retourne l'indice de la ligne actuellement sélectionnée dans le tableau
	 * @return
	 */
	public int    getSelectedRow() { return this.table.getSelectedRow(); }


	/**
	 * Défini la largeur des colonnes du tableau
	 * @param widths Largeur à appliquer
	 */
	public void setLargeurColonnes(int... widths) {
		TableColumnModel columnModel = this.table.getColumnModel();

		for (int i = 0; i < widths.length; i++)
			if (i < columnModel.getColumnCount())
				columnModel.getColumn(i).setMaxWidth(widths[i]);
			else
				break;
	}

	/**
	 * Défini la largeur de le colonne situé à un indice du tableau
	 * @param indice Indice de la colonne
	 * @param largeur Largeur à appliquer
	 */
	public void setLargeurColonne(int indice, int largeur) {
		if (indice >= this.table.getColumnCount()) return;
		this.table.getColumnModel().getColumn(indice).setPreferredWidth(largeur);
	}

	/**
	 * Permet de redéfinir les noms des colonnes du tableau
	 * @param noms Liste des noms de colonnes
	 */
	public void setNomsColonnes(String... noms) {
		TableColumnModel columnModel = this.table.getColumnModel();

		for (int i = 0; i < noms.length; i++)
			if (i < columnModel.getColumnCount())
				columnModel.getColumn(i).setHeaderValue(noms[i]);
			else
				break;

		this.table.getTableHeader().repaint();
	}


	/**
	 * Permet d'ajouter un bouton d'action au tableau
	 * @param texteBouton Texte à afficher sur le bouton d'action
	 * @param action Action à executer lors d'un clic sur le bouton
	 */
	public void ajouterActionBouton(String texteBouton, IActionBoutonTableau action) {
		JButton bouton = new JButton(texteBouton);
		bouton.addActionListener(this.actionListener);

		int selectedRow = this.table.getSelectedRow();
		bouton.setEnabled(selectedRow >= 0);

		this.actionBoutonTableau.put(bouton, action);
		this.actionPanel.add(bouton);
	}
	public void ajouterPiedPanel(JPanel panel) {
		this.add(panel, BorderLayout.SOUTH);
	}


	/**
	 * Permet de mettre à jour la hauteur des lignes en fonction de leur contenu
	 */
	public void updateRowHeight() {
		final int rowCount = table.getRowCount();
		final int colCount = table.getColumnCount();

		for (int i = 0; i < rowCount; i++) {
			int maxHeight = 0;
			for (int j = 0; j < colCount; j++) {
				final TableCellRenderer renderer = table.getCellRenderer(i, j);
				maxHeight = Math.max(maxHeight, table.prepareRenderer(renderer, i, j).getPreferredSize().height);
			}
			table.setRowHeight(i, maxHeight);
		}
	}
}
