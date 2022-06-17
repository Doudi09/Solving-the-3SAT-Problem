package SAT;

import GA.DNA;

import java.util.ArrayList;

public class SAT {

    private ArrayList<Clause> sat;
    private int nbrClause;
    private int nbrVariables;
    /**
     * //each Y[i] contains the freq of sat for each clause i /  individual population
     *     public ArrayList<Double> Y = new ArrayList<>(); //init 0
     *    // the weights attributed to each clauses
     *     //the max Wi value coresponds to the difficult clause to sat
     *     public ArrayList<Double> W = new ArrayList<>(); //init 1
     */




    public int getNbrClause() {
        return nbrClause;
    }

    public int getNbrVariables() {
        return nbrVariables;
    }

    public SAT(ArrayList<String> file, int nbrVariables, int nbrClause){
        //creatig a sat clauses from string that contains the CNF file clauses
        sat = new ArrayList<Clause>();
        this.nbrClause = nbrClause;
        this.nbrVariables = nbrVariables;

        for (String line : file)
            sat.add(new Clause(line,nbrVariables));
    }

    public boolean evaluateMe(ArrayList<Integer> instance){
        for(Clause ci : sat){
            if(! ci.evaluate(instance))
                return false;
        }
        return true;

    }


    public int evaluate(ArrayList<Integer> position){
        int fit = 0;
        for(Clause ci : sat){
            if(ci.evaluate(position))
                fit++;
        }
        return fit;
    }

    public int evaluate(DNA dna){
        int fit =0;
        for(Clause ci : sat){
            if(ci.evaluate(dna))
                fit++;
        }
        return fit;
    }

    public int evaluate2(DNA dna){
        int fit =0;
        for(Clause c : sat){
            if(c.evaluate2(dna))
                fit++;
        }
        return fit;
    }



    public void show(){
        if(sat==null)
            System.out.println("error sat is null ");
        for(Clause c : sat){
            if(c==null)
                System.out.println("error clause is null ");
            System.out.println(c.toString());
        }

    }

}
