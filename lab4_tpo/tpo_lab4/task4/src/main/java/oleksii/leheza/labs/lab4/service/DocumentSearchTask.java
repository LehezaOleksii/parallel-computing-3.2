package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

import static oleksii.leheza.labs.lab4.service.WordCounter.wordsIn;

class DocumentSearchTask extends RecursiveTask<Map<String, Integer>> {

    private final Document document;
    private final Map<String, Integer> vocabularyOfWordsCount;
    private final Set<String> keyWords;

    DocumentSearchTask(Document document, Map<String, Integer> vocabularyOfWordsCount, Set<String> keyWords) {
        this.document = document;
        this.vocabularyOfWordsCount = vocabularyOfWordsCount;
        this.keyWords = keyWords;
    }

    @Override
    protected Map<String, Integer> compute() {
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                if (keyWords.contains(word.toLowerCase())) {
                    vocabularyOfWordsCount.merge(word.toLowerCase(), 1, Integer::sum);
                }
            }
        }
        return null;
    }
}
