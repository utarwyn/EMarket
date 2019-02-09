package emarket.metier.sql;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class SQLDataSet {

	private Map<String, Object> datas;
	private ResultSetMetaData   metaData;


	private SQLDataSet(ResultSet resultSet) {
		this.datas = new HashMap<>();

		try {
			if (resultSet != null)
				this.metaData = resultSet.getMetaData();
		} catch (SQLException ignored) {}

		this._initWith(resultSet);
	}


	public  String  getString (String key) {
		Object o = this.getObject(key);

		if (o instanceof String) return (String) o;
		else                     return null;
	}
	public  Integer getInteger(String key) {
		Object o = this.getObject(key);

		if (o instanceof Integer) return (Integer) o;
		else                      return null;
	}
	public  Float   getFloat  (String key) {
		Object o = this.getObject(key);

		try {
			return Float.parseFloat(o.toString());
		} catch (Exception ignored) { }

		return null;
	}
	public  Object  getObject (String key) {
		return this.datas.get(key);
	}
	public  Date    getDate   (String key) {
		Object o = this.getObject(key);

		if (o instanceof Date) return (Date) o;
		else                     return null;
	}


	public int               getNbColumns() { return this.datas.size(); }
	public ResultSetMetaData getMetaData () { return this.metaData;     }
	public Object            getValueAt(int index) { return this.datas.values().toArray()[index]; }


	private void   _initWith(ResultSet set) {
		if (set == null) return;

		try {

			int columns = set.getMetaData().getColumnCount();
			if (!set.next()) return;

			for (int i = 1; i <= columns; i++)
				this.datas.put(set.getMetaData().getColumnLabel(i), set.getObject(i));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public String toString() {
		return "{SQLDataSet (datas=" + Arrays.deepToString(this.datas.keySet().toArray()) + " values=" + Arrays.deepToString(this.datas.values().toArray()) + ")}";
	}


	public static List<SQLDataSet> fromResultSet(ResultSet resultSet) {
		List<SQLDataSet> dataSets = new ArrayList<>();
		SQLDataSet       dataSet  ;

		do {
			dataSet = new SQLDataSet(resultSet);

			if (dataSet.datas.size() == 0) dataSet = null;
			else                           dataSets.add(dataSet);
		}
		while (dataSet != null);

		return dataSets;
	}
}
