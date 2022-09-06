package ru.job4j.io;

import java.io.*;
import java.util.*;

public class CSVReader {

    public static void handle(ArgsName argsName) throws IOException, IllegalArgumentException {
        LaunchParams launchParams = validateArguments(argsName);
        List<String> filters = Arrays.asList(launchParams.filter.split(","));
        List<List<String>> result = new ArrayList<>();
        result.add(filters);
        try (var scanner = new Scanner(launchParams.path)) {
            List<String> header;
            if (scanner.hasNextLine()) {
                header = Arrays.asList(scanner.nextLine().split(launchParams.delimiter));
            } else {
                throw new IllegalArgumentException("CSV header is required");
            }
            while (scanner.hasNextLine()) {
                List<String> row = Arrays.asList(scanner.nextLine().split(launchParams.delimiter));
                List<String> resultRow = new ArrayList<>();
                for (var filter : filters) {
                    int index = header.indexOf(filter);
                    if (index > -1) {
                        resultRow.add(row.get(index));
                    }
                }
                result.add(resultRow);
            }
        }
        output(result, launchParams);
    }

    private static void output(List<List<String>> result, LaunchParams launchParams) throws IOException {
        if ("stdout".equals(launchParams.out)) {
            result.forEach(resultRow -> System.out.println(String.join(launchParams.delimiter, resultRow)));
        } else {
            try (var out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(launchParams.out)))) {
                for (List<String> resultRow : result) {
                    out.write(String.join(launchParams.delimiter, resultRow));
                    out.newLine();
                }
            }
        }
    }

    private record LaunchParams(File path, String delimiter, String out, String filter) {
    }

    private static LaunchParams validateArguments(ArgsName argsName) {
        if (argsName.getValues().size() != 4) {
            throw new IllegalArgumentException("Exactly 4 parameters are required");
        }
        if (!argsName.getValues().keySet().containsAll(List.of("path", "delimiter", "out", "filter"))) {
            throw new IllegalArgumentException("Invalid set of parameters. Expected parameters: -path=file.csv -delimiter=;  -out=stdout -filter=name,age");
        }
        File path = new File(argsName.get("path"));
        if (!path.exists()) {
            throw new IllegalArgumentException(String.format("File %s does not exist", path.getName()));
        }
        String delimiter = argsName.get("delimiter");
        if (delimiter.length() != 1) {
            throw new IllegalArgumentException(String.format("Invalid delimiter: %s", delimiter));
        }
        return new LaunchParams(path, delimiter, argsName.get("out"), argsName.get("filter"));
    }
}
