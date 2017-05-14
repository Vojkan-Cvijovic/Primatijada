package application.service;

import application.exception.PrimaryKeyTakenException;
import application.exception.RecordNotExistsException;
import application.model.Primatijada;

public class PrimatijadaService {

	private static final String SPORT = "Sportista";
	private static final String SCIENCE = "Naucnik";
	/* ZA IZMENU */
	private static final short godina = 1;

	public void signUp(String indeksString, String category,
			String arrangement, String options)
			throws PrimaryKeyTakenException, NumberFormatException {

		int indeks = Integer.parseInt(indeksString);

		Primatijada primatijada = new Primatijada();

		primatijada.setIndeks(indeks);
		if (category.equalsIgnoreCase(SPORT)) {
			primatijada.setSport(options);
		} else if (category.equalsIgnoreCase(SCIENCE)) {
			primatijada.setRad(options);
		}

		primatijada.setGodina(godina); // ???

		primatijada.insert();
	}

	public String retriveCategory(String indeksString)
			throws NumberFormatException, RecordNotExistsException {
		int indeks = Integer.parseInt(indeksString);

		Primatijada primatijada = new Primatijada();

		String category = primatijada.retriveCategory(indeks);

		System.out.println(category);

		switch (category.charAt(0)) {
		case 'x':
			return "Navijac";
		case 's':
			return "Sportista";
		case 'n':
			return "Naucnik";
		}
		System.out.println("ERROR unknown category");
		return null;

	}

	public void updateInfo(String indeksString, String category)
			throws NumberFormatException, RecordNotExistsException {

		int indeks = Integer.parseInt(indeksString);

		Primatijada primatijada = new Primatijada();

		char tip;
		if (category.equalsIgnoreCase("Navijac"))
			tip = 'n';
		else if (category.equalsIgnoreCase("Sportista"))
			tip = 's';
		else
			tip = 'x';

		primatijada.setIndeks(indeks);
		primatijada.setTip(tip);

		primatijada.update();

	}

}
