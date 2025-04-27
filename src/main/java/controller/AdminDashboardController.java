package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Categorie;
import models.Produit;
import Services.CategorieService;
import Services.ProduitService;

public class AdminDashboardController {
    @FXML private TableView<Produit> produitsTable;
    @FXML private TableColumn<Produit, String> titreColumn;
    @FXML private TableColumn<Produit, Double> prixColumn;
    @FXML private TableColumn<Produit, String> descriptionColumn;
    
    @FXML private TableView<Categorie> categoriesTable;
    @FXML private TableColumn<Categorie, String> libelleColumn;
    @FXML private TableColumn<Categorie, String> categorieDescriptionColumn;
    
    private ProduitService produitService;
    private CategorieService categorieService;

    @FXML
    public void initialize() {
        produitService = new ProduitService();
        categorieService = new CategorieService();
        
        setupProduitsTable();
        setupCategoriesTable();
        loadData();
    }
    
    private void setupProduitsTable() {
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }
    
    private void setupCategoriesTable() {
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        categorieDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }
    
    private void loadData() {
        produitsTable.setItems(produitService.getAllProduits());
        categoriesTable.setItems(categorieService.getAllCategories());
    }
    
    @FXML
    private void handleDeleteProduit() {
        Produit selectedProduit = produitsTable.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            produitService.deleteProduit(selectedProduit.getId());
            loadData();
        }
    }
    
    @FXML
    private void handleDeleteCategorie() {
        Categorie selectedCategorie = categoriesTable.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            categorieService.deleteCategorie(selectedCategorie.getId());
            loadData();
        }
    }
} 