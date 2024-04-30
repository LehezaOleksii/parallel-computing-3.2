package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;
import oleksii.leheza.labs.lab4.entities.Folder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import static java.util.Collections.synchronizedSet;

public class WordCounter {
    private final ForkJoinPool forkJoinPool =
            new ForkJoinPool();

    private final Set<String> vocabularyOfWordsCount = synchronizedSet(new HashSet<>());

    private final String regex = "(\\s|\\p{Punct})+";

    public static String[] wordsIn(String line) {
        return line.split("(\\s|\\p{Punct})+");
    }

    public Set<String> occurrencesCountInParallel(Folder folder) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, vocabularyOfWordsCount));
    }

    public Set<String> countOccurrencesOnSingleThread(Folder folder) {
        processFolder(folder);
        return vocabularyOfWordsCount;
    }

    private void processFolder(Folder folder) {
        List<Document> allDocuments = folder.getAllDocuments();
        for (Document document : folder.getDocuments()) {
            processDocument(document, allDocuments);
        }
        for (Folder subFolder : folder.getSubFolders()) {
            processFolder(subFolder);
        }
    }

    private void processDocument(Document document, List<Document> documents) {
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                for (Document document1 : documents) {
                    if (document1 != document) {
                        if (!document1.getWords().contains(word)) {
                            break;
                        }
                    }
                    vocabularyOfWordsCount.add(word);
                }
            }
        }
    }
}
