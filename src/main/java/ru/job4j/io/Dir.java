package ru.job4j.io;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class Dir {

    public static void main(String[] args) {
        LaunchParams launchParams = validateArguments(args);
        System.out.printf("size : %s%n", launchParams.file.getTotalSpace());
        Arrays.stream(Objects.requireNonNull(launchParams.file.listFiles()))
                .filter(file -> file.getName().endsWith("." + launchParams.extension))
                .forEach(System.out::println);
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
