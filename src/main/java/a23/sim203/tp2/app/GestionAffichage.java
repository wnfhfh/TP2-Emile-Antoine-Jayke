package a23.sim203.tp2.app;


import a23.sim203.tp2.controller.CalculatriceController;
import a23.sim203.tp2.modele.MoteurCalcul;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class GestionAffichage {
    MoteurCalcul moteurCalcul;
    String stringAffiche;
    CalculatriceController calculatriceController;

    public GestionAffichage() {
        moteurCalcul = new MoteurCalcul();
        stringAffiche = "";
    }

    public void setBoutonCaractere(char caractere, Button bouton) {
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

    public void actionBoutonEffacer(Button bouton){
        bouton.setOnAction(event -> {
            stringAffiche = "";
            calculatriceController.setStringAffiche(stringAffiche);
        });
    }

    public void actionBoutonReculer(Button bouton){
        bouton.setOnAction(event -> {
            stringAffiche = stringAffiche.substring(0,stringAffiche.length()-1);
            calculatriceController.setStringAffiche(stringAffiche);
        });
    }

}
