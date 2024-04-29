package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;
import oleksii.leheza.labs.lab4.entities.Folder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

class FolderSearchTask extends RecursiveTask<Map<Integer, Integer>> {
    private final Folder folder;
    private final Map<Integer, Integer> vocabularyOfWordsCount;

    public FolderSearchTask(Folder folder, Map<Integer, Integer> vocabularyOfWordsCount) {
        super();
        this.folder = folder;
        this.vocabularyOfWordsCount = vocabularyOfWordsCount;
    }

    @Override
    protected Map<Integer, Integer> compute() {
        List<RecursiveTask< Map<Integer, Integer>>> tasks = new LinkedList<>();
        for (Folder subFolder : folder.getSubFolders()) {
            FolderSearchTask task = new FolderSearchTask(subFolder, vocabularyOfWordsCount);
            tasks.add(task);
            task.fork();
        }
        for (Document document : folder.getDocuments()) {
            DocumentSearchTask task = new DocumentSearchTask(document, vocabularyOfWordsCount);
            tasks.add(task);
            task.fork();
        }

        for (RecursiveTask< Map<Integer, Integer>> task : tasks) {
            task.join();
        }
        return vocabularyOfWordsCount;
    }
}
