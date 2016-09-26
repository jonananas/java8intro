package se.jonananas.teaching;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class JDBCPersonRepo {

	DataSource ds;

	// Hur får vi bort duplicerad kod?

	public List<String> readPersonsWithName(String name) {
		String sql = "SELECT person.namn FROM person WHERE person.namn = ?";
		try (Connection connection = ds.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(0, name);
			return collectAsList(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> readPersonsWithId(Integer id) {
		String sql = "SELECT person.namn FROM person WHERE person.id = ?";
		try (Connection connection = ds.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(0, id);
			return collectAsList(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> readAllPersons() {
		String sql = "SELECT person.namn FROM person";
		try (Connection connection = ds.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			return collectAsList(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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