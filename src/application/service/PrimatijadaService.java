package application.service;

<<<<<<< HEAD
import application.exception.DataBaseBusyException;
import application.exception.EmptyInputException;
import application.exception.IndeksFormatException;
import application.exception.InvalidInputException;
=======
import java.util.Enumeration;

import javax.swing.AbstractButton;

import application.exception.DataBaseBusyException;
import application.exception.EmptyInputException;
import application.exception.IndeksFormatException;
>>>>>>> 2c0224dd3814943461d71ac46b0c70cad05aeeaa
import application.exception.InvalidInputFormatException;
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

	private ValidationService validationService;

	private static final String SPORT = "Sportista";
	private static final String SCIENCE = "Naucnik";
	private static final int BASE_PRICE = 110;


	/**
	 * @param validationService
	 ******************************/

	public PrimatijadaService(ValidationService validationService) {
		repository = new PrimatijadaRepositoryImplementation();
		this.validationService = validationService;
	}

	public void signUp(String indeksString, String category,
			String arrangement, String options)
			throws PrimaryKeyTakenException, NumberFormatException,
			EmptyInputException, RecordNotExistsException,
			DataBaseBusyException, IndeksFormatException,
			InvalidInputFormatException {

		int indeks = Integer.parseInt(indeksString);
		Primatijada primatijada = new Primatijada();

		primatijada.setIndeks(indeks);
		if (arrangement.equalsIgnoreCase("Ceo")) {
			primatijada.setAranzman('c');
		} else {
			primatijada.setAranzman('p');
		}

		if (category.equalsIgnoreCase(SPORT)) {
			primatijada.setSport(options);
			primatijada.setTip('s');
		} else if (category.equalsIgnoreCase(SCIENCE)) {
			primatijada.setTip('n');
			primatijada.setRad(options);
		} else {
			primatijada.setTip('x');
		}
		validationService.check(primatijada);
		repository.insert(primatijada);
	}


	public void updateRecord(String indeksString, String category,
			String options) throws NumberFormatException,
			RecordNotExistsException, IndeksFormatException,
			EmptyInputException, InvalidInputException,
			InvalidInputFormatException, DataBaseBusyException {

		validationService.checkIndeksFormat(indeksString);
		validationService.checkOptionsInput(category, options);

		int indeks = Integer.parseInt(indeksString);

		Primatijada primatijada = new Primatijada();

		primatijada.setIndeks(indeks);

		if (category.equalsIgnoreCase("Naucnik")) {
			primatijada.setTip('n');
			primatijada.setRad(options);
		} else if (category.equalsIgnoreCase("Sportista")) {
			primatijada.setTip('s');
			primatijada.setSport(options);
		} else
			primatijada.setTip('x');

		repository.update(primatijada);

	}


	public void deleteRecord(String indeksString)
			throws RecordNotExistsException, IndeksFormatException,
			DataBaseBusyException, EmptyInputException {

		validationService.checkIndeksFormat(indeksString);


		int indeks = Integer.parseInt(indeksString);
		repository.delete(indeks);
	}

	public Primatijada retrievePrimatijada(String indeksText)
			throws IndeksFormatException, NumberFormatException,
			RecordNotExistsException, DataBaseBusyException,
			EmptyInputException {

		validationService.checkIndeksFormat(indeksText);

		Primatijada p = repository.retrieve(Integer.parseInt(indeksText));

		return p;
	}

	public float calculatePrice(String indeksText, String option,
			String arrangement) throws IndeksFormatException,
			DataBaseBusyException, EmptyInputException {
		
		validationService.checkIndeksFormat(indeksText);
		
		int indeks = Integer.parseInt(indeksText);
		int count = 0;
		try {
			count = repository.getCountOfRecords(indeks);
		} catch (RecordNotExistsException e) {
		}

		float price = BASE_PRICE;
		int discount = 0;

		if (arrangement.equalsIgnoreCase("pola"))
			price = price / 2;

		if (count == 2)
			discount += 5;
		else if (count > 2)
			discount += 10;

		if (option.equalsIgnoreCase(SCIENCE) || option.equalsIgnoreCase(SPORT))
			discount += 70;

		price = price * (100 - discount) / (float) 100;

		return price;
	}

	public float calculatePrice(String indeksText, String option)
			throws IndeksFormatException, EmptyInputException,
			RecordNotExistsException, DataBaseBusyException {
		validationService.checkIndeksFormat(indeksText);

		int indeks = Integer.parseInt(indeksText);
		Primatijada primatijada = repository.retrieve(indeks);

		int count = repository.getCountOfRecords(indeks);
		float price = BASE_PRICE;
		int discount = 0;

		if (primatijada.getAranzman() == 'p')
			price = price / 2;

		System.out.println("Count " + count);
		if (count == 2)
			discount += 5;
		else if (count > 2)
			discount += 10;

		if (option.equalsIgnoreCase(SCIENCE) || option.equalsIgnoreCase(SPORT))
			discount += 70;

		price = price * (100 - discount) / (float) 100;

		return price;
	}

}
