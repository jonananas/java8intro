package se.jonananas.teaching;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import se.jonananas.teaching.JDBCRunner.SQLFunction;

public class JDBCPersonRepoUsingLambda {

	DataSource ds;

	// Hur får vi bort duplicerad kod?

	public List<String> readPersonsWithName(String name) {
		String sql = "SELECT person.namn FROM person WHERE person.namn = ?";

		SQLFunction<List<String>> dbquery = statement -> {
			statement.setString(0, name);
			return collectAsList(statement);
		};

		return JDBCRunner.runQuery(sql, ds, dbquery);
	}

	public List<String> readPersonsWithId(Integer id) {
		String sql = "SELECT person.namn FROM person WHERE person.id = ?";
		return JDBCRunner.runQuery(sql, ds, statement -> {
			statement.setInt(0, id);
			return collectAsList(statement);
		});
	}

	public List<String> readAllPersons() {
		String sql = "SELECT person.namn FROM person";
		return JDBCRunner.runQuery(sql, ds, statement -> {
			return collectAsList(statement);
		});
	}

	private static List<String> collectAsList(PreparedStatement statement) throws SQLException {
		List<String> result = new ArrayList<String>();
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			result.add(rs.getString(1).trim());
		}
		return result;
	}
}