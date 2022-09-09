package ru.job4j.filesearch;

import ru.job4j.io.ArgsName;
import ru.job4j.io.Search;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.function.Predicate;

public class FileSearch {

    private static final List<String> ALLOWED_SEARCH_TYPES = List.of("mask", "name", "regex");

    public static void main(String[] args) {
        LaunchArgs launchArgs = validateInput(ArgsName.of(args));
        Predicate<Path> condition = path -> true;
        if ("mask".equals(launchArgs.type)) {
            condition = path ->  {
                FileSystem fs = FileSystems.getDefault();
                PathMatcher matcher = fs.getPathMatcher("glob:" + launchArgs.pattern);
                return matcher.matches(path.getFileName());
            };
        } else if ("name".equals(launchArgs.type)) {
            condition = path ->  path.toFile().getName().equals(launchArgs.pattern);
        } else if ("regex".equals(launchArgs.type)) {
            condition = path ->  path.toFile().getName().matches(launchArgs.pattern);
        }
        List<Path> files = Search.search(launchArgs.directory, condition);
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(launchArgs.output.toFile().getAbsolutePath())));
            for (var file : files) {
                out.write(file.toFile().getAbsolutePath());
                out.newLine();
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private record LaunchArgs(Path directory, String pattern, String type, Path output) {
    }

    private static LaunchArgs validateInput(ArgsName argsName) {
        if (argsName.getValues().size() != 4) {
            throw new IllegalArgumentException("Exactly 4 parameters are required");
        }
        for (var param : List.of("d", "n", "t", "o")) {
            if (!argsName.getValues().containsKey(param)) {
                throw new IllegalArgumentException(String.format("-%s parameter is missing", param));
            }
        }
        Path directory = Path.of(argsName.get("d"));
        if (!directory.toFile().exists()) {
            throw new IllegalArgumentException(String.format("Path %s does not exist", directory.getFileName()));
        }
        if (!directory.toFile().isDirectory()) {
            throw new IllegalArgumentException(String.format("%s is not a valid directory", directory.getFileName()));
        }
        String pattern = argsName.get("n");
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Search pattern cannot be empty");
        }
        String type = argsName.get("t");
        if (type.isEmpty()) {
            throw new IllegalArgumentException("Search type cannot be empty");
        }
        if (!ALLOWED_SEARCH_TYPES.contains(type)) {
            throw new IllegalArgumentException(String.format("Unknown search type: %s", type));
        }
        Path output = Path.of(argsName.get("o"));
        try {
            output.toFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!output.toFile().isFile()) {
            throw new IllegalArgumentException(String.format("Invalid output path: %s", output.toFile().getAbsolutePath()));
        }
        return new LaunchArgs(directory, pattern, type, output);
    }
}
