package ru.job4j.io;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("Unknown argument: " + key);
        }
        return values.get(key);
    }

    private void parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("At least one argument is required");
        }
        Arrays.stream(args)
                .forEach(argument -> {
                    Map.Entry<String, String> validatedArgument = validateArgument(argument);
                    values.put(validatedArgument.getKey(), validatedArgument.getValue());
                });
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }

    private static Map.Entry<String, String> validateArgument(String argument) {
        if (!argument.startsWith("-")) {
            throw new IllegalArgumentException("Argument key must start with a \"-\" symbol");
        }
        if (!argument.contains("=")) {
            throw new IllegalArgumentException("Missing argument assignment");
        }
        String[] argumentParts = argument.split("=", 2);
        String key = argumentParts[0].replaceFirst("-", "");
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Argument key cannot be empty");
        }
        return Map.entry(key, argumentParts[1]);
    }
}
