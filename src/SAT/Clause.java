package SAT;

import GA.DNA;

import java.util.ArrayList;


public class Clause {

    ArrayList<Integer> ci;
    ArrayList<Literal> c;



    int nbrVariable;
    //an element is either a 1 means that the element exists == true,
    // 0 the element does not exist and
    //  -1 for the negation



    public String toString(){
    String a = "";
    for(int i : ci)
        a = a + " "+ i;
    return a;
    }

    //constructor for
    public Clause(String line, int nbrVariable){
        c = new ArrayList<Literal>();
        this.nbrVariable = nbrVariable;
        int i =0;
        try {
            for(String var : line.trim().split(" ")){
                int variable = Integer.parseInt(var.trim());
                if(variable<0)
                    c.add(new Literal(-1*variable,0));
                else
                    if(variable>0)
                        c.add(new Literal(variable,1));
                i++;
            }
        }catch (Exception e){
            System.err.println("CLASS Clause > Constructor >> ERROR while decoding the clause line "+i+" "+ line);
        }
    }


    public Clause(String line){
        int v;
        this.nbrVariable = nbrVariable;
        ci = new ArrayList<>(nbrVariable);

        //felling the vector with zeros
        for(int i=0;i<nbrVariable;i++)
            ci.add(i,0);

        int i =0;
        //edcoding the CNF file line
        for(String var : line.trim().split(" ")){
            i++;
            try {
               v = Integer.valueOf(var.trim());

               if(v>0){
                   ci.add(v-1,1);
                   ci.remove(ci.size()-1);
               }
               else
                   if (v<0){
                       ci.add(Math.abs(v)-1,-1);
                       ci.remove(ci.size()-1);
                   }

           }catch (Exception e){

               System.err.println("CLASS Clause > Constructor >> ERROR while decoding the clause line "+i+" "+ line);
           }
        }
    }


    public boolean evaluate(ArrayList<Integer> instance) {

        for(int i=0;i<c.size();i++){
            //System.out.println("clause variable "+c.get(i).getVariable());
            if(instance.get(c.get(i).getVariable()-1) == c.get(i).getValue())
                return true;
        }
        return false;


        /**for(int i =0;i<nbrVariable;i++){
            if(ci.get(i)!=0 && (instance.get(i)==1 && ci.get(i)==1 || instance.get(i)==0 && ci.get(i)==-1))
                return true;
        }
        return false;**/
    }


    public boolean evaluate(DNA dna){
        for(int i =0;i<dna.getize();i++){
            if(ci.get(i)!=0 && ((dna.getGene(i)==1 && ci.get(i)==1) || (dna.getGene(i)==0 && ci.get(i)==-1)))
                return true;
        }
        return false;
    }


    public boolean evaluate2(DNA dna){
        for(int i=0;i<c.size();i++){
            //System.out.println("clause variable "+c.get(i).getVariable());
            if(dna.getGene(c.get(i).getVariable()-1) == c.get(i).getValue())
                return true;
        }
        return false;
    }






}
