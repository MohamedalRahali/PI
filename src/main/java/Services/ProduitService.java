package Services;

import models.Produit;
import models.Categorie;
import util.MyConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.sql.*;
import java.util.ArrayList;

public class ProduitService {

    Connection conn;
    private final AIService aiService = new AIService();

    public ProduitService() {
        conn = MyConnection.getInstance().getCnx();
    }

    // Récupérer tous les produits
    public List<Produit> getAll() {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setTitre(rs.getString("titre"));
                produit.setDescription(rs.getString("description"));
                produit.setArtisteId(rs.getInt("artiste_id"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setStatut(rs.getString("statut"));

                // Récupérer la date de création
                produit.setDateDeCreation(rs.getTimestamp("date_creation")); // Utiliser Timestamp pour la date

                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("categorie_id"));
                produit.setCategorie(categorie);

                produit.setImage(rs.getString("image"));
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produits;
    }


    // Ajouter ou mettre à jour un produit
    public void save(Produit produit) {
        try {
            if (produit.getId() == 0) {
                String query = "INSERT INTO produit (titre, description, artiste_id, prix, statut, categorie_id, image, date_creation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, produit.getTitre());
                stmt.setString(2, produit.getDescription());
                stmt.setInt(3, produit.getArtisteId());
                stmt.setDouble(4, produit.getPrix());
                stmt.setString(5, produit.getStatut());
                stmt.setInt(6, produit.getCategorie().getId());
                stmt.setString(7, produit.getImage());

                // Ajouter la date de création
                stmt.setTimestamp(8, new Timestamp(produit.getDateDeCreation().getTime()));

                stmt.executeUpdate();
            } else {
                String query = "UPDATE produit SET titre = ?, description = ?, artiste_id = ?, prix = ?, statut = ?, categorie_id = ?, image = ?, date_creation = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, produit.getTitre());
                stmt.setString(2, produit.getDescription());
                stmt.setInt(3, produit.getArtisteId());
                stmt.setDouble(4, produit.getPrix());
                stmt.setString(5, produit.getStatut());
                stmt.setInt(6, produit.getCategorie().getId());
                stmt.setString(7, produit.getImage());

                // Ajouter la date de création
                stmt.setTimestamp(8, new Timestamp(produit.getDateDeCreation().getTime()));

                stmt.setInt(9, produit.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Supprimer un produit
    public void delete(Produit produit) {
        String query = "DELETE FROM produit WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, produit.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Récupérer un produit par ID
    public Produit getById(int id) {
        String query = "SELECT * FROM produit WHERE id = ?";
        Produit produit = null;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setTitre(rs.getString("titre"));
                produit.setDescription(rs.getString("description"));
                produit.setArtisteId(rs.getInt("artiste_id"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setStatut(rs.getString("statut"));
                // produit.setDateDeCreation(rs.getDate("date_creation")); // Supprimé

                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("categorie_id"));
                produit.setCategorie(categorie);

                produit.setImage(rs.getString("image"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produit;
    }

    public String generateDescription(String titre) throws Exception {
        try {
            // Attendre 1 seconde entre chaque requête pour éviter les limites
            TimeUnit.SECONDS.sleep(1);
            return aiService.generateProductDescription(titre);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Exception("La génération a été interrompue", e);
        }
    }
}
