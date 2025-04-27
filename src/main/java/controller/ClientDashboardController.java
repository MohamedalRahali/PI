package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Produit;
import Services.ProduitService;
import Services.PanierService;

public class ClientDashboardController {
    @FXML private TableView<Produit> produitsTable;
    @FXML private TableColumn<Produit, String> titreColumn;
    @FXML private TableColumn<Produit, Double> prixColumn;
    @FXML private TableColumn<Produit, String> descriptionColumn;
    
    @FXML private Label totalLabel;
    @FXML private Label nombreProduitsLabel;
    
    private ProduitService produitService;
    private PanierService panierService;

    @FXML
    public void initialize() {
        produitService = new ProduitService();
        panierService = new PanierService();
        
        setupProduitsTable();
        loadData();
        updatePanierInfo();
    }
    
    private void setupProduitsTable() {
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }
    
    private void loadData() {
        produitsTable.setItems(produitService.getAllProduits());
    }
    
    private void updatePanierInfo() {
        nombreProduitsLabel.setText(String.valueOf(panierService.getNombreProduits()));
        totalLabel.setText(String.format("%.2f €", panierService.getTotal()));
    }
    
    @FXML
    private void handleAjouterAuPanier() {
        Produit selectedProduit = produitsTable.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            panierService.ajouterProduit(selectedProduit);
            updatePanierInfo();
            showAlert("Succès", "Produit ajouté au panier", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Veuillez sélectionner un produit", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void handleVoirPanier() {
        // Navigation vers la vue panier
    }
    
    @FXML
    private void handleDeconnexion() {
        // Navigation vers la page de connexion
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 