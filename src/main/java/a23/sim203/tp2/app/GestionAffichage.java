package a23.sim203.tp2.app;


import a23.sim203.tp2.controller.CalculatriceController;
import a23.sim203.tp2.modele.MoteurCalcul;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import org.mariuszgromada.math.mxparser.Constant;

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
            moteurCalcul.getEquationMap().remove(calculatriceController.getListeEquations().getSelectionModel().getSelectedItem().substring(0, 2));
            calculatriceController.getListeEquations().getItems().remove(calculatriceController.getListeEquations().getSelectionModel().getSelectedItem());
            moteurCalcul.retireVariablesInutiles();
        });
    }

    public void actionToggleBoutons(ToggleButton toggleBoutonLire, ToggleButton toggleButtonVariable, ToggleGroup toggleGroup) {
        toggleBoutonLire.setToggleGroup(toggleGroup);
        toggleButtonVariable.setToggleGroup(toggleGroup);

        toggleBoutonLire.setOnMouseClicked(event -> {
            if (toggleBoutonLire.isSelected()) {
                toggleBoutonLire.setText("écrire");
                toggleButtonVariable.setDisable(true);
                gererEcrire();
            } else {
                toggleBoutonLire.setText("lire");
                gererLire(toggleButtonVariable);
                toggleButtonVariable.setDisable(false);
            }
        });

        toggleButtonVariable.setOnMouseClicked(event -> {
            if (toggleButtonVariable.isSelected()) {
                toggleButtonVariable.setText("valeur");
            } else {
                toggleButtonVariable.setText("variable");
            }
        });
    }

    private void gererLire(ToggleButton toggleButtonVariable) {

        calculatriceController.getListeVariables().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String stringAAjouter = calculatriceController.getListeVariables().getSelectionModel().getSelectedItem().substring(0, 2);
                if (toggleButtonVariable.getText() == "valeur") {
                    stringAAjouter = String.valueOf((moteurCalcul.getVariableValueMap().get(stringAAjouter)).getConstantValue());
                }
                calculatriceController.setStringAffiche(calculatriceController.getStringAfficheTexte() + stringAAjouter);
            }
        });
    }

    private void gererEcrire() {
        calculatriceController.getListeVariables().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String nomVariableAChanger = calculatriceController.getListeVariables().getSelectionModel().getSelectedItem().substring(0, 2);
                try {
                    Constant nouvelleConstante = new Constant(nomVariableAChanger, Double.parseDouble(calculatriceController.getStringAfficheTexte()));
                    moteurCalcul.getVariableValueMap().put(nomVariableAChanger, nouvelleConstante);
                    calculatriceController.getListeVariables().getItems().remove(calculatriceController.getListeVariables().getSelectionModel().getSelectedItem());
                    calculatriceController.getListeVariables().getItems().add(String.valueOf(nouvelleConstante.getConstantName() + " = " + nouvelleConstante.getConstantValue()));
                } catch (Exception e) {
                    System.out.println("veuillez enter une valeur numérique seulement");
                }
            }
        });
    }
}
