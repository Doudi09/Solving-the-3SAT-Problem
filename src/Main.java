import BSO.BSO;
import SAT.SAT;
import javafx.application.Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {


    public static int CLAUSENBR = 325;
    public static int VARIABLENBR = 75;
    public static int TARGET = 325;

    public static void main(String[] args) {





/*
        //String pathFile = System.getProperty("user.dir")+"\\src\\FILES\\uf75-0";
        String pathFile = "uf75-0";

        double t1, t2, delta;
        ArrayList<String> file ;
        System.out.println("\n\n ");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("les résultats d'exécution de problème SAT -- BSO -- ");
        System.out.println("-----------------------------------------------------------------------");



        for (int i = 1; i <= 10; i++) {
            file = readFile(pathFile + i + ".cnf");
            SAT sat = new SAT(file, VARIABLENBR, CLAUSENBR);
            BSO bso = new BSO(sat);
            t1 = System.currentTimeMillis();
            int fit = bso.run(1, TARGET, 18, 5, 2540
                    , 26);
            t2 = System.currentTimeMillis();
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Le fichier : " +i);
            System.out.println("Le temps d'exécution  " + (t2 - t1) / 1000 + " s ");
            System.out.println("Le nombre de clause satisfiables : " + fit + "/325");
            System.out.println("-----------------------------------------------------------------------");

        }

 */
    }










    public static ArrayList<String> readFile(String filename,boolean bool) {
        ArrayList<String> file = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                if (i > 8 && i < 334) {
                    file.add(line);
                } /**else
                 System.out.println(line);**/
            }
            reader.close();
            return file;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }





    public static ArrayList<String> readFile(String filename) {
        ArrayList<String> file = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    ClassLoader.getSystemClassLoader().getResourceAsStream(filename)
            ));

            //BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                if (i > 8 && i < 334) {
                    file.add(line);
                } /**else
                 System.out.println(line);**/
            }
            reader.close();
            return file;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }



}

