package repository.Interface;

import model.Cursa;
import repository.IRepository;

import java.util.List;

public interface ICursaRepository extends IRepository<Integer, Cursa> {
    List<Cursa> findByCapacMotor(int capacMotor);

}
