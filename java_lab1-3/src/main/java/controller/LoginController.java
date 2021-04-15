package controller;


import Service.Service;
import Service.ServiceLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Angajat;

import java.io.IOException;

public class LoginController {
    private ServiceLogin serviceLogin;
    private Service service;
    private Stage stage;


    public void setService(Service service, ServiceLogin serviceLogin, Stage stage) {
        this.serviceLogin = serviceLogin;
        this.service=service;
        this.stage=stage;

    }

    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Button loginButton;

    public void handleLoginButton() {
        String username = usernameTextField.getText();
        System.out.println(username);
        String password = passwordTextField.getText();
        Angajat a=new Angajat();
        System.out.println(password);
//        try{
//             a=serviceLogin.login(username,password);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        if (serviceLogin.login(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader();
                //D:\MppProiect\mpp-proiect-repository-roxif29\ProiectMPP\src\main\resources\
                //loader.setLocation(getClass().getResource("mainView.fxml"));
                loader.setLocation(getClass().getResource("../mainView.fxml"));
               // loader.setLocation(getClass().getResource("loginView.fxml"))
                AnchorPane layout = loader.load();

                Stage stage = new Stage();
                stage.setTitle(username);
                stage.initModality(Modality.WINDOW_MODAL);

                MainController mainController = loader.getController();
                mainController.setService(service,serviceLogin, stage);

                Scene scene = new Scene(layout, 700, 600);
                stage.setScene(scene);
                stage.show();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username sau parola icorecta!");
            alert.setHeaderText("Incerca din nou!");
            alert.setTitle("Eroare");
            alert.show();
        }
    }
}

