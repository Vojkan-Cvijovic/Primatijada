package application.service;

import application.exception.DataBaseBusyException;
import application.exception.EmptyInputException;
import application.exception.IndeksFormatException;
import application.exception.InvalidInputLengthException;
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

	public boolean checkIfIndeksExists(String text) throws EmptyInputException,

	NumberFormatException, RecordNotExistsException, DataBaseBusyException,
			IndeksFormatException {

		if (text.trim().isEmpty())
			throw new EmptyInputException();

		if (!text.matches("([0-9]){5}"))
			throw new IndeksFormatException();

		int indeks = Integer.parseInt(text);

		repository.retrieve(indeks);

		return true;

	}

	public void checkOptionsInput(String category, String text)
			throws EmptyInputException, InvalidInputLengthException,
			InvalidInputFormatException {

		if (category.equalsIgnoreCase("Navijac"))
			return;

		if (text.trim().isEmpty())
			throw new EmptyInputException();
		if (category.equalsIgnoreCase("Sportista")) {
			if (text.length() > 20)
				throw new InvalidInputLengthException();
		} else if (category.equalsIgnoreCase("Naucnik")) {
			if (text.length() > 60)
				throw new InvalidInputLengthException();
		}
		if (!text.matches("([a-z]|[A-Z]|[ ])+"))
			throw new InvalidInputFormatException();

	}

	public void check(Primatijada primatijada) throws NumberFormatException,
			EmptyInputException, RecordNotExistsException,
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

	}

	public boolean checkIfExists(Primatijada primatijada)
			throws DataBaseBusyException {
		try {
			repository.retrieve(primatijada.getIndeks());
		} catch (RecordNotExistsException e) {
			return false;
		}
		return true;
	}


	public boolean checkIfExists(Primatijada primatijada) throws DataBaseBusyException {
		try {
			repository.retrieve(primatijada.getIndeks());
		} catch (RecordNotExistsException e) {
			return false;
		}
		return true;
	}

}
