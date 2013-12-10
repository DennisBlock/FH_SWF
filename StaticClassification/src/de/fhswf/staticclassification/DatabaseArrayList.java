/**
 * 
 */
package de.fhswf.staticclassification;

import java.util.ArrayList;

import de.fhswf.staticclassification.RepFactory.PersonKlasifikator;

/**
 * @author Natalie Block
 * 
 */
public class DatabaseArrayList {

	private ArrayList<Person> database;

	public DatabaseArrayList() {
		createDatabase();
	}

	private void createDatabase() {

		database = new ArrayList<Person>();
		Person model;
		double groesse;
		double gewicht;
		int klassifikation;
		for (int i = 0; i < 200; i++) {
			groesse = 162 + (7 * i % 19) + (5 * i % 7) * (i % 3);
			gewicht = (groesse - 100 + 2 * (i % 2 - 0.5)
					* (3 * i % 11 + 5 * i % 13 + 1));
			klassifikation = i % 2;
			if (i % 19 == 0 && i != 0) {
				klassifikation = 1 - klassifikation;
			}
			model = new Person(groesse, gewicht, klassifikation);
			database.add(model);
		}
	}

	public void add(Person value) {
		database.add(value);
	}

	public Person get(int index) {
		if (index >= database.size())
			throw new IndexOutOfBoundsException(String.valueOf(index));
		return database.get(index);
	}

	public void set(int index, Person value) {
		if (index >= database.size())
			throw new IndexOutOfBoundsException(String.valueOf(index));
		database.set(index, value);
	}

	public ArrayList<Person> selectPersonOf(
			PersonKlasifikator personKlasifikator) {
		ArrayList<Person> selectedPerson = new ArrayList<Person>();
		switch (personKlasifikator) {
			case Normal :

				// TODO: nicht von den testdaten!
				for (int i = 0; i < 100; i++) {
				    Person p = database.get(i);
					if (p.getKlassifikation() == 0) {
						selectedPerson.add(p);
					}
				}

				break;
			case Overweight :

				for (Person p : database) {
					if (p.getKlassifikation() == 1) {
						selectedPerson.add(p);
					}
				}
				break;

			default :
				throw new IllegalArgumentException("Falscher Klassifikator");
		}
		return selectedPerson;
	}

	public void clear() {
		database.clear();
	}
	
	public int size() {
	    return database.size();
	}

	@Override
	public String toString() {
		return "DatabaseArrayList [database=" + database + "]";
	}

}