package repository.Interface;

import model.Angajat;
import repository.IRepository;

public interface IAngajatRepository extends IRepository<Integer, Angajat> {
    Angajat findByUserPass(String user, String pass);
}
