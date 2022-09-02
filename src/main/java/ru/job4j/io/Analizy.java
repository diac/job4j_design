package ru.job4j.io;

import java.io.*;

public class Analizy {

    public void unavailable(String source, String target) {
        try (
                BufferedReader in = new BufferedReader(new FileReader(source));
                BufferedWriter out = new BufferedWriter(new FileWriter(target))
        ) {
            StringBuilder result = new StringBuilder();
            int entryCount = 0;
            while (in.ready()) {
                String line = in.readLine();
                String logEntryMessage = line.split("\\s", 2)[1];
                if (hasErrorCode(line)) {
                    if (entryCount++ == 0) {
                        result.append(logEntryMessage);
                    }
                } else if (entryCount > 0) {
                    result.append(";")
                            .append(logEntryMessage)
                            .append("\n");
                    entryCount = 0;
                }
            }
            out.write(result.toString());
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
