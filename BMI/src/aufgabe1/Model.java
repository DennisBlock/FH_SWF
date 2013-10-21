package aufgabe1;

public class Model
{
    private double groesse;
    private double gewicht;
    private int klassifikation;

    private double normalizedGroesse;
    private double normalizedGewicht;
    
    private int random;

    private boolean isNormalized = false;
    
    public Model(double groesse, double gewicht, int klassifikation, int random)
    {
	this(groesse, gewicht, klassifikation);
	this.random = random;
    }

    public Model(double groesse, double gewicht, int klassifikation)
    {
	this.groesse = groesse;
	this.gewicht = gewicht;
	this.klassifikation = klassifikation;
    }

    public int getKlassifikation()
    {
	return klassifikation;
    }

    public double getGroesse()
    {
	if (isNormalized)
	    return normalizedGroesse;

	return groesse;
    }

    public void setGroesse(double groesse)
    {
	this.groesse = groesse;
    }

    public double getGewicht()
    {
	if (isNormalized)
	    return normalizedGewicht;

	return gewicht;
    }

    public void setGewicht(double gewicht)
    {
	this.gewicht = gewicht;
    }
    
    public int getRandom()
    {
	return random;
    }

    public void normalize(double averageHeight, double averageWeight,
	    double standartHeight, double standartWeight)
    {
	normalizedGroesse = (groesse - averageHeight) / standartHeight;
	normalizedGewicht = (gewicht - averageWeight) / standartWeight;

	isNormalized = true;
    }

    public void unnormalize()
    {
	isNormalized = false;
    }

    @Override
    public String toString()
    {
	return "Model [isNormalized=" + isNormalized + ", getKlassifikation()="
		+ getKlassifikation() + ", getGroesse()=" + getGroesse()
		+ ", getGewicht()=" + getGewicht() + "]";
    }

}
