package a23.sim203.tp2;

import a23.sim203.tp2.controller.CalculatriceController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CalculatriceApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatriceApp.class.getResource("calculatrice.fxml"));
        Node root = fxmlLoader.load();
        Scene scene = new Scene((Parent) root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void watchBoutonsCalculatrice(HBox hBoxCentre) {

    }
}
