package emarket.metier.sql.tables;

import emarket.metier.sql.Database;
import emarket.metier.sql.Table;

import java.sql.Date;

public class CommandeTable extends Table {

	@Override
	public void creer() {
		Database.execute(
			"CREATE TABLE commandes ("        +
				"id_comm serial PRIMARY KEY," +
				"num_comm integer NOT NULL,"  +
				"id_p integer references produits(id_p) NOT NULL," +
				"id_c integer references clients (id_c) NOT NULL," +
				"qte_achete integer NOT NULL," +
				"prix_total float NOT NULL,"   +
				"date date NOT NULL"           +
			")"
		);
	}


	public boolean creerCommande(int num, int idProduit, int idClient, int qteAchete, float prixTotal) {
		return Database.executePreparedUpdate(
				"INSERT INTO " + this.table +
				"(num_comm, id_p, id_c, qte_achete, prix_total, date) " +
				"VALUES (?, ?, ?, ?, ?, ?)",

				num, idProduit, idClient, qteAchete, prixTotal, new Date(System.currentTimeMillis())
		) > 0;
	}

	public boolean modifierCommande(int idComm, int idProduit, int qteAchete, float prixTotal) {
		return Database.executePreparedUpdate(
				"UPDATE " + this.table + " SET " +
				"id_p = ?, qte_achete = ?, prix_total = ? " +
				"WHERE id_comm = ?",

				idProduit, qteAchete, prixTotal,
				idComm
		) > 0;
	}

	public boolean supprimerCommande(int numComm) {
		return Database.executePreparedUpdate(
				"DELETE FROM " + this.table + " WHERE num_comm = ?",
				numComm
		) > 0;
	}

}
