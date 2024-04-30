package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

import static oleksii.leheza.labs.lab4.service.WordCounter.wordsIn;

class DocumentSearchTask extends RecursiveTask<Set<String>> {
    private final Document document;
    private final Set<String> vocabularyOfWordsCount;
    private final List<Document> documents;

    DocumentSearchTask(Document document, Set<String> vocabularyOfWordsCount, List<Document> documents) {
        this.document = document;
        this.vocabularyOfWordsCount = vocabularyOfWordsCount;
        this.documents = documents;
    }

    @Override
    protected Set<String> compute() {
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
        return null;
    }
}
