package BSO;

import SAT.SAT;

import java.util.ArrayList;
import java.util.Collections;

public class BSO {

    SAT sat;
    //int n; //represent the lenght of a bee
    Bee sref;
    ArrayList<Bee> dance;
    ArrayList<Bee> listTaboo;
    //paramètres empiriques
    int flip;
    int nbrChances;
    int nbrIteration;
    int nbrBees;
    int nbrLocalSearchIteration;


    public BSO(SAT sat, int flip, int nbrBees, int nbrChances, int nbrIteration, int nbrLocalSearchIteration) {
        this.sat = sat;
        this.flip = flip;
        this.nbrBees = nbrBees;
        this.nbrChances = nbrChances;
        this.nbrIteration = nbrIteration;
        this.nbrLocalSearchIteration = nbrLocalSearchIteration;
        dance = new ArrayList<Bee>();
        listTaboo = new ArrayList<Bee>();
    }

    public BSO(SAT sat) {
        this.sat = sat;
        dance = new ArrayList<Bee>();
        listTaboo = new ArrayList<Bee>();
    }


    public int run(int flip, int max, int nbrBees, int nbrChances, int nbrIteration, int nbrLocalSearchIteration) {

        this.flip = flip;
        this.nbrBees = nbrBees;
        this.nbrChances = nbrChances;
        this.nbrIteration = nbrIteration;
        this.nbrLocalSearchIteration = nbrLocalSearchIteration;

        int chances = nbrChances;
        //initializing the sref
        sref = new Bee(sat);
        Bee bestResult = sref.copy(sat);
        for (int i = 0; i < nbrIteration; i++) {

            if (sref.getFitness() > bestResult.getFitness())
                bestResult = sref.copy(sat);

            if (bestResult.getFitness() == max) {
                //System.out.println("the max is found");
                //System.out.println(sref.toString());
                //System.out.println("the iteration number = " + i);
                return max;
            }
          //  System.out.println("The best dolution found till now :\n" + bestResult.toString());
            //System.out.println("The sref :\n" + sref.toString());


            listTaboo.add(sref);
            ArrayList<Bee> bees = searchAreaBees(sref);

            for (Bee bee : bees) {
                //searching localy and adding the solution to dance !
                //dance.add(bee.localSearch(nbrLocalSearchIteration, sat, flip));
                dance.add(bee.localSearchNotInTaboo(nbrLocalSearchIteration, sat, flip, listTaboo));
                // dance.add(bee.localSearchNeighbors(sat));
            }
            //sorting the dance table
            try {
                Collections.sort(dance);
            } catch (Exception e) {
                System.err.println("CLASS BSO > METHOD run while sorting >> ERROR -");
                e.printStackTrace();
            }
            //geting the best solution from dance

            Bee maxDance = dance.get(0).copy(sat);

            //verify if it does exist on the listTaboo
            /*
            while (listTaboo.contains(maxDance) && dance.size() > 1) {
                dance.remove(maxDance);
                maxDance = dance.get(0);
            }*/

            if (! listTaboo.contains(maxDance) && maxDance.getFitness() > sref.getFitness()) {
                //System.out.println("we are in progress +++++++++++++++++++++++++++++++++++++++++ ");
                sref = maxDance.copy(sat);
                //bestResult = maxDance;
                if (chances < nbrChances)
                    chances = nbrChances;
            } else {

                //the sref is not getting better
                //stagnation problem !
                chances--;
                //if(chances >0) we still get chances so we dont change the solution
                //sref = maxDance;
                //otherwise
                if (chances <= 0) {
                    //we dont have any chances
                    //so we change the quality of sref
                    Bee bestquality = bestDiversity();
                    if (listTaboo.contains(bestquality)) {
                        //Bee newbee = new Bee(sat);
                        //sref = newbee;
                        sref = new Bee(sat);
                  //      System.out.println("a new solution -----------------------------------------------------");

                    } else {
                        sref = bestquality.copy(sat);
                    //    System.out.println("the best diversity **********************************************");
                    }


                    chances = nbrChances;
                }
                //else
                    //System.out.println("we still have chance so we keep it "+chances);
            }
            //dance.remove(maxDance);
            //clearing the dance table :
            dance.clear();
        }

        //System.out.println("the problem is not satisfied ");
        //System.out.println("The best solution found is \n" + bestResult);
        return bestResult.getFitness();

    }


    public int diversity(Bee b) {
        int minDistance = b.distance(listTaboo.get(0));
        for (int i = 0; i < listTaboo.size(); i++) {
            if (minDistance > b.distance(listTaboo.get(i)))
                minDistance = b.distance(listTaboo.get(i));
        }
        return minDistance;
    }


    public Bee bestDiversity() {
        //Srefis best in diversity ====> diversity(Sref) = Max diversity (s)) Where s belongs to Dance.
        // diversity(s)= Min { distance(s,t), t in list taboo }
        Bee max = dance.get(0).copy();
        int bestDistance = diversity(dance.get(0));

        for (int i = 0; i < dance.size(); i++) {
            int a = diversity(dance.get(i));
            if (bestDistance < a) {
                max = dance.get(i).copy();
                bestDistance = a;
            }
        }
        max.setFitness(max.calculFitness(sat));

        //System.out.println("the bee on bext diver "+max);
        return max;
    }


    public ArrayList<Bee> searchAreaBees(Bee bee) {
        int h = 0;
        ArrayList<Bee> search = new ArrayList<Bee>();
        while (search.size() < nbrBees && h < flip) {
            Bee s = bee.copy();
            int p = 0;
            while (flip * p + h < s.size()) {
                s.flip(flip * p + h);
                p++;
            }
            s.calculFitness(sat);
            search.add(s);
            h++;
        }
        return search;
    }


}
