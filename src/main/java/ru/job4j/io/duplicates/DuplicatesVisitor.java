package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {

    private final Map<FileProperty, List<Path>> fileMap = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fileProperty = new FileProperty(file.toFile().getName(), file.toFile().length());
        fileMap.putIfAbsent(fileProperty, new ArrayList<>());
        fileMap.get(fileProperty).add(file);
        return super.visitFile(file, attrs);
    }

    public void printDuplicates() {
        fileMap.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .forEach(entry -> {
                    System.out.println(entry.getKey());
                    entry.getValue().forEach(path -> System.out.println("    " + path));
                });
    }
}
