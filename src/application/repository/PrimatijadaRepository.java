package application.repository;

import java.util.List;

import application.exception.DataBaseBusyException;
import application.exception.PrimaryKeyTakenException;
import application.exception.RecordNotExistsException;
import application.model.Primatijada;

/*
 * Interface for performing operations on primatijada table
 */

public interface PrimatijadaRepository {

	public static final String TABLE_NAME = "primatijada";
	public static final int TRY_COUNT_LIMIT = 5;

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
	public static final String UPDATE_SQL = "update "
			+ TABLE_NAME
			+ " SET tip = ?, sport = ?, rad = ?, aranzman = ?, godina = ? where indeks = ?";
	public static final String EXISTS_SQL = "select * from " + TABLE_NAME
			+ " where indeks = ?";

	public static final String RETRIVE_SQL = "select * from " + TABLE_NAME
			+ " where indeks = ? and godina = ?";
	public static final String COUNT_SQL = "select godina from " + TABLE_NAME
			+ " where indeks = ? order by 1 desc";
	public static final String RETRIVE_ALL_SQL = "select * from " + TABLE_NAME
			+ " where godina = ? ";

	/***************************************************************************************/

	public void insert(Primatijada model) throws PrimaryKeyTakenException,
			DataBaseBusyException;

	public void delete(int indeks) throws RecordNotExistsException,
			DataBaseBusyException;

	public void update(Primatijada model) throws RecordNotExistsException,
			DataBaseBusyException;

	public Primatijada retrieve(int indeks) throws RecordNotExistsException,
			DataBaseBusyException;

	public int getCountOfRecords(int indeks) throws RecordNotExistsException,
			DataBaseBusyException;

	public List<Primatijada> retriveAllForCurrentYear()
			throws DataBaseBusyException;
}
