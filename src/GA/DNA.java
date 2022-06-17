package GA;

import SAT.SAT;

import java.util.ArrayList;

public class DNA  implements Comparable {


    private ArrayList<Integer> chromosome;
    SAT sat;



    public DNA(int t []){
        chromosome = new ArrayList<>();
        for(int a : t){
            if(a>0)
                chromosome.add(1);
            else
                chromosome.add(0);
        }
    }
    public DNA(SAT sat){
        this.sat = sat;
        chromosome = new ArrayList<Integer>();
    }

    public DNA(SAT sat, int size){
        if(sat==null)
            System.out.println("error sat is null in the DNA constructor ");
        this.sat = sat;
        chromosome = new ArrayList<>();
        for(int i=0;i<size;i++){
            //generate radom numbers 0 or 1:
            chromosome.add((int)(Math.random()*2));
        }
    }

    public void addElement(int e){
        chromosome.add(e);
    }

    public int getGene(int i){
        return chromosome.get(i);
    }

    public DNA( ArrayList<Integer> n ){
        chromosome = n;
    }

    public DNA(){
        chromosome = new ArrayList<>();
    }

    public DNA(int size){
        chromosome = new ArrayList<>();
        for(int i=0;i<size;i++){
            //generate radom numbers 0 or 1:
            chromosome.add((int)(Math.random()*2));
        }
    }

    public int getize(){
        return chromosome.size();
    }

    public int fitnesse(SAT sat){
       return sat.evaluate2(this);
    }

    public int fitnesse2(SAT sat){
        return (int) Math.pow(2,sat.evaluate2(this));
    }


    public String toString(){
        return ""+ chromosome;
    }

    @Override
    public int compareTo(Object o) {
        if(o==null)
            return -1;
        DNA object = (DNA) o;
        if(sat ==null)
            System.out.println("the sat is null :(");
        if(object.fitnesse(sat)>this.fitnesse(sat))
            return 1;
        if(object.fitnesse(sat)<this.fitnesse(sat))
            return -1;

        /**
        if(sat.evaluate2(object)>sat.evaluate2(this))
            return 1;
        if(sat.evaluate2(object)<sat.evaluate2(this))
            return -1;
         **/
        return 0;
    }


    public int getElement(int i){
        return chromosome.get(i);
    }



    public void setGene(int a, int b){
        chromosome.set(a,b);
    }



    public void mutate(double mutation){
        int m = (int) (chromosome.size() * mutation/100);
        for(int i=0; i<m; i++){
            int a = (int)(Math.random()* chromosome.size());
            if(chromosome.get(a)==0)
                chromosome.set(a,1);
            else
                chromosome.set(a,0);
        }

    }


    public DNA uniformCrossover2(DNA parent) {
        DNA cross = new DNA(sat);
        for(int i=0;i<parent.getize();i++){
            if(i%2 == 0)
                cross.addElement(this.getElement(i));
            else
                cross.addElement(parent.getElement(i));

        }
        return cross;
    }

    public DNA uniformCrossover(DNA parent) {
        DNA cross = new DNA(sat);
        for(int i=0;i<parent.getize();i++){
            if(i%2 == 0)
                cross.addElement(parent.getElement(i));
            else
                cross.addElement(this.getElement(i));
        }
        return cross;
    }



    public DNA crossover(DNA parent, int crossOver) {
        DNA cross = new DNA(sat);
        for(int i=0;i<parent.getize();i++){
            int a = (int)Math.random()*parent.getize();
            if(i<a)
                cross.addElement(parent.getElement(i));
            else
                cross.addElement(this.getElement(i));
        }
        return cross;
    }




    public DNA crossover2(DNA parent, int crossOver) {
        DNA cross = new DNA(sat);
        for(int i=0;i<parent.getize();i++){
            if(i<crossOver)
                cross.addElement(this.getElement(i));
            else
                cross.addElement(parent.getElement(i));

        }
        return cross;
    }
}
