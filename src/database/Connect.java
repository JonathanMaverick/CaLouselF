package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	private final String username = "root";
	private final String password = "";
	private final String database = "javafx-sesi9";
	private final String host = "localhost:3306";
	private final String connection = String.format("jdbc:mysql://%s/%s", host, database);

	private Statement st;

	private static Connect db;

	public ResultSet rs;
	private Connection con;

	public static Connect getInstance() {
		if (db == null) {
			return new Connect();
		}
		return db;
	}

	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(connection, username, password);
			st = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void execute(String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
