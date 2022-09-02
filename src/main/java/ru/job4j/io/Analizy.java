package ru.job4j.io;

import java.io.*;
import java.util.Iterator;

public class Analizy {

    public void unavailable(String source, String target) {
        try (
                BufferedReader in = new BufferedReader(new FileReader(source));
                BufferedWriter out = new BufferedWriter(new FileWriter(target))
        ) {
            Iterator<String> inIterator = in.lines().iterator();
            while (inIterator.hasNext()) {
                String line = inIterator.next();
                if (hasErrorCode(line)) {
                    String endLine = "";
                    while (inIterator.hasNext()) {
                        endLine = inIterator.next();
                        if (!hasErrorCode(endLine)) {
                            break;
                        }
                    }
                    out.write(line.split("\\s", 2)[1] + ";" + endLine.split("\\s", 2)[1]);
                    out.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasErrorCode(String line) {
        String code = line.split("\\s", 2)[0];
        return "400".equals(code) || "500".equals(code);
    }

    public static void main(String[] args) {
        Analizy analizy = new Analizy();
        analizy.unavailable("./data/log1.log", "./data/target1.csv");
        analizy.unavailable("./data/log2.log", "./data/target2.csv");
    }
}
