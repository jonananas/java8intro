package se.jonananas.teaching.streamingslides;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class JDBCPersonRepoUsingLambda {

	DataSource ds;

	public List<String> readPersonsWithName(String name) {
		String sql = "SELECT person.namn FROM person WHERE person.namn = ?";

		return JDBCRunner.runQuery(sql, ds, statement -> {
			statement.setString(0, name);
			return collectAsList(statement);
		});
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
		ResultSet rs = statement.executeQuery();
		List<String> result = new ArrayList<String>();
		while (rs.next()) {
			result.add(rs.getString(1).trim());
		}
		return result;
	}
}