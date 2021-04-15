package Service;


import model.Cursa;
import model.Inscriere;
import model.Participant;
import repository.Interface.IAngajatRepository;
import repository.Interface.ICursaRepository;
import repository.Interface.IInscriereRepository;
import repository.Interface.IParticipantRepository;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private IAngajatRepository angajatRepository;
    private IParticipantRepository participantRepository;
    private ICursaRepository cursaRepository;
    private IInscriereRepository inscriereRepository;

    public Service(IAngajatRepository angajatRepository, IParticipantRepository participantRepository, ICursaRepository cursaRepository, IInscriereRepository inscriereRepository) {
        this.angajatRepository = angajatRepository;
        this.participantRepository = participantRepository;
        this.cursaRepository = cursaRepository;
        this.inscriereRepository = inscriereRepository;
    }

    public Iterable<Cursa> getAllCurse(){
        return cursaRepository.findAll();
    }
    public Iterable<Participant> getAllParticipants(){
        return participantRepository.findAll();
    }

    public List<Cursa> findByCapacMotor(int capacMotor){
        return cursaRepository.findByCapacMotor(capacMotor);

    }

    public int getNrParticipantiCursa(int idCursa){
     return cursaRepository.findOne(idCursa).getNrPers();
    }

    public List<Participant>findAllParticipantsByEchipa(String echipa)
    {
        Iterable <Participant> participants=participantRepository.findAll();
        List <Participant> result=new ArrayList<>();
        for(Participant p:participants){
            if(p.getEchipa().equals(echipa))
            {
                result.add(p);
            }
        }
        return result;
    }
    public Participant findParticipantByNumeSiEchipaSiCapacMotor(int capacMotor,String nume,String echipa){
        Iterable <Participant> participants=participantRepository.findAll();
        Participant result=new Participant();
        for(Participant p:participants){
            if(p.getEchipa().equals(echipa) && p.getCapacMotor()==capacMotor && p.getNume().equals(nume))
            {
                result.setId(p.getId());
                result.setNume(nume);
                result.setEchipa(echipa);
                result.setCapacMotor(capacMotor);
                return result;
            }
        }
        return null;
    }

    public Cursa findCursaByCapacMotor(int capacMotor){
        Iterable <Cursa> curse=cursaRepository.findAll();
        Cursa result=new Cursa();
        for(Cursa c:curse){
            if(c.getCapacMotor()==capacMotor){
                result.setId(c.getId());
                result.setNrPers(c.getNrPers());
                result.setCapacMotor(capacMotor);
                return result;
            }

        }
        return null;
    }

    public void inscriereParticipant(int capacMotor, String nume, String echipa){

            inscriereRepository.save(new Inscriere(findCursaByCapacMotor(capacMotor),findParticipantByNumeSiEchipaSiCapacMotor(capacMotor,nume,echipa)));

    }

    public void updateCursa(int id, Cursa cursa) {
        cursaRepository.update(id,cursa);
    }


//    public int getNrParticipantiCursa(int idcursa){
//        Iterable <Participant> participants=participantRepository.findAll();
//        int ct=0;
//        Cursa c=cursaRepository.findOne(idcursa);
//        for(Participant p:participants){
//            if(p.()==c.get)
//        }
//
//    }



}








