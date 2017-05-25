package application.window;

import application.service.PrimatijadaService;
import application.service.ValidationService;

public abstract class Window {
	
    protected static final int WIDTH = 600;
    protected static final int HEIGHT = 380;
    protected static final String TITLE = "Primatijada";
    
    protected static final String NUMBER_FORMAT_ERROR = "Unesite broj indeksa";
    protected static final String EMPTY_INPUT_ERROR = "Ovo polje je obavezno";
    protected static final String INDEKS_NOT_EXISTS_ERROR = "Ne postoji prijava sa ovim indeksom";
    protected static final String DATA_BASE_BUSY_ERROR = "Baza podataka zauzeta";
    protected static final String INPUT_TOO_LONG = "Unos je previse dugacak";
    protected static final String INVALID_INPUT_FORMAT = "Los format";
    protected static final String INDEKS_TAKEN_ERROR = "Vec prijavljen";
    
	protected PrimatijadaService service;
	protected ValidationService validationService;
	
	abstract public void run();

}
