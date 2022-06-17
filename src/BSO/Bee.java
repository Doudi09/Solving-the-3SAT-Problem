package BSO;

import SAT.SAT;

import java.util.ArrayList;

public class Bee implements Comparable<Bee> {


    private ArrayList<Integer> bee;
    private int fitness;

    public Bee() {
        bee = new ArrayList<>();
    }


    public Bee(SAT sat) {

        bee = new ArrayList<>();
        for (int i = 0; i < sat.getNbrVariables(); i++) {
            bee.add((int) (Math.random() * 2));
        }
        fitness = sat.evaluate(bee);
    }


    public Bee localSearchNeighbors(SAT sat) {
        Bee beeMax = copy();
        for (int i = 0; i < beeMax.size(); i++) {
            Bee b = copy();
            b.flip(i);
            b.calculFitness(sat);
            if (b.getFitness() > beeMax.getFitness())
                beeMax = b;
        }
        return beeMax;
    }


    public Bee localSearchNotInTaboo(int nbrRecherche, SAT sat, int flip, ArrayList<Bee> taboo) {
        //search in her local area and return the best result found:
        Bee beeMax = copy();
        /*int h = 0;
        int i = 0;
        while (i < nbrRecherche && h < flip) {
            Bee b = copy();
            int p = 0;
            while (flip * p + h < b.size()) {
                b.flip(flip * p + h);
                p++;
            }
            b.calculFitness(sat);

        }*/
        for (int i = 0; i < nbrRecherche; i++) {
            //generation the search on nbrRecherche bee
            //the bees are conclud from the bee neighbors
            Bee b = copy();
            /**
             //fliping the i elemnt so evrey time the search is done on
             // n element where n is the size of the bee
             b.flip(i);
             b.calculFitness(sat);
             **/
            //b.doflip(flip, sat, i);
            //the best one :
            //b.doflipAleatoire(flip, sat);
            //the real algo :
            b.doflipAleatoire(1,sat);
            if (b.fitness > beeMax.fitness && !taboo.contains(b))
                beeMax = b;
        }
        return beeMax;
    }


    public Bee localSearch(int nbrRecherche, SAT sat, int flip) {
        //search in her local area and return the best result found:
        Bee beeMax = copy();
        /*int h = 0;
        int i = 0;
        while (i < nbrRecherche && h < flip) {
            Bee b = copy();
            int p = 0;
            while (flip * p + h < b.size()) {
                b.flip(flip * p + h);
                p++;
            }
            b.calculFitness(sat);

        }*/

        for (int i = 0; i < nbrRecherche; i++) {
            //generation the search on nbrRecherche bee
            //the bees are conclud from the bee neighbors
            Bee b = copy();
            /**
             //fliping the i elemnt so evrey time the search is done on
             // n element where n is the size of the bee
             b.flip(i);
             b.calculFitness(sat);
             **/
            b.doflip(flip, sat, i);
            if (b.fitness > beeMax.fitness)
                beeMax = b;
        }
        return beeMax;
    }


    public int distance(Bee b) {
        //it calcul the hamming distance :
        int distance = 0;
        for (int i = 0; i < bee.size(); i++) {

            if (bee.get(i) != b.bee.get(i))
                distance++;
        }
        return distance;
    }


    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int calculFitness(SAT sat) {

        return sat.evaluate(bee);
    }

    public int getFitness() {
        return fitness;
    }

    public int size() {
        return bee.size();
    }

    public Bee copy() {
        Bee b = new Bee();
        for (int i = 0; i < bee.size(); i++) {
            b.bee.add(bee.get(i));
        }
        b.fitness = fitness;
        return b;
    }

    public Bee copy(SAT sat) {
        Bee b = new Bee();
        for (int i = 0; i < bee.size(); i++) {
            b.bee.add(bee.get(i));
        }
        b.fitness = b.calculFitness(sat);
        return b;
    }


    public void doflip(int flip, SAT sat, int p) {

        for (int i = 0; i < flip; i++) {
            if (flip * i + p < bee.size())
                flip(flip * i + p);
        }
        fitness = sat.evaluate(bee);
    }

    public void doflipAleatoire(int flip, SAT sat) {
        for (int i = 0; i < flip; i++) {

            flip((int)(Math.random()*bee.size()));
        }
        fitness = sat.evaluate(bee);
    }


    /**
     * public void doflip(int flip, SAT sat) {
     * for (int i = 0; i < flip; i++) {
     * flip((int) (Math.random() * bee.size()));
     * }
     * fitness = sat.evaluate(bee);
     * }
     **/

    public void flip(int i) {
        bee.set(i, (bee.get(i) + 1) % 2);
    }

    @Override
    public int compareTo(Bee o) {

        if (o == null)
            return -1;
        Bee b = (Bee) o;

        if (b.getFitness() > this.getFitness())
            return 1;
        if (b.getFitness() < this.getFitness())
            return -1;

        return 0;
        //return fitness - b.getFitness();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Bee))
            return false;
        Bee b = (Bee) o;
        return b.getFitness() == fitness && b.bee.equals(bee);

    }


    @Override
    public String toString() {
        return "Bee{" +
                "bee = " + bee +
                "\nfitness=" + fitness +
                '}';
    }


}
