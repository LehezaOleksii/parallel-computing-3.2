package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;
import oleksii.leheza.labs.lab4.entities.Folder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

public class WordCounter {
    private final ForkJoinPool forkJoinPool =
            new ForkJoinPool();

    private final Map<Integer, Integer> vocabularyOfWordsCount = new ConcurrentHashMap<>();

    private final String regex = "(\\s|\\p{Punct})+";

    public static String[] wordsIn(String line) {
        return line.split("(\\s|\\p{Punct})+");
    }

    public Map<Integer, Integer> occurrencesCountInParallel(Folder folder,
                                                            String searchedWord) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, vocabularyOfWordsCount));
    }

    public Map<Integer, Integer> occurrencesCountInParallel(Folder folder) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, vocabularyOfWordsCount));
    }

    public Map<Integer, Integer> countOccurrencesOnSingleThread(Folder folder) {
        processFolder(folder);
        return vocabularyOfWordsCount;
    }

    private void processFolder(Folder folder) {
        for (Document document : folder.getDocuments()) {
            processDocument(document);
        }
        for (Folder subFolder : folder.getSubFolders()) {
            processFolder(subFolder);
        }
    }

    private void processDocument(Document document) {
        for (String line : document.getLines()) {
            for (String word : line.split(regex)) {
                vocabularyOfWordsCount.merge(word.length(), 1, Integer::sum);
            }
        }
    }
}
