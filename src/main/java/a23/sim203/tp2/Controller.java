//package formatif5;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.EventListener;
//import java.util.Objects;
//import java.util.ResourceBundle;
//import java.util.logging.Handler;
//
//import formatif5.model.Personne;
//import javafx.beans.property.ReadOnlyObjectWrapper;
//import javafx.beans.value.ObservableValue;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.GridPane;
//import javafx.util.Callback;
//
//public class Controller implements Initializable {
//
//    @FXML
//    private TableView<Personne> tableView;
//
//    @FXML
//    private TableColumn<Personne, String> nomColumn;
//
//    @FXML
//    private TableColumn<Personne, String> prenomColumn;
//
//    @FXML
//    private TableColumn<Personne, Integer> ageColumn;
//
//    // L'interface Initilizable est une alternative à la méthode @FXML initialize()
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        createTableView();
//        createListView();
//
//    }
//
//    @FXML
//    void ajoute(ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("personne.fxml"));
//        try {
//            loader.load();
//        } catch (IOException e) {
//            System.out.println("oups");
//        }
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.getDialogPane().setContent(loader.getRoot());
//        alert.showAndWait();
//
//        TextField nomTextField = (TextField) loader.getNamespace().get("nomTextField");
//        TextField prenomTextField = (TextField) loader.getNamespace().get("prenomTextField");
//        TextField ageTextField = (TextField) loader.getNamespace().get("ageTextField");
//
//        if (!ageTextField.getText().equals("") && nomTextField != null && prenomTextField != null) {
//            tableView.getItems().add(new Personne(Integer.parseInt(ageTextField.getText()), prenomTextField.getText(), nomTextField.getText()));
//        } else {
//            new Alert(Alert.AlertType.ERROR).showAndWait();
//        }
//    }
//
//    @FXML
//    void efface(ActionEvent event) {
//        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
//    }
//
//    @FXML
//    void modifie(ActionEvent event) {
//        if (tableView.getSelectionModel().getSelectedItem() != null) {
//            Personne aChanger = tableView.getSelectionModel().getSelectedItem();
//
//            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("personne.fxml"));
//            try {
//                loader.load();
//            } catch (IOException e) {
//                System.out.println("oups");
//            }
//
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.getDialogPane().setContent(loader.getRoot());
//
//            TextField nomTextField = (TextField) loader.getNamespace().get("nomTextField");
//            TextField prenomTextField = (TextField) loader.getNamespace().get("prenomTextField");
//            TextField ageTextField = (TextField) loader.getNamespace().get("ageTextField");
//
//            nomTextField.setText(aChanger.getNom());
//            prenomTextField.setText(aChanger.getPrenom());
//            ageTextField.setText(String.valueOf(aChanger.getAge()));
//            alert.showAndWait();
//
//            if (nomTextField.getText() != null) {
//                aChanger.setNom(nomTextField.getText());
//            } else {
//                aChanger.setNom("");
//            }
//            if (prenomTextField.getText() != null) {
//                aChanger.setPrenom(prenomTextField.getText());
//            } else {
//                aChanger.setPrenom("");
//            }
//            if (ageTextField.getText() != null) {
//                aChanger.setAge(Integer.parseInt((ageTextField.getText())));
//            } else {
//                aChanger.setAge(-1);
//            }
//
//            tableView.refresh();
//        }
//    }
//
//    public void createTableView() {
//        nomColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personne, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Personne, String> param) {
//                return new ReadOnlyObjectWrapper(param.getValue().getNom());
//            }
//        });
//
//        prenomColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personne, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Personne, String> param) {
//                return new ReadOnlyObjectWrapper(param.getValue().getPrenom());
//            }
//        });
//
//        ageColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Personne, Integer>, ObservableValue<Integer>>() {
//            @Override
//            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Personne, Integer> param) {
//                return new ReadOnlyObjectWrapper(param.getValue().getAge());
//            }
//        });
//        tableView.getItems().add(new Personne(17, "Antoine", "Houde"));
//
//
//        // TableColumn<Personne, String> nomColumn = (TableColumn<Personne, String>)
//        // tableView.getColumns().get(0);// nom
//
//
//        // Conversion de l'objet à la colonne
//
//
//        // On ajoute 3 personne par défaut
//
//
//    }
//
//    private void createListView() {
//        // on règle la fabrique de cellules.
//
//
//    }
//
//}
