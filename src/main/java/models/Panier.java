package models;

import java.util.ArrayList;
import java.util.List;

public class Panier {
    private List<Produit> produits;
    private float total;
    private float reduction;
    private static final float SEUIL_REDUCTION = 5;
    private static final float POURCENTAGE_REDUCTION = 0.30f;

    public Panier() {
        this.produits = new ArrayList<>();
        this.total = 0;
        this.reduction = 0;
    }

    public void ajouterProduit(Produit produit) {
        produits.add(produit);
        calculerTotal();
    }

    public void retirerProduit(Produit produit) {
        produits.remove(produit);
        calculerTotal();
    }

    public void viderPanier() {
        produits.clear();
        total = 0;
        reduction = 0;
    }

    private void calculerTotal() {
        total = 0;
        for (Produit produit : produits) {
            total += produit.getPrix();
        }
        
        // Appliquer la réduction si le nombre de produits dépasse le seuil
        if (produits.size() >= SEUIL_REDUCTION) {
            reduction = total * POURCENTAGE_REDUCTION;
            total -= reduction;
        } else {
            reduction = 0;
        }
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public float getTotal() {
        return total;
    }

    public float getReduction() {
        return reduction;
    }

    public int getNombreProduits() {
        return produits.size();
    }
} 