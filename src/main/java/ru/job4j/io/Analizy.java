package ru.job4j.io;

import java.io.*;

public class Analizy {

    public void unavailable(String source, String target) {
        try (
                BufferedReader in = new BufferedReader(new FileReader(source));
                BufferedWriter out = new BufferedWriter(new FileWriter(target))
        ) {
            boolean errorSection = false;
            while (in.ready()) {
                String[] line = in.readLine().split(" ", 2);
                var hasErrorCode = "400".equals(line[0]) || "500".equals(line[0]);
                if (hasErrorCode && !errorSection) {
                    out.append(line[1]);
                    errorSection = true;
                } else if (!hasErrorCode && errorSection) {
                    out.append(";")
                            .append(line[1])
                            .append("\n");
                    errorSection = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analizy analizy = new Analizy();
        analizy.unavailable("./data/log1.log", "./data/target1.csv");
        analizy.unavailable("./data/log2.log", "./data/target2.csv");
    }
}
