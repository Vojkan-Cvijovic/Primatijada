package application.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	public void insert(Primatijada model) throws PrimaryKeyTakenException {
		System.out.println("Insert record to table " + TABLE_NAME
				+ " with primary key " + model.getIndeks());
		Connection connection = connectionManager.connect();

		try {
			insertAction(model, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}
	}

	private void insertAction(Primatijada model, Connection connection)
			throws SQLException, PrimaryKeyTakenException {

		System.out.println("Preparing statement for insert operation");

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(INSERT_SQL);

			preparedStatement.setInt(1, model.getIndeks());
			preparedStatement.setInt(2, model.getGodina());
			preparedStatement.setString(3, String.valueOf(model.getTip()));
			preparedStatement.setString(4, model.getSport());
			preparedStatement.setString(5, model.getRad());
			preparedStatement.setString(6, String.valueOf(model.getAranzman()));

			preparedStatement.executeUpdate();
			
			
		} catch (SQLException e) {

			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				insertAction(model, connection);

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

	public void update(Primatijada model) throws RecordNotExistsException {

		System.out.println("Update record for " + TABLE_NAME
				+ " with primary key " + model.getIndeks());
		Connection connection = connectionManager.connect();
		try {
			updateAction(model, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.disconnect();
		}

	}

	private void updateAction(Primatijada model, Connection connection)
			throws SQLException, RecordNotExistsException {

		System.out.println("Preparing statement for update operation");
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(UPDATE_SQL);

			preparedStatement.setString(1, String.valueOf(model.getTip()));
			preparedStatement.setString(2, model.getSport());
			preparedStatement.setString(3, model.getRad());
			preparedStatement.setString(4, String.valueOf(model.getAranzman()));
			preparedStatement.setInt(5, model.getGodina());
			preparedStatement.setInt(6, model.getIndeks());

			preparedStatement.executeUpdate();
			
			
		} catch (SQLException e) {
			if (e.getErrorCode() == -911 || e.getErrorCode() == -913) {
				System.out.println("ERROR SQLCODE: " + e.getErrorCode()
						+ " Database busy, rollback and try again ...");
				connection.rollback();
				updateAction(model, connection);
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
	
	public float countingPrice(String tip1,String aranzman1,String godina1){
		
		float cena = 0;
		char tip = tip1.charAt(0);
		char aranzman = aranzman1.charAt(0);
		int godina = Integer.parseInt(godina1);
		
		if(tip == 's'){
			if(aranzman == 'c'){
				cena = 110 * 30 / 100;
				if(godina == 2){
					cena -= cena * 5 / 100; 
				} else if(godina > 2 ){
					cena -= cena * 10 / 100;
				}
			}
			else{
				cena = (110 * 30 / 100) / 2;
				if(godina == 2){
					cena -= cena * 5 / 100;
				} else if(godina > 2){
					cena -= cena * 10 / 100;
				}
			}
		} else if(tip == 'n'){
			if(aranzman == 'c'){
				cena = 110 * 30 / 100;
				if(godina == 2){
					cena -= cena * 5 / 100; 
				} else if(godina > 2 ){
					cena -= cena * 10 / 100;
				}
			}
			else{
				cena = (110 * 30 / 100) / 2;
				if(godina == 2){
					cena -= cena * 5 / 100;
				} else if(godina > 2){
					cena -= cena * 10 / 100;
				}
			}
		} else{
			if(aranzman == 'c'){
				cena = 110;
				if(godina == 2){
					cena -= cena * 5 / 100;
				}
				else{
					if(godina > 2){
						cena -= cena *10 / 100;
					}
				}
			}
			else{
				cena = 110 / 2;
				if(godina == 2){
					cena -= cena * 5 /100;
				} else if(godina > 2){
					cena -= cena * 10 /100;
				}
			}
		}
		
		return cena;
	}

}
