package de.fhswf.randomwalk;

import java.util.Vector;

public class Main
{
   // 180.000 für 10 sekunden
   private int MAXITER = 1000;
   private int N = 1;
   private double H = 0.2;
   
   
   public void setN(int n) { N = n;}
   
   public double rnd()
   {
      double rand = Math.random() * 2 -1;
      return rand;
   }
   
   public double function(Vector<Double> x)
   {
      double sum = 0.0;
      for(int i=0; i< x.size(); i++)
      {
         sum += 100 * Math.pow(Math.sin(300 * x.get(i) - 100),2) + Math.pow((3 * x.get(i) - 1),2);
      }
      return(sum);
   }
   
   public void zufallsaufstieg()
   {
      double erg;
      Vector<Double> vec = new Vector<Double>();
      double min;
      Vector<Double> minX = new Vector<Double>();
      
      
      vec.add(-0.6743705171552017);
      erg = function(vec);

      min = erg;
      minX = vec;
      
      for(int i=0; i < MAXITER; i++)
      {
//         System.out.print("i = " + i); 
         for(int k=0; k < N; k++)
         {
            if(k==0) vec.set(k,rnd());
            else if(i==0) vec.add(rnd());
            else vec.set(k, rnd());
         }
         
         erg = function(vec);
         
    
         if(erg < min)
         {
            min = erg;
            minX = (Vector) vec.clone();
         }
//         System.out.println("  min= " + min + " erg:" + erg + "vector:" + vec.get(0));
//         System.out.println("  min= " + min); 
      }
      
      System.out.println("\nMinimum gefunden bei x=(");
      for(int i = 0; i < N; i++)
      {
         System.out.print("\t" + minX.get(i) + "  ");
      }
      System.out.printf("\n)\nmit f(x) =" + min + " \n\n");
      
   }
   
   public void durchforsten()
   {
      Vector<Double> vec = new Vector<Double>();
      double erg;
      double min;
      Vector<Double> minX = new Vector<Double>();
      
//      int nr = 2;
      
//      System.out.println("  min= " + min);        
      
      for (int k = 0; k < N; k++)
      {
         vec.add(-1.0);   
      }
      
      
      vec.set(0, -1.0);
      erg = function(vec);
      
      min = erg;
      minX = vec;
      
         for(double i=-1.0; i < 1.0; i = i + H)
         {
            vec.set(0, i);
            
            if(N > 1)
            {
               for(double i2=-1.0; i2 < 1.0; i2 = i2 + H)
               {
                  vec.set(1, i2);
                  for(double i3=-1.0; i3 < 1.0; i3 = i3 + H)
                  {
                     vec.set(2, i3);
                     for(double i4=-1.0; i4 < 1.0; i4 = i4 + H)
                     {
                        
                        vec.set(3, i4);
                        erg = function(vec);
//                        System.out.println(vec + " = " + erg);
                         if(erg < min)
                         {
                            min = erg;
                            minX = (Vector) vec.clone();
//                            System.out.println("Minimum bei :"+ vec.get(0) + "," + vec.get(1) + "," + vec.get(2) + "," + vec.get(3));
                         }  
                     }
                  }
               }
            }
            else
            {
               erg = function(vec);
               
               if(erg < min)
               {
                  min = erg;
                  minX = (Vector) vec.clone();
               }  
            }
         }  
      
      System.out.println("\nAufruf der Zielfunktion: 2/h = " + Math.pow((2/H), N));
      System.out.print("\nMinimum gefunden bei x=(\n");
      for(int i = 0; i < N; i++)
      {
         System.out.print("\t" + minX.get(i) + "  ");
      }
      System.out.printf("\n)\nmit f(x) =" + min + " \n\n");
      
   }
   

   /**
    * Diese Methode TODO ADD Comment here
    * @param args
    */
   public static void main(String[] args)
   {
      
      Main main = new Main();
      
      for(int i=1; i<=4; i= i + 3)
      {
         main.setN(i);         
         System.out.println("############# Zufallsaufstieg mit n=" + i + " #############");
         main.zufallsaufstieg();
         System.out.println("############# Systematisches Durchforsten mit n="+ i + " #############");         
         main.durchforsten();
         
      }
      

   }

}
