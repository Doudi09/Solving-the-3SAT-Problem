package GA;

import SAT.SAT;

import java.util.ArrayList;
import java.util.Collections;

public class Population {

    private ArrayList<DNA> population;
    private DNA parents[];
    private int lenght; //the lenght of one individual
    private ArrayList<DNA> parentsList = new ArrayList<>();
    private int n; //taille de la population
    private double mutation;
    private int crossOverPoint;
    private SAT sat;


    public Population(int n, int lenght, double mutation, int crossOverPoint, SAT sat) {
        population = new ArrayList<>();
        this.sat = sat;
        this.parents = new DNA[2];
        this.lenght = lenght;
        this.mutation = mutation;
        this.crossOverPoint = crossOverPoint;
        this.n = n;
        //initialise the population randomly
        for (int i = 0; i < n; i++) {
            population.add(new DNA(sat, lenght));
        }
    }


    public void selectParents() {
        parents[0] = population.get(0);
        parents[1] = population.get(1);
    }


    public void selectParentsList() {

        parentsList.clear();
        int max = population.get(0).fitnesse(sat);
        for (int i = 0; i < population.size(); i++) {
            int n = (int) (100 * population.get(0).fitnesse(sat) / max);
            for (int j = 0; j < n; j++) {
                parentsList.add(population.get(i));
            }
        }
    }


    public DNA getDNA(int i) {
        return population.get(i);
    }


    public void generate() {
        int n = population.size();
        population.clear();
        for (int i = 0; i < n; i++) {
            int a = (int) Math.random() * parentsList.size();
            int b = (int) Math.random() * parentsList.size();
            DNA child = parentsList.get(a).crossover(parentsList.get(b), crossOverPoint);
            child.mutate(mutation);
            population.add(child);
        }
    }


    /**
     * public void crossOver(){
     * parentsList.addAll(population.subList(0,(n)));
     * population.clear();
     * population.add(parentsList.get(0));
     * int j=0;
     * for(int i=1;i<n;i++){
     * population.add(parentsList.get(j).crossover(parentsList.get(j+1),crossOverPoint));
     * j++;
     * }
     * }
     **/


    public void crossOver() {
        parentsList.clear();
        parentsList = (ArrayList) population.clone();
        population.clear();
        population.add(parentsList.get(0));
        int j = 0;
        for (int i = 1; i < n; i++) {
            population.add(parentsList.get((int) Math.random() * parentsList.size()).crossover(parentsList.get((int) Math.random() * parentsList.size()),
                    (int) Math.random() * lenght));
            j++;
        }
    }


    public void croosover() {
        selectParentsList();
        population.clear();
        population.add(parentsList.get(0));
        for (int i = 1; i < lenght; i++) {
            population.add(parentsList.get(i - 1).crossover(parentsList.get(i), crossOverPoint));
        }

    }


    /**
     * public void croosover(){
     * int i = 6;
     * //the crossover operation :
     * population.clear();
     * population.add(0,parents[0]);
     * population.add(1,parents[1]);
     * // population.add(2,parents[0].crossover(parents[1],crossOverPoint));
     * population.add(2,parents[0].uniformCrossover(parents[1]));
     * //population.remove(population.size()-1);
     * population.add(3,parents[0].uniformCrossover2(parents[1]));
     * //population.add(2,parents[0].uniformCrossover2(parents[1]));
     * // population.remove(population.size()-1);
     * <p>
     * //population.add(3,parents[1].uniformCrossover(parents[0]));
     * // population.remove(population.size()-1);
     * population.add(4,parents[1].uniformCrossover(parents[0]));
     * population.add(5,parents[1].uniformCrossover2(parents[0]));
     * // population.add(5,parents[1].crossover2(parents[0],crossOverPoint));
     * // population.remove(population.size()-1);
     * while(i<n){
     * //generate the reset randomly:
     * population.add(i, new DNA(sat,lenght));
     * // population.remove(population.size()-1);
     * i++;
     * }
     * }
     **/


    public SAT getSat() {
        return sat;
    }

    public void sortPopulation() {
        try {
            Collections.sort(population);
        } catch (Exception e) {
            System.err.println("CLASS Population > METHOD SortPopulation >> ERROR -");
            e.printStackTrace();
        }

    }

    public void showPopulation() {
        for (DNA element : population) {
            System.out.println("- " + element.toString());
        }
    }

    public int getN() {
        return n;
    }


    public void mutation() {
        for (int i = 0; i < n; i++)
            population.get(i).mutate(mutation);
    }


    public void traitement(int target) {
        int iterationNbr = 0;
        int best = population.get(0).fitnesse(sat);
        while (best != target) {
            iterationNbr++;
            sortPopulation();
            if (best == target) {
                System.out.println("Le problème SAT est satisfiable ");
                System.out.println("La solution trouvée");
                System.out.println("" + population.get(0).toString());
                System.out.println("Le nombre de génération créée :" + iterationNbr);

                return;
            }
            //System.out.println("the best score untill now is " + best);
           // System.out.println("with : " + population.get(0));
            /**
             selectParentsList();
             crossOver();
             mutation();
             **/

            //it works like 98% but it never reach the 325 clauses :(
            selectParents();
            croosover();

            mutation();

        }
        sortPopulation();

        System.out.println("Aucune solution n'est trouvée ");
        System.out.println("" + population.get(0).toString());
        System.out.println("taux de satisgfiabilité " + population.get(0).fitnesse(sat));
        System.out.println("Le nombre de génération créée :" + (iterationNbr));

    }


