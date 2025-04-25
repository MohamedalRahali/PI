package Services;

import models.Panier;
import java.util.Date;

public class PaiementService {
    public boolean effectuerPaiement(Panier panier, String numeroCarte, String dateExpiration, String codeSecurite) {
        try {
            // Vérification des informations de la carte
            if (!validerCarte(numeroCarte, dateExpiration, codeSecurite)) {
                return false;
            }

            // Simulation d'un délai de traitement
            Thread.sleep(2000);

            // Enregistrement de la transaction
            enregistrerTransaction(panier);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validerCarte(String numeroCarte, String dateExpiration, String codeSecurite) {
        // Vérification du format du numéro de carte (16 chiffres)
        if (!numeroCarte.matches("\\d{16}")) {
            return false;
        }

        // Vérification du format de la date d'expiration (MM/YY)
        if (!dateExpiration.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            return false;
        }

        // Vérification du code de sécurité (3 ou 4 chiffres)
        if (!codeSecurite.matches("\\d{3,4}")) {
            return false;
        }

        return true;
    }

    private void enregistrerTransaction(Panier panier) {
        // Ici, vous pourriez enregistrer la transaction dans une base de données
        System.out.println("Transaction enregistrée :");
        System.out.println("Date : " + new Date());
        System.out.println("Nombre de produits : " + panier.getNombreProduits());
        System.out.println("Total avant réduction : " + (panier.getTotal() + panier.getReduction()));
        System.out.println("Réduction : " + panier.getReduction());
        System.out.println("Total final : " + panier.getTotal());
    }
} 