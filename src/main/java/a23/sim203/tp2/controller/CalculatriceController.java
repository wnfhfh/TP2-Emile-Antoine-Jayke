package a23.sim203.tp2.controller;

import a23.sim203.tp2.app.GestionAffichage;
import a23.sim203.tp2.modele.MoteurCalcul;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javax.security.auth.callback.Callback;
import java.net.URL;
import java.util.ResourceBundle;

public class CalculatriceController implements Initializable {

    GestionAffichage gestionAffichage;

    @FXML
    private MenuItem menuItemAPropos;

    @FXML
    private Button bouton0;

    @FXML
    private Button bouton1;

    @FXML
    private Button bouton2;

    @FXML
    private Button bouton3;

    @FXML
    private Button bouton4;

    @FXML
    private Button bouton5;

    @FXML
    private Button bouton6;

    @FXML
    private Button bouton7;

    @FXML
    private Button bouton8;

    @FXML
    private Button bouton9;

    @FXML
    private Button boutonAdditionner;

    @FXML
    private Button boutonAjoute;

    @FXML
    private Button boutonDiviser;

    @FXML
    private Button boutonEffacer;

    @FXML
    private Button boutonEgal;

    @FXML
    private Button boutonMultiplier;

    @FXML
    private Button boutonParentheseD;

    @FXML
    private Button boutonParentheseG;

    @FXML
    private Button boutonPlusMinus;

    @FXML
    private Button boutonPoint;

    @FXML
    private Button boutonReculer;

    @FXML
    private Button boutonSoustraire;

    @FXML
    private Button boutonSupprime;

    @FXML
    private Button boutonVariable;

    @FXML
    private ListView<String> listeVariables;

    @FXML
    private ListView<?> listeÉquations;

    @FXML
    private Menu menuAssistance;

    @FXML
    private CheckMenuItem menuItemAssistanceVisuelle;

    @FXML
    private ToggleButton toggleBoutonLire;

    @FXML
    private TextField stringAffiche;    // TODO Changer ID dans SceneBuilder

    @FXML
    void setBoutonsCalculatrice() {
        gestionAffichage.setBoutonCaractere('0', bouton0);
        gestionAffichage.setBoutonCaractere('1', bouton1);
        gestionAffichage.setBoutonCaractere('2', bouton2);
        gestionAffichage.setBoutonCaractere('3', bouton3);
        gestionAffichage.setBoutonCaractere('4', bouton4);
        gestionAffichage.setBoutonCaractere('5', bouton5);
        gestionAffichage.setBoutonCaractere('6', bouton6);
        gestionAffichage.setBoutonCaractere('7', bouton7);
        gestionAffichage.setBoutonCaractere('8', bouton8);
        gestionAffichage.setBoutonCaractere('9', bouton9);
        gestionAffichage.setBoutonCaractere('+', boutonAdditionner);
        gestionAffichage.setBoutonCaractere('-', boutonSoustraire);
        gestionAffichage.setBoutonCaractere('/', boutonDiviser);
        gestionAffichage.setBoutonCaractere('*', boutonMultiplier);
        gestionAffichage.setBoutonCaractere('(', boutonParentheseG);
        gestionAffichage.setBoutonCaractere(')', boutonParentheseD);
        gestionAffichage.setBoutonCaractere('.', boutonPoint);
        gestionAffichage.actionBoutonReculer(boutonReculer);
        gestionAffichage.actionBoutonEffacer(boutonEffacer);
        gestionAffichage.actionBoutonPlusMinus(boutonPlusMinus);
        gestionAffichage.actionBoutonEgal(boutonEgal);
        actionAssistanceVisuelle(menuItemAssistanceVisuelle);
    }

    private void createListeVariables() {
        //TODO setCellFactory()
    }

    private void createMenuAPropos() {
        menuItemAPropos.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cours de programmation 203 en SIM\n" +
                    "Cégep Limoilou A23\n" +
                    "par: Émile Roy, Jayke Gagné et Antoine Houde :)");
            alert.setHeaderText("SIM 203");
            alert.setTitle("Calculateur Avancé");
            alert.show();
        });
    }

    public void setStringAffiche(String stringAAfficher) {
        stringAffiche.setText(stringAAfficher);
    }

    public GestionAffichage getGestionAffichage() {
        return this.gestionAffichage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gestionAffichage = new GestionAffichage();
        setBoutonsCalculatrice();
        createListeVariables();
        createMenuAPropos();
    }


    public void actionAssistanceVisuelle(CheckMenuItem bouton) {
        bouton.setOnAction(event -> {
            boolean isSelected = bouton.isSelected();
            if(isSelected){
                bouton0.setOnMouseEntered(event1 -> {
                    bouton0.setStyle("-fx-font-size: 35;");
                });

                bouton0.setOnMouseExited(event1 -> {
                    bouton0.setStyle("-fx-font-size: 25;");
                });

            }

//            bouton0.setOnMouseEntered(isSelected ? e -> bouton0.setStyle("-fx-font-size: 35") : null);
//            bouton0.setOnMouseExited(isSelected ? e -> bouton0.setStyle("-fx-font-size: 25") : null);
        });
    }
}
