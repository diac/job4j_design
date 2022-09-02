package ru.job4j.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class AnalizyTest {

    @Test
    public void whenTestLog1(@TempDir Path tempDir) throws IOException {
        Analizy analizy = new Analizy();
        File source = tempDir.resolve("source.log").toFile();
        try (PrintWriter out = new PrintWriter(source)) {
            out.println("200 10:56:01");
            out.println("500 10:57:01");
            out.println("400 10:58:01");
            out.println("200 10:59:01");
            out.println("500 11:01:02");
            out.println("200 11:02:02");
        }
        File target = tempDir.resolve("target.csv").toFile();
        analizy.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        String rsl;
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            rsl = in.lines()
                    .collect(Collectors.joining("\n"));
        }
        assertThat("10:57:01;10:59:01\n11:01:02;11:02:02").isEqualTo(rsl);
    }

    @Test
    public void whenTestLog2(@TempDir Path tempDir) throws IOException {
        Analizy analizy = new Analizy();
        File source = tempDir.resolve("source.log").toFile();
        try (PrintWriter out = new PrintWriter(source)) {
            out.println("200 10:56:01");
            out.println("500 10:57:01");
            out.println("400 10:58:01");
            out.println("500 10:59:01");
            out.println("400 11:01:02");
            out.println("200 11:02:02");
        }
        File target = tempDir.resolve("target.csv").toFile();
        analizy.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        String rsl;
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            rsl = in.lines()
                    .collect(Collectors.joining("\n"));
        }
        assertThat("10:57:01;11:02:02").isEqualTo(rsl);
    }
}