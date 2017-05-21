package application.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import application.exception.DataBaseBusyException;
import application.model.Primatijada;
import application.repository.PrimatijadaRepository;
import application.repository.PrimatijadaRepositoryImplementation;

public class LoggingService {
	private PrimatijadaRepository repository;

	public LoggingService() {
		repository = new PrimatijadaRepositoryImplementation();
	}

	public void makeReport() throws DataBaseBusyException {
		List<Primatijada> list = repository.retriveAllForCurrentYear();
		if (list != null) {

			try {
				int n = 0;
				int sCount = 0, nCount = 0, xCount = 0;
				PrintWriter writer = new PrintWriter("Izvestaj.txt", "UTF-8");
				writer.println("Izvestaj "
						+ Calendar.getInstance().get(Calendar.YEAR) + " : ");
				writer.println("indeks | godina |   tip   |  aranzman");
				for (Primatijada primatijada : list) {
					switch (primatijada.getTip()) {
					case 's':
						sCount++;
						break;
					case 'n':
						nCount++;
						break;
					default:
						xCount++;
						break;
					}
					n++;
					writer.println(primatijada.toString());
				}
				writer.println();
				writer.println("Statistika: ");
				writer.println("Ukupno prijavljenih : " + nCount);
				writer.println("	Broj navijaca: " + xCount + " | " + xCount
						/ (double) n * 100 + "%");
				writer.println("	Broj sportista: " + sCount + " | " + sCount
						/ (double) n * 100 + "%");
				writer.println("	Broj naucnika: " + nCount + " | " + nCount
						/ (double) n * 100 + "%");

				writer.close();
			} catch (IOException e) {
				System.out.println("Failed to make report");
			}

		} else {
			System.out.println("Data base empty");
		}
	}
}
