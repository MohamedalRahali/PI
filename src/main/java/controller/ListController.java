package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Produit;
import models.Categorie;
import Services.ProduitService;
import Services.CategorieService;

import java.io.IOException;

public class ListController {

    @FXML
    private TableView<Produit> produitTable;

    @FXML
    private TableColumn<Produit, String> titreColumn;
    @FXML
    private TableColumn<Produit, Float> prixColumn;

    @FXML
    private TableView<Categorie> categorieTable;
    @FXML
    private TableColumn<Categorie, String> libelleColumn;

    private final ProduitService produitService = new ProduitService();
    private final CategorieService categorieService = new CategorieService();

    @FXML
    public void initialize() {
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        produitTable.setItems(FXCollections.observableArrayList(produitService.getAll()));

        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        categorieTable.setItems(FXCollections.observableArrayList(categorieService.getAll()));
    }

    @FXML
    private void handleUpdateProduit(ActionEvent event) throws IOException {
        Produit selectedProduit = produitTable.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            loadAddProduitScene(selectedProduit);
        }
    }

    @FXML
    private void handleDeleteProduit(ActionEvent event) {
        Produit selectedProduit = produitTable.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            produitService.delete(selectedProduit);
            produitTable.setItems(FXCollections.observableArrayList(produitService.getAll()));
        }
    }

    @FXML
    private void handleUpdateCategorie(ActionEvent event) throws IOException {
        Categorie selectedCategorie = categorieTable.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            loadAddCategorieScene(selectedCategorie);
        }
    }

    @FXML
    private void handleDeleteCategorie(ActionEvent event) {
        Categorie selectedCategorie = categorieTable.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            categorieService.delete(selectedCategorie);
            categorieTable.setItems(FXCollections.observableArrayList(categorieService.getAll()));
        }
    }

    private void loadAddProduitScene(Produit produit) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wings/pi_java/add_produit.fxml"));
        Parent root = loader.load();
        AddProduitController controller = loader.getController();
        controller.setProduit(produit);
        createStage(root);
    }

    private void loadAddCategorieScene(Categorie categorie) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wings/pi_java/add_categorie.fxml"));
        Parent root = loader.load();
        AddCategorieController controller = loader.getController();
        controller.setCategorie(categorie);
        createStage(root);
    }

    private void createStage(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
