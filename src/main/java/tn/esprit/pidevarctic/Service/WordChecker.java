package tn.esprit.pidevarctic.Service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class WordChecker {

    private static final String WORD_PATH = "data/words.json"; // Adjust path as necessary
    private final Set<String> badWords;

    public WordChecker() {
        this.badWords = loadBadWords();
    }

    private Set<String> loadBadWords() {
        Set<String> badWordsSet = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(WORD_PATH).getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Clean up the line by removing special characters and trimming
                line = line.replaceAll("[^a-zA-Z0-9 ]", "").trim().toLowerCase();
                if (!line.isEmpty()) {
                    badWordsSet.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return badWordsSet;
    }

    public boolean containsBadWords(String text) {
        // Convert the text to lowercase for case-insensitive comparison
        text = text.toLowerCase();
        for (String badWord : badWords) {
            // Using a regex pattern for exact word match
            Pattern pattern = Pattern.compile("\\b" + badWord + "\\b");
            if (pattern.matcher(text).find()) {
                return true;
            }
        }
        return false;
    }
}
