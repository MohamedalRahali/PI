package Services;

import models.Categorie;
import util.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService {

    public List<Categorie> getAll() {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categorie";

        try {
            Connection conn = MyConnection.getInstance().getCnx();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("id"));
                categorie.setLibelle(rs.getString("libelle"));
                categorie.setDescription(rs.getString("description"));
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public void save(Categorie categorie) {
        Connection conn = MyConnection.getInstance().getCnx();

        try {
            if (categorie.getId() == 0) {
                String query = "INSERT INTO categorie (libelle, description) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, categorie.getLibelle());
                stmt.setString(2, categorie.getDescription());
                stmt.executeUpdate();
            } else {
                String query = "UPDATE categorie SET libelle = ?, description = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, categorie.getLibelle());
                stmt.setString(2, categorie.getDescription());
                stmt.setInt(3, categorie.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Categorie categorie) {
        String query = "DELETE FROM categorie WHERE id = ?";
        try {
            Connection conn = MyConnection.getInstance().getCnx();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, categorie.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsedByProducts(int categorieId) {
        String query = "SELECT COUNT(*) FROM produit WHERE categorie_id = ?";
        try (Connection conn = MyConnection.getInstance().getCnx();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, categorieId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategorie(int id) {
        Connection conn = null;
        try {
            conn = MyConnection.getInstance().getCnx();
            conn.setAutoCommit(false); // Désactiver l'auto-commit pour gérer la transaction

            // 1. Mettre à jour les produits associés pour les rendre "sans catégorie"
            String updateProductsSql = "UPDATE produit SET categorie_id = NULL WHERE categorie_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateProductsSql)) {
                updateStmt.setInt(1, id);
                updateStmt.executeUpdate();
            }

            // 2. Supprimer la catégorie
            String deleteCategorieSql = "DELETE FROM categorie WHERE id = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteCategorieSql)) {
                deleteStmt.setInt(1, id);
                int rowsAffected = deleteStmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    conn.commit(); // Valider la transaction
                    return true;
                }
            }
            
            conn.rollback(); // Annuler la transaction en cas d'échec
            return false;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Annuler la transaction en cas d'erreur
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Réactiver l'auto-commit
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
