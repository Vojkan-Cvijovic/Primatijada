package application.service;

import application.exception.DataBaseBusyException;
import application.exception.EmptyInputException;
import application.exception.IndeksFormatException;
import application.exception.InvalidInputException;
import application.exception.InvalidInputFormatException;
import application.exception.RecordNotExistsException;
import application.model.Primatijada;
import application.repository.PrimatijadaRepository;
import application.repository.PrimatijadaRepositoryImplementation;

public class ValidationService {

	private PrimatijadaRepository repository;

	public ValidationService() {
		repository = new PrimatijadaRepositoryImplementation();
	}

<<<<<<< HEAD
	public boolean checkIfIndeksExists(String text) throws EmptyInputException,
=======
	public void checkIfIndeksExists(String text) throws EmptyInputException,
>>>>>>> 2c0224dd3814943461d71ac46b0c70cad05aeeaa
			NumberFormatException, RecordNotExistsException,
			DataBaseBusyException, IndeksFormatException {

		if (text.trim().isEmpty())
			throw new EmptyInputException();

		if (!text.matches("([0-9]){5}"))
			throw new IndeksFormatException();

		int indeks = Integer.parseInt(text);

		repository.retrieve(indeks);
<<<<<<< HEAD
		return true;
=======

>>>>>>> 2c0224dd3814943461d71ac46b0c70cad05aeeaa
	}

	public void checkOptionsInput(String category, String text)
			throws EmptyInputException, InvalidInputException,
			InvalidInputFormatException {

		if (text.trim().isEmpty())
			throw new EmptyInputException();
		if (category.equalsIgnoreCase("Sportista")) {
			if (text.length() > 20)
				throw new InvalidInputException();
		} else if (category.equalsIgnoreCase("Naucnik")) {
			if (text.length() > 60)
				throw new InvalidInputException();
		}
		if (!text.matches("([a-z]|[A-Z])+"))
			throw new InvalidInputFormatException();

	}

	public void check(Primatijada primatijada) throws NumberFormatException,
			EmptyInputException, RecordNotExistsException,
<<<<<<< HEAD
			DataBaseBusyException, IndeksFormatException,
			InvalidInputFormatException {
		checkIfIndeksExists(primatijada.getIndeks() + "");

		if (primatijada.getTip() == 's')
			if (!primatijada.getSport().matches("([a-z]|[A-Z])+"))
				throw new InvalidInputFormatException();

		if (primatijada.getTip() == 'n')
			if (!primatijada.getRad().matches("([a-z]|[A-Z])+"))
				throw new InvalidInputFormatException();

	}

	public void checkIndeksFormat(String indeksText)
			throws IndeksFormatException, EmptyInputException {

		if (indeksText.equalsIgnoreCase(""))
			throw new EmptyInputException();

		if (!indeksText.matches("([0-9]){5}"))
			throw new IndeksFormatException();

=======
			DataBaseBusyException, IndeksFormatException, InvalidInputFormatException {
		checkIfIndeksExists(primatijada.getIndeks() + "");

		if(primatijada.getTip() == 's')
			if (!primatijada.getSport().matches("([a-z]|[A-Z])+"))
				throw new InvalidInputFormatException();
		
		if(primatijada.getTip() == 'n')
			if(!primatijada.getRad().matches("([a-z]|[A-Z])+"))
				throw new InvalidInputFormatException();
		
>>>>>>> 2c0224dd3814943461d71ac46b0c70cad05aeeaa
	}

}
