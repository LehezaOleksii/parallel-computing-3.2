package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;
import oleksii.leheza.labs.lab4.entities.Folder;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

public class WordCounter {
    private final ForkJoinPool forkJoinPool =
            new ForkJoinPool();

    private final Set<String> keyWords;

    private final Map<String, Integer> vocabularyOfWordsCount = new ConcurrentHashMap<>();

    private final String regex = "(\\s|\\p{Punct})+";

    public WordCounter(Set<String> keyWords) {
        this.keyWords = keyWords;
    }

    public static String[] wordsIn(String line) {
        return line.split("(\\s|\\p{Punct})+");
    }

    public Map<String, Integer> occurrencesCountInParallel(Folder folder) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, vocabularyOfWordsCount, keyWords));
    }

    public Map<String, Integer> countOccurrencesOnSingleThread(Folder folder) {
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
            for (String word : wordsIn(line)) {
                if (keyWords.contains(word.toLowerCase())) {
                    vocabularyOfWordsCount.merge(word.toLowerCase(), 1, Integer::sum);
                }
            }
        }
    }
}
