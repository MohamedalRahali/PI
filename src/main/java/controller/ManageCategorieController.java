package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Categorie;
import Services.CategorieService;
import javafx.util.Callback;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.Optional;

public class ManageCategorieController {
    @FXML
    private TableView<Categorie> categorieTable;
    
    @FXML
    private TableColumn<Categorie, String> nomColumn;
    
    @FXML
    private TableColumn<Categorie, String> descriptionColumn;
    
    @FXML
    private TableColumn<Categorie, Void> actionsColumn;

    private final CategorieService categorieService = new CategorieService();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadCategories();
    }

    private void setupTableColumns() {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        actionsColumn.setCellFactory(param -> new TableCell<Categorie, Void>() {
            private final Button editButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");
            private final HBox buttons = new HBox(10, editButton, deleteButton);

            {
                editButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                editButton.setOnAction(event -> {
                    Categorie categorie = getTableView().getItems().get(getIndex());
                    handleEditCategorie(categorie);
                });

                deleteButton.setOnAction(event -> {
                    Categorie categorie = getTableView().getItems().get(getIndex());
                    handleDeleteCategorie(event);
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

    private void loadCategories() {
        List<Categorie> categories = categorieService.getAll();
        categorieTable.getItems().setAll(categories);
    }

    @FXML
    private void handleAddCategorie() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wings/pi_java/add_categorie.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter une catégorie");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleEditCategorie(Categorie categorie) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wings/pi_java/add_categorie.fxml"));
            Parent root = loader.load();
            AddCategorieController controller = loader.getController();
            controller.setCategorie(categorie);
            Stage stage = new Stage();
            stage.setTitle("Modifier la catégorie");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteCategorie(ActionEvent event) {
        Categorie selectedCategorie = categorieTable.getSelectionModel().getSelectedItem();
        if (selectedCategorie == null) {
            showAlert("Erreur", "Veuillez sélectionner une catégorie à supprimer", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation de suppression");
        confirmAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette catégorie ?");
        confirmAlert.setContentText("Les produits associés à cette catégorie deviendront 'sans catégorie'.");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (categorieService.deleteCategorie(selectedCategorie.getId())) {
                loadCategories();
                showAlert("Succès", "Catégorie supprimée avec succès", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Erreur", "Erreur lors de la suppression de la catégorie", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleBackToMain() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/wings/pi_java/main.fxml"));
            Stage stage = (Stage) categorieTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}