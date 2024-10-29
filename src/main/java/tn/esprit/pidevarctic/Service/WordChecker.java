package tn.esprit.pidevarctic.Service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class WordChecker {
    private static final String WORD_PATH = "src/main/resources/data/words.json";
    private final Set<String> badWords;

    public WordChecker() {
        this.badWords = loadBadWords();
    }

    private Set<String> loadBadWords() {
        Set<String> badWordsSet = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(WORD_PATH)))) {
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
        if (text == null) {
            return false;
        }
        text = text.toLowerCase();
        for (String badWord : badWords) {
            Pattern pattern = Pattern.compile("\\b" + badWord + "\\b");
            if (pattern.matcher(text).find()) {
                return true;
            }
        }
        return false;
    }
}
