package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Categorie;
import Services.CategorieService;

import java.io.IOException;

public class ListCategorieController {
    @FXML
    private TableView<Categorie> categorieTable;
    @FXML
    private TableColumn<Categorie, String> libelleColumn;
    @FXML
    private TableColumn<Categorie, String> descriptionColumn;

    private CategorieService categorieService;

    @FXML
    public void initialize() {
        categorieService = new CategorieService();
        
        // Configure table columns
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        // Load categories
        loadCategories();
    }

    private void loadCategories() {
        ObservableList<Categorie> observableList = FXCollections.observableArrayList(categorieService.getAll());
        categorieTable.setItems(observableList);
    }

    @FXML
    private void handleBackToMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wings/pi_java/main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) categorieTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
