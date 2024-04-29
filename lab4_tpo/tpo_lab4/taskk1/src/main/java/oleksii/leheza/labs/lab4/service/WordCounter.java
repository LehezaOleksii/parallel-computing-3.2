package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;
import oleksii.leheza.labs.lab4.entities.Folder;

import java.util.concurrent.ForkJoinPool;

public class WordCounter {
    private final ForkJoinPool forkJoinPool =
            new ForkJoinPool();

    public static String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public Long occurrencesCountInParallel(Folder folder,
                                           String searchedWord) {

        return forkJoinPool.invoke(new FolderSearchTask(folder, searchedWord));
//        long count = 0;
//        for (String line : document.getLines()) {
//            for (String word : wordsIn(line)) {
//                if (searchedWord.equals(word)) {
//                    count++;
//                }
//            }
//        }
//        return count;
    }

    public Long countOccurrencesOnSingleThread(Folder folder,
                                               String searchedWord) {
        long count = 0;
        for (Folder subFolder : folder.getSubFolders()) {
            count = count + countOccurrencesOnSingleThread(
                    subFolder, searchedWord);
        }
        for (Document document : folder.getDocuments()) {
            count = count + occurrencesCountInParallel(folder,
                    searchedWord);
        }
        return count;
    }
}
