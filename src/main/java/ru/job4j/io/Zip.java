package ru.job4j.io;

import java.io.*;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void packFiles(String startingDirectory, String exclude, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            packSingleFile(new File(startingDirectory), "", exclude, zip);
        } catch (IOException e) {
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
        ArgsName namedArgs = ArgsName.of(args);
        String directory = namedArgs.get("d");
        String exclude = namedArgs.get("e");
        File output = new File(namedArgs.get("o"));
        packFiles(directory, exclude, output);
    }
}
