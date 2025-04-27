package models;

import java.util.Date;
import java.io.File;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Produit {
    private SimpleIntegerProperty id;
    private SimpleStringProperty titre;
    private SimpleStringProperty description;
    private SimpleIntegerProperty artisteId;
    private SimpleDoubleProperty prix;
    private SimpleStringProperty statut;
    private Date dateDeCreation; // Utiliser Date pour stocker la date de création
    private Categorie categorie;
    private String image;
    private File imageFile;
    private SimpleIntegerProperty quantite;

    // Constructeurs
    public Produit() {
        this.id = new SimpleIntegerProperty();
        this.titre = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.artisteId = new SimpleIntegerProperty();
        this.prix = new SimpleDoubleProperty();
        this.statut = new SimpleStringProperty();
        this.quantite = new SimpleIntegerProperty(0);
    }

    public Produit(String titre, String description, int artisteId, float prix, String statut, Date dateDeCreation, Categorie categorie, String image) {
        this.titre = new SimpleStringProperty(titre);
        this.description = new SimpleStringProperty(description);
        this.artisteId = new SimpleIntegerProperty(artisteId);
        this.prix = new SimpleDoubleProperty(prix);
        this.statut = new SimpleStringProperty(statut);
        this.dateDeCreation = dateDeCreation;
        this.categorie = categorie;
        this.image = image;
        this.quantite = new SimpleIntegerProperty(0);
    }

    public Produit(int id, String titre, String description, int artisteId, float prix, String statut, Date dateCreation, Categorie categorie, String image) {
        this.id = new SimpleIntegerProperty(id);
        this.titre = new SimpleStringProperty(titre);
        this.description = new SimpleStringProperty(description);
        this.artisteId = new SimpleIntegerProperty(artisteId);
        this.prix = new SimpleDoubleProperty(prix);
        this.statut = new SimpleStringProperty(statut);
        this.dateDeCreation = dateCreation;
        this.categorie = categorie;
        this.image = image;
        this.quantite = new SimpleIntegerProperty(0);
    }

    // Getters et Setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public SimpleIntegerProperty idProperty() { return id; }

    public String getTitre() { return titre.get(); }
    public void setTitre(String titre) { this.titre.set(titre); }
    public SimpleStringProperty titreProperty() { return titre; }

    public String getDescription() { return description.get(); }
    public void setDescription(String description) { this.description.set(description); }
    public SimpleStringProperty descriptionProperty() { return description; }

    public int getArtisteId() { return artisteId.get(); }
    public void setArtisteId(int artisteId) { this.artisteId.set(artisteId); }
    public SimpleIntegerProperty artisteIdProperty() { return artisteId; }

    public double getPrix() { return prix.get(); }
    public void setPrix(double prix) { this.prix.set(prix); }
    public SimpleDoubleProperty prixProperty() { return prix; }

    public String getStatut() { return statut.get(); }
    public void setStatut(String statut) { this.statut.set(statut); }
    public SimpleStringProperty statutProperty() { return statut; }

    public Date getDateDeCreation() { return dateDeCreation; }
    public void setDateDeCreation(Date dateDeCreation) { this.dateDeCreation = dateDeCreation; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public File getImageFile() { return imageFile; }
    public void setImageFile(File imageFile) { this.imageFile = imageFile; }

    public int getQuantite() { return quantite.get(); }
    public void setQuantite(int quantite) { this.quantite.set(quantite); }
    public SimpleIntegerProperty quantiteProperty() { return quantite; }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id.get() +
                ", titre='" + titre.get() + '\'' +
                ", description='" + description.get() + '\'' +
                ", artisteId=" + artisteId.get() +
                ", prix=" + prix.get() +
                ", statut='" + statut.get() + '\'' +
                ", dateDeCreation=" + dateDeCreation +
                ", categorie=" + categorie +
                ", image='" + image + '\'' +
                '}';
    }
}
