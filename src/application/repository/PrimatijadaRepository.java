package application.repository;

import application.exception.PrimaryKeyTakenException;
import application.exception.RecordNotExistsException;
import application.model.Primatijada;

/*
 * Interface for performing operations on primatijada table
 */

public interface PrimatijadaRepository {

	public static final String TABLE_NAME = "primatijada";

	/*************************************** SQL *****************************************/
	public static final String CREATE_TABLE_SQL = "create table "
			+ TABLE_NAME
			+ "( indeks integer not null,"
			+ " godina int not null, tip char, sport varchar(20), rad varchar(100), aranzman char,"
			+ " primary key(indeks, godina))";
	public static final String INSERT_SQL = "insert into " + TABLE_NAME
			+ " values(?,?,?,?,?,?)";
	public static final String DELETE_SQL = "delete from " + TABLE_NAME
			+ " where indeks = ?";
	public static final String UPDATE_SQL = "update " + TABLE_NAME
			+ " SET tip = ?, sport = ?, rad = ?, aranzman = ?, godina = ? where indeks = ?";
	public static final String EXISTS_SQL = "select * from " + TABLE_NAME
			+ " where indeks = ?";
	public static final String RETRIVE_CATEGORY_SQL = "select tip from "
			+ TABLE_NAME + " t1 where indeks = ? "
			+ "and not exists ( select * from " + TABLE_NAME
			+ " t2 where t2.godina > t1.godina ) ";

	/***************************************************************************************/

	public void insert(Primatijada model) throws PrimaryKeyTakenException;

	public void delete(int indeks) throws RecordNotExistsException;

	public void update(Primatijada model) throws RecordNotExistsException;

	public String retrieveCategory(int indeks) throws RecordNotExistsException;
}
