package a23.sim203.tp2.app;


import a23.sim203.tp2.controller.CalculatriceController;
import a23.sim203.tp2.modele.MoteurCalcul;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class GestionAffichage {
    MoteurCalcul moteurCalcul;
    String stringAffiche;
    CalculatriceController calculatriceController;

    public GestionAffichage(CalculatriceController controller) {
        moteurCalcul = new MoteurCalcul();
        stringAffiche = "";
        this.calculatriceController = controller;
    }

    public void setBoutonCaractere(char caractere, Button bouton) {
        actionAssistanceVisuelle(bouton);
        bouton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                stringAffiche += caractere;
                calculatriceController.setStringAffiche(stringAffiche);
            }
        });
    }

    public void setCalculatriceController(CalculatriceController calculatriceController) {
        this.calculatriceController = calculatriceController;
    }

    public void actionBoutonEffacer(Button bouton) {
        bouton.setOnAction(event -> {
            stringAffiche = "";
            calculatriceController.setStringAffiche(stringAffiche);
        });
    }

    public void actionBoutonReculer(Button bouton) {
        bouton.setOnAction(event -> {
            if (stringAffiche.length() >= 1) {
                stringAffiche = stringAffiche.substring(0, stringAffiche.length() - 1);
                calculatriceController.setStringAffiche(stringAffiche);
            }
        });
    }

    public void actionBoutonPlusMinus(Button bouton) {
        actionAssistanceVisuelle(bouton);
        bouton.setOnAction(event -> {
            if (stringAffiche.charAt(0) == '-') {
                stringAffiche = stringAffiche.substring(1);
                calculatriceController.setStringAffiche(stringAffiche);
            } else {
                stringAffiche = "-" + stringAffiche;
                calculatriceController.setStringAffiche(stringAffiche);
            }
        });
    }

    public void actionBoutonEgal(Button bouton) {
        actionAssistanceVisuelle(bouton);
        bouton.setOnAction(event -> {
            double reponse = moteurCalcul.calcule(calculatriceController.getStringAfficheTexte());
            calculatriceController.setStringAffiche(String.valueOf(reponse));
        });
    }

    public void creerAlerteBoutonEgal() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Expression invalide");
        alert.setTitle("Calculateur avancée");
        alert.setContentText("L'expression saisie ne peut être calculée");
        alert.showAndWait();
    }

    public void actionBoutonAjoute(Button bouton) {
        bouton.setOnMouseClicked(event -> {
            try {
                moteurCalcul.ajouteEquation(calculatriceController.getStringAfficheTexte());
                calculatriceController.getListeEquations().getItems().add(moteurCalcul.getEquationMap().get((calculatriceController.getStringAfficheTexte()).substring(0, 2)).toString());
                calculatriceController.getListeEquations().refresh();
                calculatriceController.getListeVariables().getItems().setAll(moteurCalcul.getToutesLesVariables());
            } catch (Exception e) {
                System.out.println("équation invalide");
            }
        });
    }

    public void actionAssistanceVisuelle(Button bouton) {
        bouton.setOnMouseEntered(event -> {
            boolean isSelected = calculatriceController.getMenuItemAssistanceVisuelle().isSelected();
            if (isSelected) {
                bouton.setStyle("-fx-font-size: 35;");
            }
        });

        bouton.setOnMouseExited(event1 -> {
            boolean isSelected = calculatriceController.getMenuItemAssistanceVisuelle().isSelected();
            if (isSelected) {
                bouton.setStyle("-fx-font-size: 25;");
            }
        });
    }

    public void actionBoutonSupprime(Button bouton) {
        bouton.setOnAction(event -> {
            String equationAEffacer = calculatriceController.getListeEquations().getSelectionModel().getSelectedItems().get(0);
            moteurCalcul.getEquationMap().remove(equationAEffacer.substring(0,2));

        });
    }
}
