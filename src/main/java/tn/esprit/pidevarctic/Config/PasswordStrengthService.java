package tn.esprit.pidevarctic.Config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import tn.esprit.pidevarctic.entities.PasswordStrength;

import java.util.Properties;

@Service
public class PasswordStrengthService {
    private final StanfordCoreNLP pipeline;

    public PasswordStrengthService() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
        this.pipeline = new StanfordCoreNLP(props);
    }

    public PasswordStrength getPasswordStrength(String password) {
        Annotation document = new Annotation(password);
        pipeline.annotate(document);

        int sentimentScore = getSentimentScore(document);
        int lengthScore = getPasswordLengthScore(password);
        int complexityScore = getPasswordComplexityScore(password);

        int totalScore = sentimentScore + lengthScore + complexityScore;

        if (totalScore >= 12) {
            return PasswordStrength.STRONG;
        } else if (totalScore >= 8) {
            return PasswordStrength.MODERATE;
        } else {
            return PasswordStrength.WEAK;
        }
    }

    private int getSentimentScore(Annotation document) {
        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            int sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class).equals("Positive") ? 4 : 2;
            return sentiment;
        }
        return 0;
    }

    private int getPasswordLengthScore(String password) {
        if (password.length() >= 12) {
            return 4;
        } else if (password.length() >= 8) {
            return 3;
        } else {
            return 1;
        }
    }

    private int getPasswordComplexityScore(String password) {
        int score = 0;
        if (password.matches(".*[A-Z].*")) {
            score += 2;
        }
        if (password.matches(".*[a-z].*")) {
            score += 2;
        }
        if (password.matches(".*\\d.*")) {
            score += 2;
        }
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            score += 2;
        }
        return score;
    }
}
