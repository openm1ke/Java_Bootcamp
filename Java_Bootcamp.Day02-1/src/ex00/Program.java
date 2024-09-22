import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        String signaturesFile = "signatures.txt";
        String resultFile = "result.txt";

        Map<String, String> signaturesMap = readSignatures(signaturesFile);

        // Выводим содержимое Map для проверки
        // for (Map.Entry<String, String> entry : signaturesMap.entrySet()) {
        //     System.out.println("Сигнатура: " + entry.getKey() + ", Тип файла: " + entry.getValue());
        // }

        Scanner scanner = new Scanner(System.in);
        String pathToFile = scanner.nextLine();

        while(!pathToFile.equals("42")) {
            processFile(pathToFile, signaturesMap, resultFile);
            pathToFile = scanner.nextLine();
        }
        
    }

    private static void processFile(String pathToFile, Map<String, String> signaturesMap, String resultFile) {
        // Считываем первые 8 байт файла
        byte[] fileBytes = readBytes(pathToFile, 8);

        if (fileBytes != null) {
            String hexFileBytes = bytesToHex(fileBytes);
            String fileSignature = null;
            for(Map.Entry<String, String> entry : signaturesMap.entrySet()){
                if(hexFileBytes.startsWith(entry.getValue())) {
                    //System.out.println("Сигнатура: " + entry.getKey());
                    fileSignature = entry.getKey();
                    break;
                }
            }

            if(fileSignature != null) {
                System.out.println("PROCESSED");
                writeToResultFile(resultFile, fileSignature);
            } else {
                System.out.println("UNDEFINED");
            }
        } else {
            System.out.println("Ошибка при чтении файла: " + pathToFile);
        }
    }

    private static void writeToResultFile(String resultFile, String fileSignature) {
        try (FileOutputStream fos = new FileOutputStream(resultFile, true)) {
            fos.write((fileSignature + "\n").getBytes());
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private static Map<String, String> readSignatures(String signaturesFile) {    
        Map<String, String> signaturesMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(signaturesFile))) {
            while (scanner.hasNextLine()) {
                String[] signature = scanner.nextLine().split(",");
                signaturesMap.put(signature[0], signature[1].replaceAll("\\s+", ""));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл сигнатур не найден: " + signaturesFile);
        }       

        return signaturesMap;
    }

    // Метод для преобразования массива байт в шестнадцатеричную строку
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }

    private static byte[] readBytes(String filename, int n) {
        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] buffer = new byte[n];
            int bytesRead = fis.read(buffer);
            if (bytesRead == n) {
                return buffer;
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return null;
    }
}