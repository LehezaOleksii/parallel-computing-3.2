package oleksii.leheza.labs.lab4;

import oleksii.leheza.labs.lab4.entities.Folder;
import oleksii.leheza.labs.lab4.service.WordCounter;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(new File("C:\\Users\\Legza Aleksey\\Desktop\\Университет\\3 курс\\ТПО\\labs\\lab4_tpo\\tpo_lab4\\taskk1\\src\\main\\resources"));
        WordCounter wordCounter = new WordCounter();
        String searchWorld = "thread";
        int repeatCount = 1;
        long counts = 0;
        long startTime = 0;
        long averTime = 0;
        for (int i = 0; i < repeatCount; i++) {
            startTime = System.currentTimeMillis();
            counts = wordCounter.occurrencesCountInParallel(folder, searchWorld);
            averTime = System.currentTimeMillis() - startTime;
        }
        System.out.println(counts + " words are found. Fork / join search " + averTime / repeatCount + "ms");
        //pool.invoke();
    }
}
