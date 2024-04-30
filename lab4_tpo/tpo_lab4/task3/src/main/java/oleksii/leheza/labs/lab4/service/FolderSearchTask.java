package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;
import oleksii.leheza.labs.lab4.entities.Folder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

class FolderSearchTask extends RecursiveTask<Set<String>> {
    private final Folder folder;
    private final Set<String> vocabularyOfWordsCount;

    public FolderSearchTask(Folder folder, Set<String>  vocabularyOfWordsCount) {
        this.folder = folder;
        this.vocabularyOfWordsCount = vocabularyOfWordsCount;
    }

    @Override
    protected Set<String>  compute() {
        List<Folder> subFolders = folder.getSubFolders();
        for (Folder subFolder : subFolders) {
            FolderSearchTask task = new FolderSearchTask(subFolder, vocabularyOfWordsCount);
            task.invoke();
        }

        List<Document> allDocuments = folder.getAllDocuments();
        List<Document> documents = folder.getDocuments();
        for (Document document : documents) {
            DocumentSearchTask task = new DocumentSearchTask(document, vocabularyOfWordsCount, allDocuments);
            task.fork();
        }
        return vocabularyOfWordsCount;
    }
}