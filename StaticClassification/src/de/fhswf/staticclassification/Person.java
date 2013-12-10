package de.fhswf.staticclassification;

import de.fhswf.staticclassification.RepFactory.PersonKlasifikator;

public class Person {
	private double groesse;
	private double gewicht;
	private int klassifikation;

	public Person(double groesse2, double gewicht2, int klassifikation) {
		this.groesse = groesse2;
		this.gewicht = gewicht2;
		this.klassifikation = klassifikation;
	}

	public Person(double groesse, double gewicht,
			PersonKlasifikator personKlasifikator) {
		this.groesse = groesse;
		this.gewicht = gewicht;

		if (personKlasifikator.equals(PersonKlasifikator.Normal)) {
			this.klassifikation = 0;
		} else {
			this.klassifikation = 1;
		}

	}

	public int getKlassifikation() {
		return klassifikation;
	}

	public void setKlassifikation(int klassifikation) {
		this.klassifikation = klassifikation;
	}

	public Double getGroesse() {
		return groesse;
	}

	public void setGroesse(double groesse) {
		this.groesse = groesse;
	}

	public double getGewicht() {
		return gewicht;
	}

	public void setGewicht(double gewicht) {
		this.gewicht = gewicht;
	}

	@Override
	public String toString() {
		return groesse + " " + gewicht + " "
				+ (klassifikation == 1 ? "Übergewicht" : "Normal");
	}

}