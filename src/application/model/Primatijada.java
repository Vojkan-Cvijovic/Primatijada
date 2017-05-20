package application.model;

import java.util.Calendar;
import java.util.Date;

/*
 * Represents table primatijada
 * 	performs all validations 
 */
public class Primatijada {
	
	private int indeks; // not null
	private int godina;
	private char tip; // s- sportisti, n-naucnici, x-navijaci
	private String sport;
	private String rad;
	private char aranzman; // c-celo mesto, p-pola mesta

	public Primatijada() {

		System.out.println("Creating Primatijada");
		initialize();
	}

	private void initialize() {
		System.out.println("Initializing Model");
		indeks = 0;
		godina = Calendar.getInstance().get(Calendar.YEAR);
		System.out.println(godina);
		tip = 's';
		sport = null;
		rad = null;
		aranzman = 'c';
	}
	
	public int getIndeks() {
		return indeks;
	}

	public void setIndeks(int indeks) {
		this.indeks = indeks;
	}

	public int getGodina() {
		return godina;
	}

	public void setGodina(int godina) {
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
