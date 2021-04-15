package controller;

import Service.Service;
import Service.ServiceLogin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.Cursa;
import model.Inscriere;
import model.Participant;
import repository.RepositoryException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainController {

    private Service service = null;
    private ServiceLogin serviceLogin = null;
    private Stage stage;
    private ObservableList<Cursa> cursaObservableList = FXCollections.observableArrayList();
    private ObservableList<Participant> participantObservableList = FXCollections.observableArrayList();

    public void setService(Service service, ServiceLogin serviceLogin, Stage stage) {
        this.service = service;
        this.serviceLogin = serviceLogin;
        this.stage = stage;
        initialize();
    }

    private void initialize(){
        setComboBox();
        setData();
        setCurseTable(cursaObservableList);
        setParticipantiTable(participantObservableList);
        setSelectionTable();
    }


    private void setComboBox(){
        List<Integer> lista=new ArrayList<Integer>();
        Iterable<Cursa> curse=service.getAllCurse();
        for(Cursa c:curse){
            lista.add(c.getCapacMotor());
        }
        capacComboBox.getItems().setAll(lista);//todo sa fac functia pt capac motor
        capacComboBox.getSelectionModel().selectFirst();
        categComboBox.getItems().setAll(lista);
        categComboBox.getSelectionModel().selectFirst();
        categComboBox.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                cursaObservableList.setAll((Collection<? extends Cursa>) service.getAllCurse());
            }
        });

    }

    private void setData(){
        cursaObservableList.setAll((Collection<? extends Cursa>) service.getAllCurse());
        participantObservableList.setAll((Collection<? extends Participant>) service.getAllParticipants());
    }

    private void setCurseTable(ObservableList<Cursa> cursaObservableList) {
        idCursaCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idCursaCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        nrPersCursaCol.setCellValueFactory(new PropertyValueFactory<>("NrPers"));
        nrPersCursaCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        capacMotorCursaCol.setCellValueFactory(new PropertyValueFactory<>("CapacMotor"));
        capacMotorCursaCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        curseTableView.getColumns().set(0, idCursaCol);
        curseTableView.getColumns().set(1, nrPersCursaCol);
        curseTableView.getColumns().set(2, capacMotorCursaCol);
        curseTableView.setItems(cursaObservableList);
    }
//id,capac,nume,echipa
    private void setParticipantiTable(ObservableList<Participant> participantObservableList) {
        idEchipaCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idEchipaCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        capacMotorEchipaCol.setCellValueFactory(new PropertyValueFactory<>("CapacMotor"));
        capacMotorEchipaCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numeEchipaCol.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        numeEchipaCol.setCellFactory(TextFieldTableCell.forTableColumn());
        echipaEchipacol.setCellValueFactory(new PropertyValueFactory<>("Echipa"));
        echipaEchipacol.setCellFactory(TextFieldTableCell.forTableColumn());
        echipeTableView.getColumns().set(0, idEchipaCol);
        echipeTableView.getColumns().set(1, capacMotorEchipaCol);
        echipeTableView.getColumns().set(2, numeEchipaCol);
        echipeTableView.getColumns().set(3, echipaEchipacol);
        echipeTableView.setItems(participantObservableList);
    }

    private void setSelectionTable(){
        curseTableView.getSelectionModel().selectedItemProperty().addListener((x, y, z) -> {
            Cursa cursa = (Cursa) z;
            if(cursa != null){
                String id= String.valueOf(cursa.getId());
                idTextField.setText(id);
            }
        });

        echipeTableView.getSelectionModel().selectedItemProperty().addListener((x, y, z) -> {
            Participant participant = (Participant) z;
            if(participant != null){
                numeTextField.setText(participant.getNume());
                echipaTextField.setText(participant.getEchipa());
                capacComboBox.getSelectionModel().select(participant.getCapacMotor());
            }
        });
    }



    @FXML
    Tooltip echipeTooltip;

    @FXML
    TableView curseTableView;
    @FXML
    TableColumn<Cursa, Integer> idCursaCol;
    @FXML
    TableColumn<Cursa, Integer> nrPersCursaCol;
    @FXML
    TableColumn<Cursa, Integer> capacMotorCursaCol;

    @FXML
    TableView echipeTableView;
    @FXML
    TableColumn<Participant, Integer> idEchipaCol;
    @FXML
    TableColumn<Participant, Integer> capacMotorEchipaCol;
    @FXML
    TableColumn<Participant, String> numeEchipaCol;
    @FXML
    TableColumn<Participant, String> echipaEchipacol;

    @FXML
    Button cautareButton;
    @FXML
    Button inscriereButton;
    @FXML
    Button logoutButton;

    @FXML
    TextField echipaSearchTextField;
    @FXML
    TextField idTextField;
    @FXML
    TextField numeTextField;
    @FXML
    TextField echipaTextField;
    @FXML
    ComboBox<Integer> capacComboBox;
    @FXML
    ComboBox<Integer> categComboBox;

    @FXML
    Label echipaLabel;

    public void handleLogoutButton(){
        stage.close();
    }

    public void handleCautaButton(){
        String echipa = echipaSearchTextField.getText();
        participantObservableList.setAll(service.findAllParticipantsByEchipa(echipa));
    }


    public void handleInscriereButton(){
        try {
            int idCursa = Integer.parseInt(idTextField.getText());
            String nume = numeTextField.getText();
            String echipa = echipaTextField.getText();
            Integer capMotor = capacComboBox.getSelectionModel().getSelectedItem();

            Participant p = service.findParticipantByNumeSiEchipaSiCapacMotor(capMotor, nume, echipa);
            Cursa c = service.findCursaByCapacMotor(capMotor);

            Inscriere inscriere = new Inscriere(c, p);

            if (capMotor==c.getCapacMotor()) {
                service.inscriereParticipant(capMotor,nume,echipa);

                c.setNrPers(c.getNrPers() + 1);
                service.updateCursa(idCursa, c);

                cursaObservableList.setAll((Collection<? extends Cursa>) service.getAllCurse());
            } else {
                String error = "Participantul nu se poate inscrie la cursa deoarece: \n -categoria cursei este: " +
                        c.getCapacMotor() +
                        "\n -capacitatea motorului sau este: " +
                        capMotor;
                Alert alert = new Alert(Alert.AlertType.ERROR, error);
                alert.show();
            }
        }
        catch (RepositoryException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Participant deja inscris la cursa");
            alert.show();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Selectati participantul si cursa la care doriti sa-l inscrieti");
            alert.show();
        }
    }

}