    public void doImmigration(){
        ArrayList<DNA> list = (ArrayList) population.clone();
        population.clear();
        int limit = (int) 80*n/100;
        for (int i=0;i<n;i++){
            if(i<limit)
                population.add(list.get(i));
            else
                population.add(new DNA(sat));
        }
    }

    public int traitementWithImmigration(int iterationNbr, int max, int immigration) {

        for (int i = 0; i < iterationNbr; i++) {
            sortPopulation();
            int best = population.get(0).fitnesse(sat);
            if (best == max) {
                /*System.out.println("Le problème SAT est satisfiable ");
                System.out.println("La solution trouvée");
                System.out.println("" + population.get(0).toString());
                System.out.println("taux de satisfiabilité : " + best + "/325");

                System.out.println("Le nombre de génération créée :" + i + 1);

                 */
                return best;
            }
            //System.out.println("the best score untill now is " + best);
          //  System.out.println("with : " + population.get(0));

            //trying to add immigration:
            if (i > immigration)
                doImmigration();
            croosover();
            mutation();
        }
        sortPopulation();

/*        System.out.println("Aucune solution n'est trouvée ");
        System.out.println("" + population.get(0).toString());
        System.out.println("taux de satisgfiabilité " + population.get(0).fitnesse(sat));
        System.out.println("Le nombre de génération créée :" + (iterationNbr + 1));

 */
       // System.out.println("la population");
        //show();
        return population.get(0).fitnesse(sat);
    }


    public void traitement(int iterationNbr, int max) {

        for (int i = 0; i < iterationNbr; i++) {
            sortPopulation();
            int best = population.get(0).fitnesse(sat);
            if (best == max) {
                System.out.println("Le problème SAT est satisfiable ");
                System.out.println("La solution trouvée");
                System.out.println("" + population.get(0).toString());
                System.out.println("taux de satisfiabilité : " + best + "/325");

                System.out.println("Le nombre de génération créée :" + i + 1);

                return;
            }
            System.out.println("the best score untill now is " + best);
            System.out.println("with : " + population.get(0));


            /**selectParentsList();
             crossOver();
             mutation();*/


            croosover();
            mutation();


            /**
             *
             * //this the first fucking methos
             *  selectParents();
             *             croosover();
             *
             *             mutation();
             */


        }
        sortPopulation();

        System.out.println("Aucune solution n'est trouvée ");
        System.out.println("" + population.get(0).toString());
        System.out.println("taux de satisgfiabilité " + population.get(0).fitnesse(sat));
        System.out.println("Le nombre de génération créée :" + (iterationNbr + 1));

        System.out.println("la population");
        //  show();

    }


    public void traitementDrias(int iterationNbr, int target) {
        for (int i = 0; i < iterationNbr; i++) {
            sortPopulation();
            int best = population.get(0).fitnesse(sat);
            if (target == best) {
                System.out.println("Le problème SAT est satisfiable ");
                System.out.println("La solution trouvée");
                System.out.println("" + population.get(0).toString());
                System.out.println("taux de satisfiabilité : " + best + "/325");
                System.out.println("Le nombre de génération créée :" + i + 1);
                return;
            }

            System.out.println("the best score untill now is " + best);
            System.out.println("with : " + population.get(0));
            //select best 2 individuals
            DNA papa = population.get(0);
            DNA mama = population.get(1);
            DNA child = papa.crossover(mama, crossOverPoint);
            if ((int) Math.random() * 100 < mutation) {
                int m = (int) Math.random() * child.getize();
                if (child.getGene(m) == 1)
                    child.setGene(m, 0);
                else
                    child.setGene(m, 0);
            }

            if (population.get(population.size() - 1).fitnesse(sat) < child.fitnesse(sat))
                population.set(population.size() - 1, child);
        }
        sortPopulation();

        System.out.println("Aucune solution n'est trouvée ");
        System.out.println("" + population.get(0).toString());
        System.out.println("taux de satisgfiabilité " + population.get(0).fitnesse(sat));
        System.out.println("Le nombre de génération créée :" + (iterationNbr + 1));

        System.out.println("la population");
        //  show();

    }


    public void show() {
        for (DNA d : population)
            System.out.println("- " + d.toString());
    }


}
/*
i was trying something !!



public ArrayList<Integer> fitnessList() {
        ArrayList<Integer> fitness = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < population.size(); i++) {
            sum += population.get(i).fitnesse(sat);
            fitness.add(sum);
        }
        return fitness;
    }

    public void selectPrentList(){
        ArrayList<Integer> fitness = fitnessList();

    }
*
 */




