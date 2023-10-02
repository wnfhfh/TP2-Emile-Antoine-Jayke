package a23.sim203.tp2;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class CalculatriceApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("calculatrice.fxml"));
        Node root = fxmlLoader.load();
        Scene scene = new Scene((Parent) root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
