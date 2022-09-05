package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void packFiles(List<Path> sources, File startDirectory, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (var source : sources) {
                String absolutePath = source.toString();
                String entryName = absolutePath.substring(startDirectory.getAbsolutePath().length() + 1);
                if (!entryName.equals(target.toString())) {
                    zip.putNextEntry(new ZipEntry(entryName));
                    try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source.toFile()))) {
                        zip.write(out.readAllBytes());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void packSingleFile(File source, String targetFilePath, String exclude, ZipOutputStream zip) throws IOException {
        final String ds = System.getProperty("file.separator");
        if (source.isDirectory()) {
            if (!targetFilePath.isEmpty() && !targetFilePath.endsWith(ds)) {
                targetFilePath += ds;
            }
            zip.putNextEntry(new ZipEntry(targetFilePath));
            zip.closeEntry();
            File[] children = Optional.ofNullable(source.listFiles()).orElse(new File[]{});
            for (File file : children) {
                packSingleFile(file, targetFilePath + file.getName(), exclude, zip);
            }
        } else if (!source.getName().endsWith(exclude)) {
            ZipEntry zipEntry = new ZipEntry(targetFilePath);
            zip.putNextEntry(zipEntry);
            BufferedInputStream out = new BufferedInputStream(new FileInputStream(source));
            zip.write(out.readAllBytes());
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Exactly 3 parameters are required");
        }
        ArgsName namedArgs = ArgsName.of(args);
        validateArgs(namedArgs);
        File directory = new File(namedArgs.get("d"));
        String exclude = namedArgs.get("e");
        File output = new File(namedArgs.get("o"));
        List<Path> sources = Search.search(directory.toPath(), path -> !path.toFile().getName().endsWith(exclude));
        sources.forEach(System.out::println);
        packFiles(sources, directory, output);
    }

    private static void validateArgs(ArgsName args) {
        if (!args.getValues().keySet().containsAll(List.of("d", "e", "o"))) {
            throw new IllegalArgumentException("Invalid parameters. Expected parameters: -d=DIRECTORY -e=EXCLUDE -o=OUTPUT");
        }
        File directory = new File(args.get("d"));
        if (!directory.exists()) {
            throw new IllegalArgumentException(String.format("Directory %s does not exist", directory.getName()));
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(String.format("%s is not a valid directory", directory.getName()));
        }
        String extension = args.get("e");
        if (!extension.startsWith(".")) {
            throw new IllegalArgumentException("The \"EXCLUDE\" parameter must start with \".\"");
        }
        String output = args.get("o");
        if (!output.endsWith(".zip")) {
            throw new IllegalArgumentException("The extension of the output file must be exactly \".zip\"");
        }
    }
}
