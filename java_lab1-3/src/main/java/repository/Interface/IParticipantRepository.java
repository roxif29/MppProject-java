package repository.Interface;

import model.Participant;
import repository.IRepository;

import java.util.List;

public interface IParticipantRepository extends IRepository<Integer, Participant> {
    List<Participant> findByEchipa(String echipa);

}
