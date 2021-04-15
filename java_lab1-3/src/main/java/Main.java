import model.Cursa;
import model.Inscriere;
import model.Participant;
import repository.DB.InscriereDBRepository;
import repository.DB.ParticipantDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
//        Properties props=new Properties();
//        try {
//            props.load(new FileReader("bd.config"));
//        } catch (IOException e) {
//            System.out.println("Cannot find bd.config "+e);
//        }
//
//        ParticipantDBRepository participantRepo=new ParticipantDBRepository(props);
//        InscriereDBRepository inscriereDBRepository=new InscriereDBRepository(props);
//        participantRepo.save(new Participant(15,"nume1","echipa1",150));
//        inscriereDBRepository.save(new Inscriere(new Cursa(1,1,150),new Participant(9,"nume1","echipa1",150)));
//
//        System.out.println("toti participantii din db");
//        for(Participant part:participantRepo.findAll())
//            System.out.println(part);
//        String echipa="echipa1";
//        System.out.println("Masinile produse de "+echipa);
//        for(Participant part:participantRepo.findByEchipa(echipa))
//            System.out.println(part);


        MainFX.launch(args);

    }
}
