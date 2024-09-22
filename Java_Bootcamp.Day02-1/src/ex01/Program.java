import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Program <text1> <text2>");
            return;
        }

        String text1File = args[0];
        String text2File = args[1];

        Map<String, Integer> tempDictionary = new HashMap<>();
        createDictionary(text1File, tempDictionary);
        createDictionary(text2File, tempDictionary);

        Vector<Integer> text1vector = createVector(text1File, tempDictionary);
        Vector<Integer> text2vector = createVector(text2File, tempDictionary);

        // System.out.println("Text1 vector: " + text1vector);
        // System.out.println("Text2 vector: " + text2vector);

        int numerator = multiplyVectors(text1vector, text2vector);
        int denominator1 = multiplyVectors(text1vector, text1vector);
        int denominator2 = multiplyVectors(text2vector, text2vector);
        double denominator = Math.sqrt((double) denominator1) * Math.sqrt((double) denominator2);

        System.out.println("Similarity = " + (double) numerator / (double) denominator);

        saveDictionaryToFile(tempDictionary, "dictionary.txt");
    }

    private static void saveDictionaryToFile(Map<String, Integer> dictionary, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            for (String key : dictionary.keySet()) {
                fos.write((key + " ").getBytes());
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
            System.exit(1);
        }
    }

    private static int multiplyVectors(Vector<Integer> vector1, Vector<Integer> vector2) {
        int result = 0;
        for (int i = 0; i < vector1.size(); i++) {
            result += vector1.get(i) * vector2.get(i);
        }
        return result;
    }

    private static Vector<Integer> createVector(String fileName, Map<String, Integer> tempDictionary) {
        Map<String, Integer> sortedDictionary = new TreeMap<>(tempDictionary);
        Vector<Integer> vector = new Vector<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    sortedDictionary.put(word, sortedDictionary.get(word) + 1);
                }
            }

            for (Integer value : sortedDictionary.values()) {
                vector.add(value);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            System.exit(1);
        }

        return vector;
    }

    private static void createDictionary(String fileName, Map<String, Integer> map) {
        String text = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    map.put(word, 0);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            System.exit(1);
        }
    }
}
