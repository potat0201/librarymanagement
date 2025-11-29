package library.service;

import library.exception.FileProcessingException;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogService {

    private final String logPath = "activity_log.txt";

    public void writeLog(String message) throws FileProcessingException {
        try (FileWriter writer = new FileWriter(logPath, true)) {
            writer.write(LocalDateTime.now() + " - " + message + "\n");
        } catch (IOException e) {
            throw new FileProcessingException("Không thể ghi vào file log", e);
        }
    }
}
