package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Categorie;
import models.Produit;
import Services.CategorieService;
import Services.ProduitService;

public class ArtisteDashboardController {
    @FXML private TableView<Produit> produitsTable;
    @FXML private TableColumn<Produit, String> titreColumn;
    @FXML private TableColumn<Produit, Double> prixColumn;
    @FXML private TableColumn<Produit, String> descriptionColumn;
    
    @FXML private TableView<Categorie> categoriesTable;
    @FXML private TableColumn<Categorie, String> libelleColumn;
    @FXML private TableColumn<Categorie, String> categorieDescriptionColumn;
    
    @FXML private TextField titreField;
    @FXML private TextField prixField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<Categorie> categorieComboBox;
    
    @FXML private TextField libelleField;
    @FXML private TextArea categorieDescriptionArea;
    
    private ProduitService produitService;
    private CategorieService categorieService;

    @FXML
    public void initialize() {
        produitService = new ProduitService();
        categorieService = new CategorieService();
        
        setupProduitsTable();
        setupCategoriesTable();
        setupComboBoxes();
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
    
    private void setupComboBoxes() {
        categorieComboBox.setItems(categorieService.getAllCategories());
    }
    
    private void loadData() {
        produitsTable.setItems(produitService.getAllProduits());
        categoriesTable.setItems(categorieService.getAllCategories());
    }
    
    @FXML
    private void handleAddProduit() {
        try {
            Produit produit = new Produit();
            produit.setTitre(titreField.getText());
            produit.setPrix(Double.parseDouble(prixField.getText()));
            produit.setDescription(descriptionField.getText());
            produit.setCategorie(categorieComboBox.getValue());
            
            produitService.addProduit(produit);
            loadData();
            clearProduitFields();
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez remplir tous les champs correctement", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void handleAddCategorie() {
        try {
            Categorie categorie = new Categorie();
            categorie.setLibelle(libelleField.getText());
            categorie.setDescription(categorieDescriptionArea.getText());
            
            categorieService.addCategorie(categorie);
            loadData();
            clearCategorieFields();
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez remplir tous les champs correctement", Alert.AlertType.ERROR);
        }
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
    
    private void clearProduitFields() {
        titreField.clear();
        prixField.clear();
        descriptionField.clear();
        categorieComboBox.getSelectionModel().clearSelection();
    }
    
    private void clearCategorieFields() {
        libelleField.clear();
        categorieDescriptionArea.clear();
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 