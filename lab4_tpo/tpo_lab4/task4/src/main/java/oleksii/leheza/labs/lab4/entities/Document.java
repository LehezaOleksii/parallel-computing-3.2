package oleksii.leheza.labs.lab4.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static oleksii.leheza.labs.lab4.service.WordCounter.wordsIn;

public class Document {
    private final List<String> lines;
    private static final Set<String> words = new HashSet<>();

    public Document(List<String> lines) {
        this.lines = lines;
    }

    static Document fromFile(File file)
            throws IOException {
        System.out.println("Document: " + file.getName());
        List<String> lines = new LinkedList<>();
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                words.addAll(Arrays.asList(wordsIn(line)));
                line = reader.readLine();
            }
        }
        return new Document(lines);
    }


    public List<String> getLines() {
        return lines;
    }

    public Set<String> getWords() {
        return words;
    }
}