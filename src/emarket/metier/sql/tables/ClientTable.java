package emarket.metier.sql.tables;

import emarket.metier.sql.Database;
import emarket.metier.sql.Table;

import java.sql.Date;

public class ClientTable extends Table {

	@Override
	public void creer() {
		Database.execute(
			"CREATE TABLE " + this.table + " ("   +
				"id_c serial PRIMARY KEY,"        +
				"prenom_c varchar(100) NOT NULL," +
				"nom_c varchar(100) NOT NULL,"    +
				"ville_c varchar(100) NOT NULL,"  +
				"date_naissance date NOT NULL"    +
			")"
		);
	}


	public boolean createClient(String prenom, String nom, String ville, Date dateNaissance) {
		return Database.executePreparedUpdate(
			"INSERT INTO " + this.table +
			"(prenom_c, nom_c, ville_c, date_naissance) " +
			"VALUES (?, ?, ?, ?)",

			prenom, nom, ville, dateNaissance
		) > 0;
	}

	public boolean modifierClient(int idClient,
								  String prenom, String nom, String ville, Date dateNaissance) {
		return Database.executePreparedUpdate(
			"UPDATE " + this.table + " SET " +
			"prenom_c = ?, nom_c = ?, ville_c = ?, date_naissance = ? " +
			"WHERE id_c = ?",

			prenom, nom, ville, dateNaissance,
			idClient
		) > 0;
	}

	public boolean supprimerClient(int idClient) {
		return Database.executePreparedUpdate(
				"DELETE FROM " + this.table + " WHERE id_c = ?",
				idClient
		) > 0;
	}

}
