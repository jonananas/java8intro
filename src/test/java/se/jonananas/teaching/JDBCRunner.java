package se.jonananas.teaching;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JDBCRunner {
	
	@FunctionalInterface
	interface SQLFunction<R> {
		R apply(PreparedStatement t) throws SQLException;
	}

	public static <T> T runQuery(String sql, DataSource ds, SQLFunction<T> f) {
		try (Connection connection = ds.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			return f.apply(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}