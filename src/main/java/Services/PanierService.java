package Services;

import models.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class PanierService {
    private ObservableList<Produit> produits;
    private static final double REDUCTION_POURCENTAGE = 0.30; // 30% de réduction
    private static final int SEUIL_REDUCTION = 5; // Seuil pour la réduction

    public PanierService() {
        this.produits = FXCollections.observableArrayList();
    }

    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    public void retirerProduit(Produit produit) {
        produits.remove(produit);
    }

    public ObservableList<Produit> getProduits() {
        return produits;
    }

    public double calculerTotal() {
        double total = produits.stream()
                .mapToDouble(Produit::getPrix)
                .sum();

        if (produits.size() >= SEUIL_REDUCTION) {
            total = total * (1 - REDUCTION_POURCENTAGE);
        }

        return total;
    }

    public boolean appliquerReduction() {
        return produits.size() >= SEUIL_REDUCTION;
    }

    public void viderPanier() {
        produits.clear();
    }

    public int getNombreProduits() {
        return produits.size();
    }

    public double getReduction() {
        if (produits.size() >= SEUIL_REDUCTION) {
            double total = produits.stream()
                    .mapToDouble(Produit::getPrix)
                    .sum();
            return total * REDUCTION_POURCENTAGE;
        }
        return 0;
    }
} 