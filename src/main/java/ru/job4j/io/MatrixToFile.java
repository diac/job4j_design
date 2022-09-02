package ru.job4j.io;

import ru.job4j.array.Matrix;

import java.io.FileOutputStream;
import java.io.IOException;

public class MatrixToFile {

    public static void main(String[] args) {
        int[][] matrix = Matrix.multiple(9);
        try (FileOutputStream out = new FileOutputStream("result.txt")) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    out.write(matrix[i][j]);
                }
                out.write(System.lineSeparator().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
