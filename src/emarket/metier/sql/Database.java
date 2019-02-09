package emarket.metier.sql;

import java.sql.*;
import java.util.List;

/**
 * Classe de gestion de la base de données
 */
public class Database {

	private static Database    instance;

	private static final String HOST     = "localhost";
	private static final int    PORT     =        5432;
	private static final String USER     =  "mm161075";
	private static final String PASSWORD = "123456789";
	private static final String DATABASE =       "iut";


	private Connection conn;


	private Database() {
		try {
			Class.forName ("org.postgresql.Driver");
			System.out.println ("[D] Driver PostgreSQL chargé.");

			this.conn = DriverManager.getConnection (
					"jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE,
					USER, PASSWORD
			);

			System.out.println ("[D] Connexion à la base de données réussie.");
		}
		catch(SQLException | ClassNotFoundException e) { e.printStackTrace(); }
	}


	/**
	 * Retourne directement la connexion vers la base de données
	 * @return Connexion déjà ouverte
	 */
	public static Connection getConnection()
	{
		return Database.getInstance().conn;
	}


	/**
	 * Execute une requête simplement et retourne le résultat sous forme de ResultSet
	 * @param query La requête à executer
	 * @return Le résultat sous forme de ResultSet
	 */
	public static ResultSet executeQuery(String query) {
		try {
			Connection conn = getConnection();
			if (conn == null) return null;
			Statement  st   = conn.createStatement();

			return st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Execute une requête préparée et retourne le résultat sous forme de ResultSet
	 * @param query La requête à executer
	 * @param attributes Les attributs à intégrer à la requête
	 * @return Le résultat sous forme de ResultSet
	 */
	public static ResultSet executePreparedQuery(String query, Object... attributes) {
		try {
			Connection        conn = getConnection();
			if (conn == null) return null;
			PreparedStatement st   = conn.prepareStatement(query);

			for (int i = 0; i < attributes.length; i++)
				st.setObject(i + 1, attributes[i]);

			return st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * Execute une requête de mise à jour
	 * @param request La requête à executer
	 * @return Le nombre de tuples mis à jour
	 */
	public static int      execute(String request) {
		try {
			Connection conn = getConnection();
			if (conn == null) return -1;
			Statement  st   = conn.createStatement();

			return st.executeUpdate(request);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * Execute une requête préparée de mise à jour
	 * @param request La requête à executer
	 * @param attributes Les attributs à insérer dans la requête
	 * @return Le nombre de tuples mis à jour
	 */
	public static int      executePreparedUpdate(String request, Object... attributes) {
		try {
			Connection conn = getConnection();
			if (conn == null) return -1;
			PreparedStatement st   = conn.prepareStatement(request);

			for (int i = 0; i < attributes.length; i++)
				st.setObject(i + 1, attributes[i]);

			return st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * Retourne le dernier identifiant du tuple inséré dans la base
	 * @return Le dernier identifiant inséré
	 */
	public static int      getLastInsertId() {
		ResultSet set = Database.executeQuery("SELECT lastval()");
		try {
			if (set == null || !set.next()) return -1;

			return set.getInt("lastval");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * Retourne si une table existe dans la base de données
	 * @param table Le nom de la table à tester
	 * @return La table existe-t-elle ?
	 */
	public static boolean  tableExists(String table) {
		try {
			ResultSet set = Database.executePreparedQuery(
					"select exists(select 1 from information_schema.tables where table_name = ?)",
					table
			);

			return set != null && set.next() && set.getBoolean(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}



	private static Database getInstance() {
		if (instance == null) instance = new Database();
		return instance;
	}
	
}
