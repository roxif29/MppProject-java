package model;
public class Participant{
    private int id;
    private String nume;
    private String echipa;
    private int capacMotor;

    public Participant() {
    }

    public Participant(int id, String nume, String echipa, int capacMotor) {
        this.id = id;
        this.nume = nume;
        this.echipa = echipa;
        this.capacMotor = capacMotor;
    }


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEchipa() {
        return echipa;
    }

    public void setEchipa(String echipa) {
        this.echipa = echipa;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacMotor() {
        return capacMotor;
    }

    public void setCapacMotor(int capacMotor) {
        this.capacMotor = capacMotor;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", echipa='" + echipa + '\'' +
                ", capacMotor=" + capacMotor +
                '}';
    }
}
