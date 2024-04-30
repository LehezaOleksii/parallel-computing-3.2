package oleksii.leheza.labs.lab4;

import oleksii.leheza.labs.lab4.entities.Folder;
import oleksii.leheza.labs.lab4.service.WordCounter;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(new File("C:\\Users\\Legza Aleksey\\Desktop\\Университет\\3 курс\\ТПО\\labs\\lab4_tpo\\tpo_lab4\\task1\\src\\main\\resources"));
        WordCounter wordCounter = new WordCounter();
        WordCounter wordCounterSimple = new WordCounter();
        long startTimeParallel;
        long startTimeSimple;
        Set<String> wordsVocabulary;
        startTimeParallel = System.currentTimeMillis();
        wordsVocabulary = wordCounter.occurrencesCountInParallel(folder);
        long resultTimeParallel = System.currentTimeMillis() - startTimeParallel;
        Set<String> vocabularySimple;
        startTimeSimple = System.currentTimeMillis();
        vocabularySimple = wordCounterSimple.countOccurrencesOnSingleThread(folder);
        long resultTimeSimple = System.currentTimeMillis() - startTimeSimple;
        printWordLengthCounts(vocabularySimple);
        System.out.println("===================================");
        printWordLengthCounts(wordsVocabulary);
        System.out.println("Time Parallel: " + resultTimeParallel + " ms" +
                "\nTime Base alg:" + resultTimeSimple + "ms"
                + "\nSpeed up:" + (double) resultTimeSimple / resultTimeParallel);
        System.out.println("are set equals:" + wordsVocabulary.equals(vocabularySimple));
    }

    public static void printWordLengthCounts(Set<String> words) {
        for (String word : words) {
            System.out.println(word);
        }
    }
}
