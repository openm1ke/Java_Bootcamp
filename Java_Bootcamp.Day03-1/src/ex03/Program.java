import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.nio.file.*;
import java.io.*;

public class Program {
    public static void main(String[] args) {
        int threadsCount = 3;
        if (args.length == 1) {
            if (args[0].startsWith("--threadsCount=")) {
                String[] split = args[0].split("=");
                try {
                    threadsCount = Integer.parseInt(split[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Введено некорректное значение");
                }
            }
        }

        List<String> links = readLinksFromFile("files_urls.txt");
        DownloadManager manager = new DownloadManager(links);
        try {
            manager.startDownloading(threadsCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readLinksFromFile(String filePath) {
        List<String> links = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                links.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }

}

class DownloadManager {
    private final List<String> downloadLinks = new ArrayList<>();

    public DownloadManager(List<String> downloadLinks) {
        this.downloadLinks.addAll(downloadLinks);
    }

    public void startDownloading(int threadsCount) throws InterruptedException {
        Thread[] downloadThreads = new Thread[threadsCount]; // Три потока, как в вашем примере

        for (int i = 0; i < threadsCount; i++) {
            downloadThreads[i] = new DownloadThread();
            downloadThreads[i].start();
        }

        for (Thread thread : downloadThreads) {
            thread.join();
        }
    }

    private class DownloadThread extends Thread {
        @Override
        public void run() {
            while (true) {
                String link;
                synchronized (downloadLinks) {
                    if (downloadLinks.isEmpty()) {
                        return;
                    }
                    link = downloadLinks.remove(0);
                }
                // Здесь можно добавить логику загрузки файла по ссылке
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " start download file " + link);

                try {
                    String saveDir = "./Downloads";
                    downloadFile(link, saveDir);
                } catch (IOException e) {
                    System.out.println("Ошибка при загрузке: " + e);
                }

                System.out.println(threadName + " finish download file " + link);
            }
        }
    }

    public static void downloadFile(String fileURL, String saveDir) throws IOException {
        Path path = Paths.get(saveDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        URL url = new URL(fileURL);
        InputStream inputStream = url.openStream();
        String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
        FileOutputStream outputStream = new FileOutputStream(saveDir + File.separator + fileName);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
    }
}
