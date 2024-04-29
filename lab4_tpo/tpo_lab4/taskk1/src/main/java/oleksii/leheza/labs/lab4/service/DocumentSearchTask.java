package oleksii.leheza.labs.lab4.service;

import oleksii.leheza.labs.lab4.entities.Document;

import java.util.concurrent.RecursiveTask;

import static oleksii.leheza.labs.lab4.service.WordCounter.wordsIn;

class DocumentSearchTask extends RecursiveTask<Long> {
    private final Document document;
    private final String searchedWord;

    DocumentSearchTask(Document document, String searchedWord) {
        this.document = document;
        this.searchedWord = searchedWord;
    }

    @Override
    protected Long compute() {
        long count = 0;
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                if (searchedWord.equalsIgnoreCase(word)) {
                    count++;
                }
            }
        }
        return count;
    }
}
