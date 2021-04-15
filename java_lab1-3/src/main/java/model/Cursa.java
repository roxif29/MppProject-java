package model;




public class Cursa {
    private int id;
    private int nrPers;
    private int capacMotor;

    public Cursa(int id, int nrPers, int capacMotor) {
        this.id = id;
        this.nrPers = nrPers;
        this.capacMotor = capacMotor;
    }

    public Cursa() {
    }


    public int getNrPers() {
        return nrPers;
    }

    public void setNrPers(int nrPers) {
        this.nrPers = nrPers;
    }

    public int getCapacMotor() {
        return capacMotor;
    }

    public void setCapacMotor(int capacMotor) {
        this.capacMotor = capacMotor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
