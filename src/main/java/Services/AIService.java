package Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AIService {
    private static final Logger LOGGER = Logger.getLogger(AIService.class.getName());
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static long lastRequestTime = 0;
    private static final long MIN_REQUEST_INTERVAL = 5000; // Augmenté à 5 secondes
    private static int requestCount = 0;
    private static final int MAX_REQUESTS_PER_MINUTE = 3; // Limite à 3 requêtes par minute

    private synchronized void waitForRateLimit() throws Exception {
        if (API_KEY == null || API_KEY.isEmpty()) {
            return;
        }
        
        long currentTime = System.currentTimeMillis();
        long timeSinceLastRequest = currentTime - lastRequestTime;
        
        if (timeSinceLastRequest > 60000) {
            requestCount = 0;
        }
        
        if (requestCount >= MAX_REQUESTS_PER_MINUTE) {
            throw new Exception("Limite de requêtes atteinte. Veuillez attendre une minute avant de réessayer.");
        }
        
        if (timeSinceLastRequest < MIN_REQUEST_INTERVAL) {
            TimeUnit.MILLISECONDS.sleep(MIN_REQUEST_INTERVAL - timeSinceLastRequest);
        }
        
        lastRequestTime = System.currentTimeMillis();
        requestCount++;
    }

    public String generateProductDescription(String imageUrl) {
        try {
            String prompt = "Décris ce produit artisanal en détail en te basant sur son image : " + imageUrl;
            return callAIAPI(prompt);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la génération de la description du produit", e);
            return "Un magnifique produit artisanal unique, fabriqué avec soin et passion. " +
                   "Chaque pièce est le résultat d'un savoir-faire traditionnel transmis de génération en génération.";
        }
    }

    public String genererDescriptionParImage(String imageUrl) {
        return generateProductDescription(imageUrl);
    }

    public String generateCategoryDescription(String libelle) {
        return "Une collection unique d'objets artisanaux soigneusement sélectionnés, " +
               "représentant le meilleur de l'artisanat traditionnel dans la catégorie " + libelle + ". " +
               "Chaque pièce reflète l'authenticité et le savoir-faire des artisans locaux.";
    }

    private String callAIAPI(String prompt) throws Exception {
        waitForRateLimit();
        
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setDoOutput(true);

        String jsonInputString = String.format(
            "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
            URLEncoder.encode(prompt, StandardCharsets.UTF_8.toString())
        );

        try {
            conn.getOutputStream().write(jsonInputString.getBytes());
            
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                String errorMessage = "Erreur API OpenAI (code " + responseCode + ")";
                LOGGER.severe(errorMessage);
                throw new Exception(errorMessage);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'appel à l'API OpenAI", e);
            throw e;
        } finally {
            conn.disconnect();
        }
    }
} 