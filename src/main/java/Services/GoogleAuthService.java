package Services;

// import com.google.api.client.auth.oauth2.Credential;
// import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
// import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
// import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
// import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
// import com.google.api.client.http.javanet.NetHttpTransport;
// import com.google.api.client.json.jackson2.JacksonFactory;
// import com.google.api.client.util.store.FileDataStoreFactory;
// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.util.Collections;
// import java.util.List;

public class GoogleAuthService {
    // private static final String APPLICATION_NAME = "Your Application Name";
    // private static final String TOKENS_DIRECTORY_PATH = "tokens";
    // private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/userinfo.email");
    // private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    // private final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    // private final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    // private final int port;

    // public GoogleAuthService() throws IOException {
    //     this(8888);
    // }

    // public GoogleAuthService(int port) throws IOException {
    //     this.port = port;
    // }

    // public Credential getCredentials() throws IOException {
    //     InputStream in = GoogleAuthService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    //     if (in == null) {
    //         throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    //     }
    //     GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    //     GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
    //             HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
    //             .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
    //             .setAccessType("offline")
    //             .build();

    //     LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(port).build();
    //     return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    // }
} 