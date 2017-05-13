package application.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.exception.PrimaryKeyTakenException;
import application.exception.RecordNotExistsException;
import application.utils.ConnectionManager;

public class Primatijada {

	private int indeks; // not null
	private short godina;
	private char tip; // s- sportisti, n-naucnici, x-navijaci
	private String sport;
	private String rad;
	private char aranzman; // c-celo mesto, p-pola mesta

	private ConnectionManager connectionManager;
	private static final String TABLE_NAME = "primatijada";
	/********* SQL **********/

	private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME
			+ " " + "( " + "indeks integer not null, " + "godina smallint, "
			+ "tip char, " + "sport varchar(20), " + "rad varchar(100), "
			+ "aranzman char, primary key(indeks, godina)  " + ")";
	private static final String INSERT_SQL = "insert into " + TABLE_NAME
			+ " values(?,?,?,?,?,?)";
	private static final String DELETE_SQL = "delete from " + TABLE_NAME
			+ "where indeks = ?";
	private static final String UPDATE_SQL = "update " + TABLE_NAME
			+ " SET tip = ?, sport = ?, rad = ? where indeks = ?";
	private static final String EXISTS_SQL = "select * from " + TABLE_NAME
			+ " where indeks = ?";

	public Primatijada() {

		connectionManager = new ConnectionManager();
		Connection connection = connectionManager.connect();
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet tables = databaseMetaData.getTables(null, null,
					TABLE_NAME, null);
			if (!tables.next())
				createTable(connection);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
		initialize();
	}

	private void initialize() {
		indeks = 0;
		godina = 0;
		tip = 's';
		sport = null;
		rad = null;
		aranzman = 'c';
	}

	private void createTable(Connection connection) throws SQLException {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_TABLE_SQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {

				connection.rollback();
				createTable(connection);

			}
		}

	}

	public void insert() throws PrimaryKeyTakenException {
		Connection connection = connectionManager.connect();

		try {
			insertAction(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
	}

	private void insertAction(Connection connection) throws SQLException, PrimaryKeyTakenException {

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

				connection.rollback();

				insertAction(connection);
			}else if(e.getErrorCode() == -803){
				throw new PrimaryKeyTakenException();
			}

		}

	}

	public void delete() throws RecordNotExistsException {
		Connection connection = connectionManager.connect();
		try {
			deleteAction(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
	}

	private void deleteAction(Connection connection) throws SQLException, RecordNotExistsException {

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(DELETE_SQL);

			preparedStatement.setInt(1, indeks);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				connection.rollback();
				deleteAction(connection);
			}else if(e.getErrorCode() == 100)
				throw new RecordNotExistsException();

		}

	}

	public void update() throws RecordNotExistsException {
		Connection connection = connectionManager.connect();
		try {
			updateAction(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}

	}

	private void updateAction(Connection connection) throws SQLException, RecordNotExistsException {

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
				connection.rollback();
				updateAction(connection);
			}else if(e.getErrorCode() == 100)
				throw new RecordNotExistsException();
		}

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
