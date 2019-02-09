package emarket.ihm.objets.tableau;

import emarket.metier.util.DateUtil;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe liée au tableau qui permet de gérer le chargement des données dans les cases
 */
public class TableauDonnees {

	private String[]       colonnes;
	private List<Object[]> donnees;

	private TableauDonnees() {
		this.colonnes = new String[0];
		this.donnees  = new ArrayList<>();
	}


	public int getNbLignes  () { return this.donnees.size();  }
	public int getNbColonnes() { return this.colonnes.length; }

	public Object[][] toObjectArray() {
		if (this.donnees.size() == 0) return new Object[0][this.colonnes.length];
		Object[][] obj = new Object[this.donnees.size()][this.colonnes.length];

		for (int row = 0; row < this.donnees.size(); row++)
			for (int col = 0; col < this.donnees.get(row).length; col++)
				obj[row][col] = this.donnees.get(row)[col];

		return obj;
	}
	public String[]   getColonnes() { return this.colonnes; }

	public String   getColonne(int index) {
		return this.colonnes[index];
	}
	public Class<?> getTypeColonne(int index) {
		Object f = this.getValueAt(0, index);
		if (f == null) return Object.class;

		if (f instanceof  String) return  String.class;
		if (f instanceof Integer) return Integer.class;
		if (f instanceof   Float) return   Float.class;

		return Object.class;
	}
	public Object   getValueAt(int row, int col) {
		if (col > this.getNbColonnes() - 1) return null;
		return this.donnees.get(row)[col];
	}

	public void setNomsColonnes(String... noms) {
		this.colonnes = noms;
	}

	public void addDonnees(Object... valeurs) {
		this.donnees.add(valeurs);
	}



	public static TableauDonnees fromClassList(List<?> list) {
		TableauDonnees tab = new TableauDonnees();
		if (list.size() == 0) return tab;

		/*  On remplit le tableau  */
		for (int i = 0; i < list.size(); i++) {
			Object   obj    = list.get(i);
			Field[]  champs = obj.getClass().getDeclaredFields();
			Object[] datas  = new Object[champs.length];

			/*  On récupère les noms des champs  */
			if (i == 0) {
				String[] noms = new String[champs.length];

				for (int cptChamp = 0; cptChamp < champs.length; cptChamp++) {
					Field champ = champs[cptChamp];
					String nom = champ.getName();

					noms[cptChamp] = nom;
				}

				tab.setNomsColonnes(noms);
			}

			for (int cptChamp = 0; cptChamp < champs.length; cptChamp++) {
				Field champ = champs[cptChamp];

				champ.setAccessible(true);

				try {
					Object value = champ.get(obj);

					// Types spéciaux à traiter à part
					if (value instanceof Date)
						value = DateUtil.dateToString((Date) value);

					datas[cptChamp] = value;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			tab.addDonnees(datas);
		}

		return tab;
	}

}
