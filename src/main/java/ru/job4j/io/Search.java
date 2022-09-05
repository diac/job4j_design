package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class Search {

    public static void main(String[] args) throws IOException {
        LaunchParams launchParams = validateArguments(args);
        Path start = launchParams.file.toPath();
        search(start, p -> p.toFile().getName().endsWith("." + launchParams.extension))
                .forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) {
        SearchFiles searcher = new SearchFiles(condition);
        try {
            Files.walkFileTree(root, searcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searcher.getPaths();
    }

    private static LaunchParams validateArguments(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Root folder is null. Usage  ROOT_FOLDER.");
        }
        if (args.length < 2) {
            throw new IllegalArgumentException("File extension is required");
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(String.format("Not directory %s", file.getAbsoluteFile()));
        }
        return new LaunchParams(file, args[1]);
    }

    private record LaunchParams(File file, String extension) {
    }
}
