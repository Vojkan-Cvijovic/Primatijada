package application.service;

import application.exception.PrimaryKeyTakenException;
import application.exception.RecordNotExistsException;
import application.model.Primatijada;
import application.repository.PrimatijadaRepository;
import application.repository.PrimatijadaRepositoryImplementation;

/*
 * Middle tier, it takes all data from window package,
 *  creates model,
 *  fills in data in model
 * 	retrieves all informations from models,
 * 	performs necessary validations on input data,
 * 	in case of error throws specific exceptions,
 * 	does not handle any validation exception
 */

public class PrimatijadaService {

	// uses repository for communication with database
	private PrimatijadaRepository repository;

	private static final String SPORT = "Sportista";
	private static final String SCIENCE = "Naucnik";

	/********************************/
	/*
	 * FOR FUTURE CHANGE/DELETE Temporary solution
	 */
	private static final short godina = 1;

	/********************************/

	public PrimatijadaService() {
		repository = new PrimatijadaRepositoryImplementation();

	}

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

		/*
		 * FOR FUTURE CHANGE what is godina!?
		 */
		primatijada.setGodina(godina);

		repository.insert(primatijada);
	}

	public String retrieveCategory(String indeksString)
			throws NumberFormatException, RecordNotExistsException {
		int indeks = Integer.parseInt(indeksString);

		String category = repository.retrieveCategory(indeks);

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

	public void updateRecord(String indeksString, String category,
			String options) throws NumberFormatException,
			RecordNotExistsException {

		int indeks = Integer.parseInt(indeksString);

		Primatijada primatijada = new Primatijada();

		if (category.equalsIgnoreCase("Navijac")) {
			primatijada.setTip('n');
			primatijada.setRad(options);
		} else if (category.equalsIgnoreCase("Sportista")) {
			primatijada.setTip('s');
			primatijada.setSport(options);
		} else
			primatijada.setTip('x');

		primatijada.setIndeks(indeks);

		repository.update(primatijada);

	}

	/* FOR FUTURE DEVELOPMENT */
	// deletes record from db
	public void deleteRecord(String indeksString) {

	}

}
