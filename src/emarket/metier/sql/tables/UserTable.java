package emarket.metier.sql.tables;

import emarket.metier.sql.Database;
import emarket.metier.sql.Table;

import java.sql.ResultSet;

public class UserTable extends Table {


	public ResultSet findUser(String email, String password) {
		return Database.executePreparedQuery(
			"SELECT * FROM " + this.table + " WHERE email_user = ? AND password_user = ?",
			email, password
		);
	}

	public boolean   createUser(String prenom, String nom, String email, String password) {
		return Database.executePreparedUpdate(
				"INSERT INTO " + this.table                           +
				"(prenom_user, nom_user, email_user, password_user) " +
				"VALUES(?, ?, ?, ?)",
				prenom, nom, email, password
		) > 0;
	}


	@Override
	public void creer() {
		Database.execute(
			"CREATE TABLE users ( "                  +
				"id_user serial PRIMARY KEY,"        +
				"nom_user varchar(50) NOT NULL,"     +
				"prenom_user varchar(50) NOT NULL,"  +
				"email_user varchar(50) NOT NULL,"   +
				"password_user varchar(50) NOT NULL" +
			");"
		);
	}

}
