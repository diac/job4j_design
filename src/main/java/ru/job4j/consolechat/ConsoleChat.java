package ru.job4j.consolechat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConsoleChat {

    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;
    private boolean mute = false;
    private final List<String> phrases = new ArrayList<>();
    private final List<String> chatLog = new ArrayList<>();

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
        try {
            loadPhrases();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("### Начало чата ###");
        try {
            while (processInput(reader)) {
                System.out.println();
            }
            reader.close();
            saveLog(chatLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean processInput(BufferedReader reader) throws IOException {
        boolean result = true;
        String input;
        input = reader.readLine();
        chatLog.add(input);
        if (List.of(OUT, STOP, CONTINUE).contains(input)) {
            if (input.equals(OUT)) {
                result = false;
            } else if (input.equals(STOP)) {
                System.out.println("Бот молчит");
                mute = true;
            } else {
                System.out.println("Бот отвечает");
                mute = false;
            }
        } else if (!mute) {
            answer();
        }
        return result;
    }

    private void answer() {
        Random rand = new Random();
        String answer = phrases.get(rand.nextInt(phrases.size()));
        chatLog.add(answer);
        System.out.println(answer);
    }

    private List<String> readPhrases() {
        try {
            if (phrases.isEmpty()) {
                loadPhrases();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phrases;
    }

    private void loadPhrases() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(botAnswers)));
        String phrase;
        while ((phrase = reader.readLine()) != null) {
            phrases.add(phrase);
        }
        reader.close();
    }

    private void saveLog(List<String> log) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
        for (var logEntry : log) {
            writer.write(logEntry);
            writer.newLine();
        }
        writer.close();
    }

    public static void main(String[] args) {
        String ds = System.getProperty("file.separator");
        ConsoleChat cc = new ConsoleChat("chat.log", "." + ds + "data" + ds + "bot_answers.txt");
        cc.run();
    }
}
