package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.exception.PrimaryKeyTakenException;
import application.exception.RecordNotExistsException;
import application.utils.ConnectionManager;

/*Low level tier
 * it communicate directly with database,
 * performs all queries,
 * performs all operations with table TABLE_NAME,
 * insert, update, delete, create ....
 */
public class Primatijada {

	private int indeks; // not null
	private short godina;
	private char tip; // s- sportisti, n-naucnici, x-navijaci
	private String sport;
	private String rad;
	private char aranzman; // c-celo mesto, p-pola mesta

	private ConnectionManager connectionManager;
	private static final String TABLE_NAME = "primatijada";

	/*************************************** SQL *****************************************/
	private static final String CREATE_TABLE_SQL = "create table "
			+ TABLE_NAME
			+ "( indeks integer not null,"
			+ " godina smallint not null, tip char, sport varchar(20), rad varchar(100), aranzman char,"
			+ " primary key(indeks, godina))";
	private static final String INSERT_SQL = "insert into " + TABLE_NAME
			+ " values(?,?,?,?,?,?)";
	private static final String DELETE_SQL = "delete from " + TABLE_NAME
			+ "where indeks = ?";
	private static final String UPDATE_SQL = "update " + TABLE_NAME
			+ " SET tip = ?, sport = ?, rad = ? where indeks = ?";
	private static final String EXISTS_SQL = "select * from " + TABLE_NAME
			+ " where indeks = ?";
	private static final String RETRIVE_CATEGORY_SQL = "select tip from "
			+ TABLE_NAME + " t1 where indeks = ? "
			+ "and not exists ( select * from " + TABLE_NAME
			+ " t2 where t2.godina > t1.godina ) ";

	/*****************************************************************************************/

	public Primatijada() {

		System.out.println("Creating Primatijada");

		connectionManager = new ConnectionManager();
		Connection connection = connectionManager.connect();
		try {
			Statement statement = connection.createStatement();
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
		initialize();
	}

	private void initialize() {
		System.out.println("Initializing Model");
		indeks = 0;
		godina = 0;
		tip = 's';
		sport = null;
		rad = null;
		aranzman = 'c';
	}

	private void createTable(Connection connection) throws SQLException {

		System.out.println("Creating table " + TABLE_NAME);
		try {
			Statement statement = connection.createStatement();
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

	public void insert() throws PrimaryKeyTakenException {
		System.out.println("Insert record to table " + TABLE_NAME
				+ " with primary key " + indeks);
		Connection connection = connectionManager.connect();

		try {
			insertAction(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
	}

	private void insertAction(Connection connection) throws SQLException,
			PrimaryKeyTakenException {

		System.out.println("Preparing statement for insert operation");

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(INSERT_SQL);

			preparedStatement.setInt(1, indeks);
			preparedStatement.setShort(2, godina);
			preparedStatement.setString(3, String.valueOf(tip));
			preparedStatement.setString(4, sport);
			preparedStatement.setString(5, rad);
			preparedStatement.setString(6, String.valueOf(aranzman));

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				insertAction(connection);

			} else if (e.getErrorCode() == -803) {
				throw new PrimaryKeyTakenException();
			}

		}

	}

	public void delete(int indeks) throws RecordNotExistsException {
		System.out.println("Deleting record for " + TABLE_NAME
				+ " for primary key " + indeks);
		Connection connection = connectionManager.connect();
		try {
			deleteAction(indeks, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
	}

	private void deleteAction(int indeks, Connection connection)
			throws SQLException, RecordNotExistsException {

		System.out.println("Preparing statement for delete operation");

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(DELETE_SQL);

			preparedStatement.setInt(1, indeks);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				deleteAction(indeks, connection);
			} else if (e.getErrorCode() == 100)
				throw new RecordNotExistsException();

		}

	}

	public void update() throws RecordNotExistsException {

		System.out.println("Update record for " + TABLE_NAME
				+ " with primary key " + indeks);
		Connection connection = connectionManager.connect();
		try {
			updateAction(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}

	}

	private void updateAction(Connection connection) throws SQLException,
			RecordNotExistsException {

		System.out.println("Preparing statement for update operation");
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(UPDATE_SQL);

			preparedStatement.setString(1, String.valueOf(tip));
			preparedStatement.setString(2, sport);
			preparedStatement.setString(3, rad);
			preparedStatement.setInt(4, indeks);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				updateAction(connection);
			} else if (e.getErrorCode() == 100)
				throw new RecordNotExistsException();
		}

	}

	public String retrieveCategory(int indeks) throws RecordNotExistsException {

		System.out.println("Retrieves data from table " + TABLE_NAME);

		Connection connection = connectionManager.connect();
		String category = null;
		try {
			category = retriveCategoryAction(indeks, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
		return category;
	}

	private String retriveCategoryAction(int indeks, Connection connection)
			throws SQLException, RecordNotExistsException {

		System.out.println("Preparing statement for select operation");

		String category = null;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(RETRIVE_CATEGORY_SQL);

			preparedStatement.setInt(1, indeks);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				category = resultSet.getString(1);

		} catch (SQLException e) {
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				category = retriveCategoryAction(indeks, connection);
			} else if (e.getErrorCode() == 100)
				throw new RecordNotExistsException();
		}
		return category;
	}

	public int getIndeks() {
		return indeks;
	}

	public void setIndeks(int indeks) {
		this.indeks = indeks;
	}

	public short getGodina() {
		return godina;
	}

	public void setGodina(short godina) {
		this.godina = godina;
	}

	public char getTip() {
		return tip;
	}

	public void setTip(char tip) {
		this.tip = tip;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getRad() {
		return rad;
	}

	public void setRad(String rad) {
		this.rad = rad;
	}

	public char getAranzman() {
		return aranzman;
	}

	public void setAranzman(char aranzman) {
		this.aranzman = aranzman;
	}

}
