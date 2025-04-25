package Services;

public class PaymentService {
    public void processPayment(String email, double montant) {
        // Simuler le traitement du paiement
        System.out.println("Traitement du paiement de " + montant + "€ pour " + email);
        System.out.println("Paiement effectué avec succès!");
    }
} 