package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Produit;
import Services.ProduitService;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;

import java.io.IOException;

public class ManageProduitController {

    @FXML
    private TableView<Produit> produitTable;
    
    @FXML
    private TableColumn<Produit, String> titreColumn;
    
    @FXML
    private TableColumn<Produit, String> descriptionColumn;
    
    @FXML
    private TableColumn<Produit, Void> actionsColumn;

    private final ProduitService produitService = new ProduitService();

    @FXML
    public void initialize() {
        setupTableColumns();
        refreshList();
    }

    private void setupTableColumns() {
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        actionsColumn.setCellFactory(param -> new TableCell<Produit, Void>() {
            private final Button editButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");
            private final HBox buttons = new HBox(10, editButton, deleteButton);

            {
                editButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                editButton.setOnAction(event -> {
                    Produit produit = getTableView().getItems().get(getIndex());
                    try {
                        loadAddProduitScene(produit);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                deleteButton.setOnAction(event -> {
                    Produit produit = getTableView().getItems().get(getIndex());
                    produitService.delete(produit);
                    refreshList();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttons);
                }
            }
        });
    }

    private void refreshList() {
        produitTable.setItems(FXCollections.observableArrayList(produitService.getAll()));
    }

    @FXML
    private void handleAddProduit(ActionEvent event) throws IOException {
        loadAddProduitScene(null);
    }

    @FXML
    private void handleBackToMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wings/pi_java/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void loadAddProduitScene(Produit produit) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wings/pi_java/add_produit.fxml"));
        Parent root = loader.load();
        AddProduitController controller = loader.getController();
        controller.setProduit(produit);
        createStage(root);
    }

    private void createStage(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
