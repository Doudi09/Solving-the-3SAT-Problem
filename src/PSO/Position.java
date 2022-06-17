package PSO;




import java.util.ArrayList;

public class Position {

    private ArrayList<Integer> position;
    private int fitness;
    private int lenght;


    public Position(){

    }

    public Position(int lenght) {
        this.lenght = lenght;
        position = new ArrayList<>();
        for (int i = 0; i < lenght; i++)
            position.add((int) (Math.random()*2));

    }



    public int compareTo(Position o){
        if(fitness > o.getFitness())
            return 1;
        if (fitness<o.getFitness())
            return -1;
        return 0;
    }


    public int distanceHamming(Particle particle) {
        int distance = 0;
        for(int i=0; i<particle.getPosition().getLenght();i++){
            if(position.get(i) != particle.getPosition().getPosition().get(i))
                distance++;
        }
        return distance;
    }

    public int distanceSimilarity(Position pos){
        int similarity = 0;
        for(int i=0;i<pos.getLenght();i++){
            if(pos.getPosition().get(i) != getPosition().get(i))
                similarity++;
        }
        return getLenght() - similarity;
    }



    public Position clone(){
        Position copy = new Position();
        copy.setLenght(lenght);
        copy.setFitness(fitness);
        copy.position = new ArrayList<>();
        for(int i=0;i<lenght;i++){
            copy.position.add(position.get(i));
        }
        return copy;
    }

    public int getFitness() {
        return fitness;
    }

    public ArrayList<Integer> getPosition() {
        return position;
    }

    public int getLenght() {
        return lenght;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public void setPosition(ArrayList<Integer> position) {
        this.position = position;
    }
}
