//package tn.esprit.pidevarctic.Service;
//
//import com.google.api.gax.core.FixedCredentialsProvider;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.language.v1.*;
//import org.springframework.stereotype.Service;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class SentimentAnalysisService {
//    private static final Logger LOGGER = Logger.getLogger(SentimentAnalysisService.class.getName());
//
//    private final LanguageServiceClient languageServiceClient;
//
//    private static final String CREDENTIALS_PATH = "src/main/resources/config/key.json";
//
//    public SentimentAnalysisService() throws IOException {
//        GoogleCredentials credentials = null;
//        try {
//            credentials = GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_PATH))
//                    .createScoped("https://www.googleapis.com/auth/cloud-platform");
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Failed to load Google Cloud credentials", e);
//        }
//
//        if (credentials == null) {
//            throw new IllegalStateException("Google Cloud credentials not loaded");
//        }
//
//        this.languageServiceClient = LanguageServiceClient.create(LanguageServiceSettings.newBuilder()
//                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
//                .build());
//    }
//
//    public float analyzeSentiment(String text) {
//        Document doc = Document.newBuilder()
//                .setContent(text)
//                .setType(Document.Type.PLAIN_TEXT)
//                .build();
//
//        AnalyzeSentimentResponse response = languageServiceClient.analyzeSentiment(doc);
//        Sentiment sentiment = response.getDocumentSentiment();
//
//        return sentiment.getScore();
//    }
//    public String getEmoji(float sentimentScore) {
//        if (sentimentScore >= 0.5) {
//            return "\uD83D\uDC4F✅";
//        } else if (sentimentScore > 0 && sentimentScore < 0.5) {
//            return "🙂";
//        } else if (sentimentScore == 0) {
//            return "😐";
//        } else if (sentimentScore > -0.5 && sentimentScore < 0) {
//            return "😕❌";
//        } else {
//            return "😢";
//        }}
//    public void closeService() {
//        if (languageServiceClient != null) {
//            languageServiceClient.close();
//        }
//    }
//}