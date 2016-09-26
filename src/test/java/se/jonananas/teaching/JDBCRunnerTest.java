package se.jonananas.teaching;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.assertj.core.api.Condition;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.jonananas.teaching.JDBCRunner.SQLFunction;

@RunWith(CdiRunner.class)
public class JDBCRunnerTest {

	@Mock
	DataSource ds;
	@Mock
	Connection connection;
	@Mock
	PreparedStatement statement;

	Logger log = LoggerFactory.getLogger(JDBCRunner.class);

	@Before
	public void setup() throws SQLException {
		when(ds.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
	}

	@Test
	public void shouldCloseStatementAndConnectionOnException() throws Exception {
		doThrow(new SQLException("connection")).when(connection).close();
		doThrow(new SQLException("statement")).when(statement).close();

		try {
			JDBCRunner.runQuery("", ds, statementParam -> {
				throw new RuntimeException("lambda");
			});
			fail("Should not be reached!");
		} catch (Exception ex) {
			assertThat(ex.getMessage()).isEqualTo("lambda");
			assertThat(ex.getSuppressed().length).isEqualTo(2);
			assertThat(getCauses(ex.getSuppressed())).contains("connection", "statement");
		}

		verify(connection, Mockito.times(1)).close();
		verify(statement, Mockito.times(1)).close();
	}

	@Test
	public void usage() {
		JDBCRunner.runQuery("SELECT * FROM ...", ds, st -> st.executeQuery());
	}

	@Test
	public void shouldPropagateException() {

		assertThatThrownBy(() -> JDBCRunner.runQuery("", ds, throwRuntimeException("lambda"))).hasMessage("lambda");
	}

	@Test
	public void shouldSuppressStatementCloseException() throws Exception {
		doThrow(new SQLException("sqlexception")).when(statement).close();

		assertThatThrownBy(() -> JDBCRunner.runQuery("", ds, throwRuntimeException("lambda")))
				.has(condition(ex -> containsSuppressedMessage(ex, "sqlexception")));
	}

	@Test
	public void shouldSuppressConnectionCloseException() throws Exception {
		doThrow(new SQLException("sqlexception")).when(connection).close();

		assertThatThrownBy(() -> JDBCRunner.runQuery("", ds, throwRuntimeException("lambda")))
				.has(condition(ex -> containsSuppressedMessage(ex, "sqlexception")));
	}

	private SQLFunction<Object> throwRuntimeException(String message) {
		return (PreparedStatement st) -> {
			throw new RuntimeException(message);
		};
	}

	@Test
	public void shouldCloseConnectionAndStatementOnException() throws Exception {
		assertThatThrownBy(() -> JDBCRunner.runQuery("", ds, throwRuntimeException("lambda")));

		verify(connection, Mockito.times(1)).close();
		verify(statement, Mockito.times(1)).close();
	}

	@Test
	public void shouldCloseConnectionAndStatementOnCloseException() throws Exception {
		doThrow(new SQLException("connection")).when(connection).close();
		doThrow(new SQLException("statement")).when(statement).close();

		assertThatThrownBy(() -> JDBCRunner.runQuery("", ds, st -> st.executeQuery()));

		verify(connection, Mockito.times(1)).close();
		verify(statement, Mockito.times(1)).close();
	}

	private boolean containsSuppressedMessage(Throwable ex, String suppressedMessage) {
		return getCauses(ex.getSuppressed()).contains(suppressedMessage);
	}

	private Condition<Throwable> condition(Predicate<Throwable> cond) {
		return new Condition<Throwable>() {

			@Override
			public boolean matches(Throwable ex) {
				return cond.test(ex);
			}
		};
	}

	private List<String> getCauses(Throwable[] suppressed) {
		return arrayToStream(suppressed).map(ex -> ex.getMessage()).collect(toList());
	}

	private Stream<Throwable> arrayToStream(Throwable[] suppressed) {
		return Arrays.asList(suppressed).stream();
	}

}
