import java.io.File;
import java.util.Scanner;

public class Program {
    String path;
    public static void main(String[] args) {
        String currentPath = "./";
        if(args.length == 1) {
            if (args[0].startsWith("--current-folder")) {
                currentPath = args[0].split("=")[1];
            }
        }

        CommandParser commandParser = new CommandParser(currentPath);
        commandParser.exec();
    }
}
