package oleksii.leheza.labs.lab4;

import oleksii.leheza.labs.lab4.entities.Folder;
import oleksii.leheza.labs.lab4.service.WordCounter;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(new File("C:\\Users\\Legza Aleksey\\Desktop\\Университет\\3 курс\\ТПО\\labs\\lab4_tpo\\tpo_lab4\\task1\\src\\main\\resources"));
        long startTimeParallel;
        long startTimeSimple;
        String[] words = {"java", "thread", "fork", "forkJoin", "pool", "DeadLock"};
        Set<String> wordsVocabulary = new HashSet<>();
        for (String word : words) {
            wordsVocabulary.add(word.toLowerCase());
        }
        WordCounter wordCounter = new WordCounter(wordsVocabulary);
        WordCounter wordCounterSimple = new WordCounter(wordsVocabulary);

        Map<String, Integer> wordsCount;

        startTimeParallel = System.currentTimeMillis();
        wordsCount = wordCounter.occurrencesCountInParallel(folder);
        long resultTimeParallel = System.currentTimeMillis() - startTimeParallel;
        Map<String, Integer> wordsCountSimple;
        startTimeSimple = System.currentTimeMillis();
        wordsCountSimple = wordCounterSimple.countOccurrencesOnSingleThread(folder);
        long resultTimeSimple = System.currentTimeMillis() - startTimeSimple;
        printWordLengthCounts(wordsCountSimple);
        System.out.println("===================================");
        printWordLengthCounts(wordsCount);
        System.out.println("Time Parallel: " + resultTimeParallel + " ms" +
                "\nTime Base alg:" + resultTimeSimple + "ms"
                + "\nSpeed up:" + (double) resultTimeSimple / resultTimeParallel);
        System.out.println("are set equals:" + wordsCount.equals(wordsCountSimple));
    }

    public static void printWordLengthCounts(Map<String, Integer> wordLengthCounts) {
        for (Map.Entry<String, Integer> entry : wordLengthCounts.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
