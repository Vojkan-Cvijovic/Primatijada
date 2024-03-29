package application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import application.exception.DataBaseBusyException;
import application.exception.PrimaryKeyTakenException;
import application.exception.RecordNotExistsException;
import application.model.Primatijada;
import application.utils.ConnectionManager;

/*
 * Performs operations on database, update, delete, insert ...
 */

public class PrimatijadaRepositoryImplementation implements
		PrimatijadaRepository {

	private ConnectionManager connectionManager;

	public PrimatijadaRepositoryImplementation() {

		connectionManager = new ConnectionManager();
		Connection connection = connectionManager.connect();
		try {
			PreparedStatement preparedTimeout = connection
					.prepareStatement(TIMEOUT);
			Statement statement = connection.createStatement();

			preparedTimeout.execute();
			statement.execute("select * from " + TABLE_NAME);

		} catch (SQLException e) {
			if (e.getErrorCode() == -204)
				try {
					createTable(connection);
				} catch (SQLException e1) {
					System.out.println("Create table fail");
					e1.printStackTrace();
				}
			else
				e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
	}

	private void createTable(Connection connection) throws SQLException {

		System.out.println("Creating table " + TABLE_NAME);
		try {
			PreparedStatement preparedTimeout = connection
					.prepareStatement(TIMEOUT);
			Statement statement = connection.createStatement();

			preparedTimeout.execute();
			statement.executeUpdate(CREATE_TABLE_SQL);

			System.out.println("Table " + TABLE_NAME + " is created");
		} catch (SQLException e) {
			System.out.println("Error while creating table Error code "
					+ e.getErrorCode());
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				createTable(connection);

			}
		}

	}

	public void insert(Primatijada model) throws PrimaryKeyTakenException,
			DataBaseBusyException {
		System.out.println("Insert record to table " + TABLE_NAME
				+ " with primary key " + model.getIndeks());
		Connection connection = connectionManager.connect();

		try {
			insertAction(model, connection, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
	}

	private void insertAction(Primatijada model, Connection connection,
			int times) throws SQLException, PrimaryKeyTakenException,
			DataBaseBusyException {

		if (times >= TRY_COUNT_LIMIT) {
			System.out
					.println("reached maximum count of atempts to write in db");
			throw new DataBaseBusyException();
		}

		System.out.println("Preparing statement for insert operation");

		try {
			PreparedStatement preparedTimeout = connection
					.prepareStatement(TIMEOUT);
			PreparedStatement preparedStatement = connection
					.prepareStatement(INSERT_SQL);

			preparedStatement.setInt(1, model.getIndeks());
			preparedStatement.setInt(2, model.getGodina());
			preparedStatement.setString(3, String.valueOf(model.getTip()));
			preparedStatement.setString(4, model.getSport());
			preparedStatement.setString(5, model.getRad());
			preparedStatement.setString(6, String.valueOf(model.getAranzman()));

			preparedTimeout.execute();
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				insertAction(model, connection, times + 1);

			} else if (e.getErrorCode() == -803) {
				throw new PrimaryKeyTakenException();
			}

		}

	}

	public void delete(int indeks) throws RecordNotExistsException,
			DataBaseBusyException {
		System.out.println("Deleting record for " + TABLE_NAME
				+ " for primary key " + indeks);
		Connection connection = connectionManager.connect();
		try {
			deleteAction(indeks, connection, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
	}

	private void deleteAction(int indeks, Connection connection, int times)
			throws SQLException, RecordNotExistsException,
			DataBaseBusyException {

		if (times >= TRY_COUNT_LIMIT) {
			System.out
					.println("reached maximum count of atempts to write in db");
			throw new DataBaseBusyException();
		}

		System.out.println("Preparing statement for delete operation");

		try {
			PreparedStatement preparedTimeout = connection
					.prepareStatement(TIMEOUT);
			PreparedStatement preparedStatement = connection
					.prepareStatement(DELETE_SQL);

			preparedStatement.setInt(1, indeks);

			preparedTimeout.execute();
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				deleteAction(indeks, connection, times + 1);
			} else if (e.getErrorCode() == 100)
				throw new RecordNotExistsException();

		}

	}

	public void update(Primatijada model) throws RecordNotExistsException,
			DataBaseBusyException {

		System.out.println("Update record for " + TABLE_NAME
				+ " with primary key " + model.getIndeks());
		Connection connection = connectionManager.connect();
		try {
			updateAction(model, connection, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}

	}

	private void updateAction(Primatijada model, Connection connection,
			int times) throws SQLException, RecordNotExistsException,
			DataBaseBusyException {

		if (times >= TRY_COUNT_LIMIT) {
			System.out
					.println("reached maximum count of atempts to write in db");
			throw new DataBaseBusyException();
		}

		System.out.println("Preparing statement for update operation");
		try {
			PreparedStatement preparedTimeout = connection
					.prepareStatement(TIMEOUT);
			PreparedStatement preparedStatement = connection
					.prepareStatement(UPDATE_SQL);

			preparedStatement.setString(1, String.valueOf(model.getTip()));
			preparedStatement.setString(2, model.getSport());
			preparedStatement.setString(3, model.getRad());
			preparedStatement.setString(4, String.valueOf(model.getAranzman()));
			preparedStatement.setInt(5, model.getGodina());
			preparedStatement.setInt(6, model.getIndeks());

			preparedTimeout.execute();
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				updateAction(model, connection, times + 1);
			} else if (e.getErrorCode() == 100)
				throw new RecordNotExistsException();
		}

	}

	public Primatijada retrieve(int indeks) throws RecordNotExistsException,
			DataBaseBusyException {

		System.out.println("Retrieves data from table " + TABLE_NAME);

		Connection connection = connectionManager.connect();
		Primatijada primatijada = null;

		try {
			primatijada = retriveAction(indeks, connection, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
		return primatijada;
	}

	private Primatijada retriveAction(int indeks, Connection connection,
			int times) throws SQLException, RecordNotExistsException,
			DataBaseBusyException {

		if (times >= TRY_COUNT_LIMIT) {
			System.out
					.println("reached maximum count of atempts to write in db");

			throw new DataBaseBusyException();
		}

		System.out.println("Preparing statement for select operation");

		Primatijada primatijada = new Primatijada();
		try {
			PreparedStatement preparedTimeout = connection
					.prepareStatement(TIMEOUT);
			PreparedStatement preparedStatement = connection
					.prepareStatement(RETRIVE_SQL);

			preparedStatement.setInt(1, indeks);

			int godina = Calendar.getInstance().get(Calendar.YEAR);
			preparedStatement.setInt(2, godina);

			preparedTimeout.execute();
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				primatijada.setIndeks(resultSet.getInt(1));
				primatijada.setGodina(resultSet.getInt(2));
				primatijada.setTip(resultSet.getString(3).charAt(0));
				primatijada.setSport(resultSet.getString(4));
				primatijada.setRad(resultSet.getString(5));
				primatijada.setAranzman(resultSet.getString(6).charAt(0));
			} else {
				System.out.println("indeks" + indeks + " do not exists");
				throw new RecordNotExistsException();

			}
		} catch (SQLException e) {

			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				primatijada = retriveAction(indeks, connection, times + 1);
			} else if (e.getErrorCode() == 100)
				throw new RecordNotExistsException();
		}
		return primatijada;
	}

	@Override
	public int getCountOfRecords(int indeks) throws DataBaseBusyException,
			RecordNotExistsException {

		System.out.println("Getting number of times for indeks " + indeks);
		Connection connection = connectionManager.connect();
		int count = 0;
		try {
			count = getCountOfRecordsAction(indeks, connection, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}

		return count;
	}

	private int getCountOfRecordsAction(int indeks, Connection connection,
			int times) throws SQLException, DataBaseBusyException,
			RecordNotExistsException {

		if (times >= TRY_COUNT_LIMIT) {
			System.out
					.println("Reached maximum count of atempts to write in db");
			throw new DataBaseBusyException();
		}

		PreparedStatement preparedStatement;
		int count = 0;

		try {
			PreparedStatement preparedTimeout = connection
					.prepareStatement(TIMEOUT);
			preparedStatement = connection.prepareStatement(COUNT_SQL);

			preparedStatement.setInt(1, indeks);

			preparedTimeout.execute();
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("executed query");
			int godina = Calendar.getInstance().get(Calendar.YEAR);
			while (resultSet.next()) {
				int g = resultSet.getInt(1);
				if (g != godina)
					break;
				godina--;
				count++;

			}
			if (count == 0) {
				System.out.println("fist time for indeks " + indeks);
				throw new RecordNotExistsException();
			}

		} catch (SQLException e) {
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");

				connection.rollback();

				count = getCountOfRecordsAction(indeks, connection, times + 1);

			}
		}
		return count;
	}

	@Override
	public List<Primatijada> retriveAllForCurrentYear()
			throws DataBaseBusyException {

		System.out.println("Preparing for retriving all ...");
		Connection connection = connectionManager.connect();
		List<Primatijada> list = null;
		try {
			list = retriceAllForCurrentYearAction(connection, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}

		return list;
	}

	private List<Primatijada> retriceAllForCurrentYearAction(
			Connection connection, int times) throws SQLException,
			DataBaseBusyException {

		if (times >= TRY_COUNT_LIMIT) {
			System.out
					.println("Reached maximum count of atempts to write in db");
			throw new DataBaseBusyException();
		}

		PreparedStatement preparedStatement;
		List<Primatijada> list = null;

		try {
			PreparedStatement preparedTimeout = connection
					.prepareStatement(TIMEOUT);
			preparedStatement = connection.prepareStatement(RETRIVE_ALL_SQL);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			preparedStatement.setInt(1, year);

			preparedTimeout.execute();
			ResultSet resultSet = preparedStatement.executeQuery();
			list = new LinkedList<>();
			while (resultSet.next()) {
				// Loading model
				System.out.println("Adding in list ");
				Primatijada primatijada = new Primatijada();

				primatijada.setIndeks(resultSet.getInt(1));
				primatijada.setGodina(resultSet.getInt(2));
				primatijada.setTip(resultSet.getString(3).charAt(0));
				primatijada.setSport(resultSet.getString(4));
				primatijada.setRad(resultSet.getString(5));
				primatijada.setAranzman(resultSet.getString(6).charAt(0));

				System.out.println("adding indeks " + primatijada.getIndeks());
				list.add(primatijada);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");

				connection.rollback();
				list = retriceAllForCurrentYearAction(connection, times + 1);
			}

		}
		return list;
	}

}
