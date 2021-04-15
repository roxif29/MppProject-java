package Service;

//import repository.DB.AngajatDBRepository;
import repository.Interface.IAngajatRepository;

public class ServiceLogin {
    private IAngajatRepository angajatDBRepository;
   // AngajatDBRepository angajatDBRepository;

    public ServiceLogin(IAngajatRepository angajatDBRepository) {
        this.angajatDBRepository = angajatDBRepository;
    }
//    public ServiceLogin(AngajatDBRepository angajatDBRepository) {
//        this.angajatDBRepository = angajatDBRepository;
//    }

    public boolean login(String user, String pass){
        if( angajatDBRepository.findByUserPass(user,pass)==null)
            return false;
        else return true;

    }
}
