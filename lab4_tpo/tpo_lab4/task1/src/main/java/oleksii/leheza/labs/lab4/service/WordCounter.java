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

    public static String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public  Map<Integer, Integer> occurrencesCountInParallel(Folder folder,
                                           String searchedWord) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, vocabularyOfWordsCount));
    }

    public Map<Integer, Integer> occurrencesCountInParallel(Folder folder) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, vocabularyOfWordsCount));
    }

//    public Long countOccurrencesOnSingleThread(Folder folder,
//                                               String searchedWord) {
//        long count = 0;
//        for (Folder subFolder : folder.getSubFolders()) {
//            count = count + countOccurrencesOnSingleThread(
//                    subFolder, searchedWord);
//        }
//        for (Document document : folder.getDocuments()) {
//            count = count + occurrencesCountInParallel(folder,
//                    searchedWord);
//        }
//        return count;
//    }
}
