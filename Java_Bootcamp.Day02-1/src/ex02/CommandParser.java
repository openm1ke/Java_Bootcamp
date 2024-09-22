import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CommandParser {
    private String currentPath = "./";

    public CommandParser(String currentPath) {
        if (!checkFolder(currentPath)) {
            System.out.println("Папка не существует");
            System.exit(1);
        }
        this.currentPath = currentPath;
    }

    private boolean checkFolder(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    public void exec() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(currentPath);
            String command = scanner.nextLine();

            if (command.equals("exit")) {
                break;
            }

            if (command.startsWith("cd")) {
                cdCommand(command);
            }
            if (command.startsWith("ls")) {
                lsCommand(currentPath);
            }

            if (command.startsWith("mv")) {
                mvCommand(command);
            }
        }

    }

    private void mvCommand(String command) {
        String[] args = command.split(" ");
        if (args.length != 3) {
            System.out.println("Неверное количество аргументов");
            return;
        }
        String oldPath = this.currentPath + "/" + args[1];
        String newPath = this.currentPath + "/" + args[2];

        File sourceFile = new File(oldPath);
        File targetFile = new File(newPath);

        if (!sourceFile.exists()) {
            System.out.println("Файл не существует");
            return;
        }

        if (sourceFile.isDirectory()) {
            System.out.println("Нельзя перенести папку");
            return;
        }

        try {
            if (targetFile.exists()) {
                if (targetFile.isDirectory()) {
                    targetFile = new File(targetFile.getAbsolutePath() + "/" + sourceFile.getName());
                }
                Files.deleteIfExists(targetFile.toPath());
            }

            Files.move(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Произошла ошибка при перемещении");
        }

    }

    private void cdCommand(String command) {
        if (command.length() < 2) {
            System.out.println("Необходимо указать путь к папке");
            return;
        }
        String newPath = command.split(" ")[1];

        if (newPath.startsWith("..")) {
            newPath = newPath.substring(2);
            this.currentPath = this.currentPath.substring(0, this.currentPath.lastIndexOf('/'));
        }

        if (newPath.startsWith("/")) {
            newPath = newPath.substring(1);
        }

        StringBuilder tempPath = new StringBuilder(currentPath);

        if (!newPath.isEmpty()) {
            tempPath.append("/").append(newPath);
        }

        if (!checkFolder(tempPath.toString())) {
            System.out.println("Папка не существует");
            return;
        }

        this.currentPath = tempPath.toString();
    }

    private static void lsCommand(String path) {
        File folder = new File(path);

        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(file.getName() + " " + getFolderSize(file) + " KB");
            } else {
                System.out.println(file.getName() + " " + file.length() + " KB");
            }
        }
    }

    private static long getFolderSize(File folder) {
        long size = 0;

        // Проверяем, является ли файл папкой
        if (folder.isDirectory()) {
            // Получаем список файлов и папок в папке
            File[] files = folder.listFiles();

            // Проходимся по всем файлам и папкам и добавляем их размеры
            for (File file : files) {
                size += file.isDirectory() ? getFolderSize(file) : file.length();
            }
        } else {
            // Если файл, то просто добавляем его размер
            size += folder.length();
        }

        return size;
    }
}
