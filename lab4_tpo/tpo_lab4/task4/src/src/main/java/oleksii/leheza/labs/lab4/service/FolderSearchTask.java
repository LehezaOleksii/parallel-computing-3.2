package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;
import oleksii.leheza.labs.lab4.entities.Folder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

class FolderSearchTask extends RecursiveTask<Map<Integer, Integer>> {
    private final Folder folder;
    private final Map<Integer, Integer> vocabularyOfWordsCount;

    public FolderSearchTask(Folder folder, Map<Integer, Integer> vocabularyOfWordsCount) {
        this.folder = folder;
        this.vocabularyOfWordsCount = vocabularyOfWordsCount;
    }

    @Override
    protected Map<Integer, Integer> compute() {
        List<Folder> subFolders = folder.getSubFolders();
        for (Folder subFolder : subFolders) {
            FolderSearchTask task = new FolderSearchTask(subFolder, vocabularyOfWordsCount);
            task.fork();
        }

        List<Document> documents = folder.getDocuments();
        for (Document document : documents) {
            DocumentSearchTask task = new DocumentSearchTask(document, vocabularyOfWordsCount);
            task.fork();
        }
        return vocabularyOfWordsCount;
    }
}