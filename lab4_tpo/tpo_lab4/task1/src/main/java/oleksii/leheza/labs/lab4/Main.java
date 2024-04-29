package oleksii.leheza.labs.lab4;

import oleksii.leheza.labs.lab4.entities.Folder;
import oleksii.leheza.labs.lab4.service.WordCounter;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(new File("C:\\Users\\Legza Aleksey\\Desktop\\Университет\\3 курс\\ТПО\\labs\\lab4_tpo\\tpo_lab4\\task1\\src\\main\\resources"));
        WordCounter wordCounter = new WordCounter();
        Map<Integer, Integer> wordsVocabulary;
        wordsVocabulary = wordCounter.occurrencesCountInParallel(folder);
        printWordLengthCounts(wordsVocabulary);
    }

    public static void printWordLengthCounts(Map<Integer, Integer> wordLengthCounts) {
        System.out.println("Word Length Counts:");
        for (Map.Entry<Integer, Integer> entry : wordLengthCounts.entrySet()) {
            System.out.println(entry.getKey() + " characters: " + entry.getValue() + " words");
        }
    }
}
