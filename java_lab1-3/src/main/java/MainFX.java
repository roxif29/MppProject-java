import Service.Service;
import Service.ServiceLogin;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.DB.AngajatDBRepository;
import repository.DB.CursaDBRepository;
import repository.DB.InscriereDBRepository;
import repository.DB.ParticipantDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainFX extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties properties = new Properties();

        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        AngajatDBRepository angajatRepository = new AngajatDBRepository(properties);
        ParticipantDBRepository participantRepository = new ParticipantDBRepository(properties);
        CursaDBRepository cursaRepository = new CursaDBRepository(properties);
        InscriereDBRepository inscriereRepository = new InscriereDBRepository(properties);
        ServiceLogin serviceLogin=new ServiceLogin(angajatRepository);
        Service service = new Service(angajatRepository, participantRepository, cursaRepository, inscriereRepository);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("loginView.fxml"));
            //loader.setLocation(getClass().getResource("mainView.fxml"));

            AnchorPane layout = loader.load();

            LoginController loginController = loader.getController();
            loginController.setService(service, serviceLogin,primaryStage);

            Scene scene = new Scene(layout, 600, 400);
            primaryStage.setScene(scene);
            //MainController mainController = loader.getController();
            //mainController.setService(service,serviceLogin,primaryStage);

            primaryStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app " + e);
            alert.showAndWait();
        }
    }


}
