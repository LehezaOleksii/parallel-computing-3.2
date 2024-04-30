package oleksii.leheza.labs.lab4.entities;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Folder {
    private final List<Folder> subFolders;
    private final List<Document> documents;

    public Folder(List<Folder> subFolders, List<Document> documents) {
        this.subFolders = subFolders;
        this.documents = documents;
    }


    public static Folder fromDirectory(File dir) throws IOException {
        List<Document> documents = new LinkedList<>();
        List<Folder> subFolders = new LinkedList<>();
        System.out.println("Folder: " + dir.getName());

        for (File entry : dir.listFiles()) {
            if (entry.isDirectory()) {
                subFolders.add(Folder.fromDirectory(entry));
            } else {
                documents.add(Document.fromFile(entry));
            }
        }
        return new Folder(subFolders, documents);
    }

    public List<Document> getAllDocuments() {
        List<Document> allDocuments = new LinkedList<>(documents);
        for (Folder subFolder : subFolders) {
            allDocuments.addAll(subFolder.getAllDocuments());
        }
        return allDocuments;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public List<Document> getDocuments() {
        return documents;
    }
}