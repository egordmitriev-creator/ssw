import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Main {

    private static final int NUM_FILES = 5;
    private static final int NUM_THREADS = 10;
    private static final int TOTAL_RECORDS = 50;

    private static final Semaphore fileSemaphore = new Semaphore(NUM_FILES, true);

    public static void main(String[] args) {
        // Создаем файлы
        createFiles();

        // Создаем и запускаем потоки
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(new FileWriterTask(i));
            thread.start();
        }
    }

    //создание файлов
    private static void createFiles() {
        for (int i = 0; i < NUM_FILES; i++) {
            File file = new File("file" + i + ".txt");
            try {
                if (file.createNewFile()) {
                    System.out.println("Файл создан: " + file.getName());
                } else {
                    System.out.println("Файл уже существует: " + file.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class FileWriterTask implements Runnable {
        private final int threadId;

        public FileWriterTask(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            int recordsWritten = 0;

            while (recordsWritten < TOTAL_RECORDS / NUM_THREADS) {
                try {
                    //захватываем семафор
                    fileSemaphore.acquire();

                    //вЫбираем файл для записи
                    int fileIndex = recordsWritten % NUM_FILES;
                    try (FileWriter writer = new FileWriter("file" + fileIndex + ".txt", true)) {
                        writer.write("Thread " + threadId + ": Record " + recordsWritten + "\n");
                        System.out.println("Thread " + threadId + ": Record " + recordsWritten);
                        recordsWritten++; // Увеличиваем счетчик записанных записей

                        Thread.sleep(1500);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    fileSemaphore.release();
                }
            }
        }
    }
}