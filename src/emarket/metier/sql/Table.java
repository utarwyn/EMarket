package emarket.metier.sql;

import java.sql.ResultSet;
import java.util.List;

/**
 * Classe pour gérer les tables SQL
 */
public abstract class Table {

	protected String table;


	public Table() { this(null); }
	public Table(String table, Class<? extends Table>... dependsOf) {
		// Si la table n'est pas passée au constructeur,
		// on tente de la récupérer automatiquement.
		if (table == null) {
			String className = getClass().getSimpleName();
			this.table = className.replace("Table", "").toLowerCase() + "s";
		} else {
			this.table = table;
		}

		// On créé les tables dépendantes si besoin
		for(Class<? extends Table> dependOf : dependsOf) {
			try {
				Table t = dependOf.newInstance();

				if (!Database.tableExists(t.table)) {
					System.out.println("[D] La table " + this.table + " a besoin de la table " + t.table + " !");
					t.creer();
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		// On lance la création de la table si elle
		// n'est pas déjà créée.
		if (!Database.tableExists(this.table)) {
			System.out.println("[D] Création de la table '" + this.table + "' ..");
			this.creer();
		}
	}


	/**
	 * Trouver un résultat particulier dans la table coresspondante
	 * @param id Identifiant à utiliser pour le test
	 * @return Le tuple résultat
	 */
	public SQLDataSet       find(int id) {
		ResultSet        result   = Database.executePreparedQuery("SELECT * FROM " + this.table + " WHERE id = ?", id);
		List<SQLDataSet> dataSets = SQLDataSet.fromResultSet(result);

		return dataSets.size() > 0 ? dataSets.get(0) : null;
	}

	/**
	 * Retourne tous les tuples de la table correspondante
	 * @return La liste des tuples résultats
	 */
	public List<SQLDataSet> all() {
		ResultSet result = Database.executeQuery("SELECT * FROM " + this.table);
		return SQLDataSet.fromResultSet(result);
	}


	/**
	 * Méthode appelée si la table n'existe pas déjà dans la base
	 */
	public abstract void creer();

}
