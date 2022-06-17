package PSO;

import SAT.SAT;

import java.util.ArrayList;

public class PSO {

    private ArrayList<Particle> particles;
    private Position Gbest;
    private int target;
    private SAT sat;
    private int particlesNbr;
    private int iterationNbr;
    //PSO parameters
    private double c1, c2, w;


    public PSO(int particlesNbr, SAT sat, double c1, double c2, double inertia) {
        this.particlesNbr = particlesNbr;
        this.sat = sat;
        this.c1 = c1;
        this.c2 = c2;
        this.w = inertia;
        particles = new ArrayList<>();
        for (int i = 0; i < particlesNbr; i++) {
            //i suppose that the max is the variable numbers 75
            particles.add(new Particle(sat.getNbrVariables(), (int) (Math.random() * 2)));
            particles.get(i).calculFitness(sat);
            //System.out.println(">>"+particles.get(i).getPosition().getPosition().toString());
            //System.out.println(">>"+particles.get(i).getPosition().getFitness());

            if (Gbest == null || Gbest.compareTo(particles.get(i).getPosition()) == -1)
                Gbest = particles.get(i).getPosition().clone();
        }
    }


    public double calculC(double phe, double w){
        return phe * w;
    }

    public double calculInertia(double phe){
        return 1/(phe-1+Math.sqrt(phe*phe - 2*phe));
    }

    public PSO(int particlesNbr, SAT sat, double phe) {
        this.particlesNbr = particlesNbr;
        this.sat = sat;
        this.w = calculInertia(phe);
        this.c1 = calculC(phe,this.w);
        this.c2 = calculC(phe,this.w);

        particles = new ArrayList<>();
        for (int i = 0; i < particlesNbr; i++) {
            particles.add(new Particle(sat.getNbrVariables(), (int) (Math.random() * 2)));
            particles.get(i).calculFitness(sat);
            if (Gbest == null || Gbest.compareTo(particles.get(i).getPosition()) == -1)
                Gbest = particles.get(i).getPosition().clone();
        }
    }




    public int traitement(int iterationNbr, int target) {
        this.iterationNbr = iterationNbr;
        this.target = target;
        for (int i = 0; i < iterationNbr; i++) {

            for (int j = 0; j < particlesNbr; j++) {
                //updating the velocity
                particles.get(j).calculVelocity(w, c1, c2, Gbest);
                //updating the position
                particles.get(j).updatePosition(sat);
                // System.out.println(">>"+particles.get(j).getPosition().getPosition().toString());
                // System.out.println(""+particles.get(j).getFitness());
                /**
                //this one is the second one and im modifying the Gbest from the Pbest ones
                if (Gbest.compareTo(particles.get(j).getPbest()) == -1)
                    Gbest = particles.get(j).getPosition().clone();
                 **/
                //here is the first one
                //updating Gbest:
                if (Gbest.compareTo(particles.get(j).getPosition()) == -1)
                    Gbest = particles.get(j).getPosition().clone();
                //evaluate the Gbest:
                if (Gbest.getFitness() == target) {

                    return target;
                    /**System.out.println("Le problème SAT est satisfiable ");
                    System.out.println("La solution trouvée");
                    System.out.println("" + Gbest.getPosition().toString());
                    System.out.println("taux de satisfiabilité : " + Gbest.getFitness() + "/325");
                    System.out.println("Le nombre de génération créée :" + iterationNbr);

                     **/
                }
            }

//            System.out.println("the best score untill now is " + Gbest.getPosition().toString());
//            System.out.println("with : " + Gbest.getFitness());

        }


        /**System.out.println("Le problème SAT n'est pas satisfiable ");
        System.out.println("La solution la plus optimale trouvée");
        System.out.println("" + Gbest.getPosition().toString());
        System.out.println("taux de satisfiabilité : " + Gbest.getFitness() + "/325");
        System.out.println("Le nombre de génération créée :" + iterationNbr);
         **/
        return Gbest.getFitness();
    }






    public void traitementBPSO(int iterationNbr, int target, double Vmax) {
        this.iterationNbr = iterationNbr;
        this.target = target;
        for (int i = 0; i < iterationNbr; i++) {

            for (int j = 0; j < particlesNbr; j++) {
                //updating the velocity
                particles.get(j).calculVelocityB(w, c1, c2, Gbest, Vmax);
                //updating the position
                particles.get(j).updatePositionB(sat);
                // System.out.println(">>"+particles.get(j).getPosition().getPosition().toString());
                // System.out.println(""+particles.get(j).getFitness());
                /**
                 //this one is the second one and im modifying the Gbest from the Pbest ones
                 if (Gbest.compareTo(particles.get(j).getPbest()) == -1)
                 Gbest = particles.get(j).getPosition().clone();
                 **/
                //here is the first one
                //updating Gbest:
                if (Gbest.compareTo(particles.get(j).getPosition()) == -1)
                    Gbest = particles.get(j).getPosition().clone();

                //evaluate the Gbest:
                if (Gbest.getFitness() == target) {

                    System.out.println("Le problème SAT est satisfiable ");
                    System.out.println("La solution trouvée");
                    System.out.println("" + Gbest.getPosition().toString());
                    System.out.println("taux de satisfiabilité : " + Gbest.getFitness() + "/325");
                    System.out.println("Le nombre de génération créée :" + iterationNbr);
                    return;
                }
            }

//            System.out.println("the best score untill now is " + Gbest.getPosition().toString());
//            System.out.println("with : " + Gbest.getFitness());

        }

        System.out.println("Le problème SAT n'est pas satisfiable ");
        System.out.println("La solution la plus optimale trouvée");
        System.out.println("" + Gbest.getPosition().toString());
        System.out.println("taux de satisfiabilité : " + Gbest.getFitness() + "/325");
        System.out.println("Le nombre de génération créée :" + iterationNbr);

    }


    public void show(){
        for(Particle p : particles)
            System.out.println(p.getPosition().getPosition().toString());
    }







}
