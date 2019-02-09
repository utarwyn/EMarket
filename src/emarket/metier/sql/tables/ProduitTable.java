package emarket.metier.sql.tables;

import emarket.metier.sql.Database;
import emarket.metier.sql.Table;

public class ProduitTable extends Table {

	public ProduitTable() {
		super("produits", FournisseurTable.class, CategorieProduitTable.class);
	}

	@Override
	public void creer() {
		Database.execute(
			"CREATE TABLE produits ("                +
				"  id_p serial PRIMARY KEY,"         +
				"  libelle varchar(100) NOT NULL,"   +
				"  prix float NOT NULL,"             +
				"  quantite integer NOT NULL,"       +
				"  photo text DEFAULT NULL,"         +
				"  id_categ integer references categories_produits (id_categ) NOT NULL," +
				"  id_fournisseur integer references fournisseurs (id_f) NOT NULL"       +
			") ;"
		);
	}



	public boolean creerProduit(String lib, float prix, int qs, int catPId, int fournId, String photo) {
		return Database.executePreparedUpdate(
				"INSERT INTO " + this.table +
				"(libelle, prix, quantite, id_categ, id_fournisseur, photo) " +
				"VALUES (?, ?, ?, ?, ?, ?)",

				lib, prix, qs, catPId, fournId, photo
		) > 0;
	}


	public boolean modifierProduit(int idProd,
								  String lib, float prix, int qs, int catPId, int fournId, String photo) {
		return Database.executePreparedUpdate(
				"UPDATE " + this.table + " SET " +
				"libelle = ?, prix = ?, quantite = ?, id_categ = ?, id_fournisseur = ?, photo = ? " +
				"WHERE id_p = ?",

				lib, prix, qs, catPId, fournId, photo, idProd
		) > 0;
	}

	public boolean supprimerProduit(int idProd) {
		return Database.executePreparedUpdate(
				"DELETE FROM " + this.table + " WHERE id_p = ?",
				idProd
		) > 0;
	}

}
