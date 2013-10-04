package aufgabe1;

public class Model
{
   private double groesse;
   private double gewicht;
   private int klassifikation;
   
   public Model(double groesse2, double gewicht2, int klassifikation)
   {
      this.groesse = groesse2;
      this.gewicht = gewicht2;
      this.klassifikation = klassifikation;
   }

   public int getKlassifikation()
   {
      return klassifikation;
   }

   public void setKlassifikation(int klassifikation)
   {
      this.klassifikation = klassifikation;
   }

   public double getGroesse()
   {
      return groesse;
   }

   public void setGroesse(double groesse)
   {
      this.groesse = groesse;
   }

   public double getGewicht()
   {
      return gewicht;
   }

   public void setGewicht(double gewicht)
   {
      this.gewicht = gewicht;
   }

   @Override
   public String toString()
   {
      return groesse + " " + gewicht + " " + klassifikation;
   }
    
   
}
