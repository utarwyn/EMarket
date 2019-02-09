package emarket.metier.sql.tables;

import emarket.metier.sql.Database;
import emarket.metier.sql.Table;

public class CategorieProduitTable extends Table {

	public CategorieProduitTable() { super("categories_produits"); }

	@Override
	public void creer() {
		Database.execute(
			"CREATE TABLE " + this.table + " ("  +
				"id_categ serial PRIMARY KEY,"   +
				"nom_categ varchar(50) NOT NULL" +
			");"
		);

		// Ajout de quelques catégories de test
		this.insererCategorie("Fruits et légumes");
		this.insererCategorie("Viandes et poissons");
		this.insererCategorie("Pains et pâtisseries");
		this.insererCategorie("Crémerie");
		this.insererCategorie("Charcuterie");
		this.insererCategorie("Traiteur");
		this.insererCategorie("Surgelés");
		this.insererCategorie("Epicerie salée");
		this.insererCategorie("Epicerie sucrée");
		this.insererCategorie("Boissons");
		this.insererCategorie("Bio");
	}



	private void insererCategorie(String nom) {
		Database.executePreparedUpdate(
			"INSERT INTO " + this.table + "(nom_categ)" +
			"VALUES (?)",

			nom
		);
	}

}
