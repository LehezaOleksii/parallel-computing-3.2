package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;
import oleksii.leheza.labs.lab4.entities.Folder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

class FolderSearchTask extends RecursiveTask<Map<String, Integer>> {
    private final Folder folder;
    private final Map<String, Integer> vocabularyOfWordsCount;
    private final Set<String> keyWords;


    public FolderSearchTask(Folder folder, Map<String, Integer> vocabularyOfWordsCount, Set<String> keyWords) {
        this.folder = folder;
        this.vocabularyOfWordsCount = vocabularyOfWordsCount;
        this.keyWords = keyWords;
    }

    @Override
    protected Map<String, Integer> compute() {
        List<Folder> subFolders = folder.getSubFolders();
        for (Folder subFolder : subFolders) {
            FolderSearchTask task = new FolderSearchTask(subFolder, vocabularyOfWordsCount, keyWords);
            task.invoke();
        }
        List<Document> documents = folder.getDocuments();
        for (Document document : documents) {
            DocumentSearchTask task = new DocumentSearchTask(document, vocabularyOfWordsCount, keyWords);
            task.fork();
        }
        return vocabularyOfWordsCount;
    }
}