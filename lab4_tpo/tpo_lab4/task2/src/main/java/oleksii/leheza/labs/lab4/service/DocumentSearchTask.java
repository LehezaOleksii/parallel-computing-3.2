package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;

import java.util.Map;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

import static oleksii.leheza.labs.lab4.service.WordCounter.wordsIn;

class DocumentSearchTask extends RecursiveAction<Map<Integer, Integer>> {
    private final Document document;
    private final Map<Integer, Integer> vocabularyOfWordsCount;

    DocumentSearchTask(Document document, Map<Integer, Integer> vocabularyOfWordsCount) {
        this.document = document;
        this.vocabularyOfWordsCount = vocabularyOfWordsCount;
    }

    @Override
    protected Map<Integer, Integer> compute() {
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                vocabularyOfWordsCount.merge(word.length(), 1, Integer::sum);
            }
        }
        return null;
    }
}
