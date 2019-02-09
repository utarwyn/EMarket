package emarket.metier.sql.tables;

import emarket.metier.sql.Database;
import emarket.metier.sql.Table;

import java.sql.Date;

public class FournisseurTable extends Table {

	@Override
	public void creer() {
		Database.execute(
			"CREATE TABLE fournisseurs (" +
				"  id_f serial PRIMARY KEY," +
				"  nom_f varchar(255) NOT NULL," +
				"  prenom_f varchar(255) NOT NULL," +
				"  ville_f varchar(255) NOT NULL," +
				"  societe varchar(255) NOT NULL" +
			");\n"
		);
	}


	public boolean creerFournisseur(String prenom, String nom, String ville, String societe) {
		return Database.executePreparedUpdate(
				"INSERT INTO " + this.table +
				"(prenom_f, nom_f, ville_f, societe) " +
				"VALUES (?, ?, ?, ?)",

				prenom, nom, ville, societe
		) > 0;
	}

	public boolean modifierFournisseur(int idFourn, String prenom, String nom, String ville, String societe) {
		return Database.executePreparedUpdate(
				"UPDATE " + this.table + " SET " +
				"prenom_f = ?, nom_f = ?, ville_f = ?, societe = ? " +
				"WHERE id_f = ?",

				prenom, nom, ville, societe,
				idFourn
		) > 0;
	}

	public boolean supprimerFournisseur(int idFourn) {
		return Database.executePreparedUpdate(
				"DELETE FROM " + this.table + " WHERE id_f = ?",
				idFourn
		) > 0;
	}

}
