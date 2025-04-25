package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Categorie {
    private final StringProperty nom;
    private final StringProperty description;

    public Categorie() {
        this.nom = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
    }

    public Categorie(String nom, String description) {
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }
} 