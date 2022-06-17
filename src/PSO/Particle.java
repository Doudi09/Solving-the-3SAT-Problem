package PSO;


import SAT.SAT;


public class Particle {

    private Position position;
    private Position Pbest;
    private double velocity;


    public Particle(int lenght, double velocity) {
        this.velocity = velocity;
        position = new Position(lenght);
        Pbest = position.clone();
    }


    public Position getPbest() {
        return Pbest;
    }

    public int getFitness() {
        return position.getFitness();
    }

    public int compareTo(Particle o) {
        if (position.getFitness() > o.getFitness())
            return 1;
        if (position.getFitness() < o.getFitness())
            return -1;
        return 0;
    }


    public void calculFitness(SAT sat) {
        int fit = sat.evaluate(position.getPosition());
        position.setFitness(fit);
        //update the best particle position:
        if (Pbest.compareTo(position) == -1)
            Pbest = position.clone();
    }


    public Position getPosition() {
        return position;
    }


    public int distanceHamming(Particle particle) {
        int distance = 0;
        for (int i = 0; i < particle.getPosition().getLenght(); i++) {
            if (position.getPosition().get(i) != particle.getPosition().getPosition().get(i))
                distance++;
        }
        return distance;
    }

    public int distanceSimilarity(Particle particle) {
        int similarity = 0;
        for (int i = 0; i < particle.getPosition().getLenght(); i++) {
            if (position.getPosition().get(i) != particle.getPosition().getPosition().get(i))
                similarity++;
        }
        return particle.position.getLenght() - similarity;
    }


    //V(t+1) ==> inertia * V(t) + c1*r1*distance(Pbest,X(t)) + c2*r2*distance(Gbest,X(t))
    //r1, r2 are two random numbers
    //c1 and c2 are between 0 and 2
    public void calculVelocity(double inertia, double c1, double c2, Position Gbest) {
        //we suppose that the random numbers are between 0 and 1
        double r1 = (Math.random());
        double r2 = (Math.random());

        velocity = (inertia * velocity +
                c1 * r1 * Pbest.distanceHamming(this) +
                c2 * r2 * Gbest.distanceHamming(this));
    }

    //the BPSO calcul velocity (the second one)
    //the velocity is calculated by this formula:
    //V(t+1) = g( w*V(t) + c1*r1*distance(Pbest-X(t)) + c2*r2*distance(Gbest-X(t) )
    public void calculVelocityB(double w, double c1, double c2, Position Gbest, double Vmax) {
        //the random should be in [0;1]
        double r1 = Math.random();
        double r2 = Math.random();
        //calculating the velocity:
        velocity = (w * velocity +
                c1 * r1 * Pbest.distanceHamming(this) +
                c2 * r2 * Gbest.distanceHamming(this));
        //calculing the g function :
        if (velocity > Vmax)
            velocity = Vmax;
        if (velocity < -1 * Vmax)
            velocity = -1 * Vmax;
        //otherwise the value of velocity wont change...
    }


    public void inversePositionBit(int index) {
        position.getPosition().set(index, (getPositionBit(index) + 1) % 2);
    }

    public int getPositionBit(int index) {
        return position.getPosition().get(index);
    }

    //updating the position X(t) ==> X(t+1) using the velocity
    //X(t+1) = X(t) + V(t)

    /**
     * //unsing the segmoid function
     * public void updatePosition(SAT sat) {
     * //System.out.println("the velocity is " + velocity);
     * //just because i cant deal with velocity i will khellet jedha lehna :P
     * for (int i = 0; i < position.getPosition().size(); i++) {
     * //inversing the bits :
     * //this mine ::
     * if (Math.random() < segmoid(velocity))
     * inversePositionBit(i);
     * }
     * //calcul the fitness:
     * calculFitness(sat);
     * //updating the Pbest:
     * if (Pbest.compareTo(position) == -1)
     * Pbest = position.clone();
     * <p>
     * }
     **/


    public void updatePosition(SAT sat) {
        //System.out.println("the velocity is " + velocity);
        for (int i = 0; i < velocity % position.getPosition().size(); i++) {
            //inversing the bits :
            inversePositionBit((int) Math.random() * 74);
        }
        //calcul the fitness:
        calculFitness(sat);
        //updating the Pbest:
        if (Pbest.compareTo(position) == -1)
            Pbest = position.clone();

    }



    public void updatePositionBit(int index, int bit){
        position.getPosition().set(index,bit);
    }

    //updating the velocity following the BPSO method:
    //unsing the sigmoid function
    public void updatePositionB(SAT sat) {
        //for all the bit :
        for (int i = 0; i < position.getPosition().size(); i++) {
            if (Math.random() < segmoid(velocity))
                updatePositionBit(i,1);
            else
                updatePositionBit(i,0);
        }
        //calcul the fitness of the new particles :
        calculFitness(sat);
        //updating the Pbest:
        if (Pbest.compareTo(position) == -1)
            Pbest = position.clone();
    }


    public static double segmoid(double a) {
        return 1 / (1 + Math.exp(-a));
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

}

