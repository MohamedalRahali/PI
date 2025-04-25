package service;

import model.Categorie;
import java.util.ArrayList;
import java.util.List;

public class CategorieService {
    private final List<Categorie> categories;

    public CategorieService() {
        this.categories = new ArrayList<>();
        // Add some sample data
        categories.add(new Categorie("Électronique", "Produits électroniques et accessoires"));
        categories.add(new Categorie("Vêtements", "Vêtements pour hommes, femmes et enfants"));
        categories.add(new Categorie("Alimentation", "Produits alimentaires et boissons"));
    }

    public List<Categorie> getAllCategories() {
        return new ArrayList<>(categories);
    }

    public void addCategorie(Categorie categorie) {
        categories.add(categorie);
    }

    public void updateCategorie(Categorie oldCategorie, Categorie newCategorie) {
        int index = categories.indexOf(oldCategorie);
        if (index != -1) {
            categories.set(index, newCategorie);
        }
    }

    public void deleteCategorie(Categorie categorie) {
        categories.remove(categorie);
    }
} 