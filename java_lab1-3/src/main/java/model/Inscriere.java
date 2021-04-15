package model;

public class Inscriere  {
    //cursa+participnt
    private int id;
    //private int capacMotor;
    private Cursa c;
    private Participant p;

    public Inscriere() {
    }

    public Inscriere(Cursa c, Participant p) {
        this.c = c;
        this.p = p;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getCapacMotor() {
//        return capacMotor;
//    }
//
//    public void setCapacMotor(int capacMotor) {
//        this.capacMotor = capacMotor;
//    }

    public Cursa getC() {
        return c;
    }

    public void setC(Cursa c) {
        this.c = c;
    }

    public Participant getP() {
        return p;
    }

    public void setP(Participant p) {
        this.p = p;
    }

    public Inscriere(int id, Cursa c, Participant p) {
        this.id = id;
       // this.capacMotor = capacMotor;
        this.c = c;
        this.p = p;
    }
}
