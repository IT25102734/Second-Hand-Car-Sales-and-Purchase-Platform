package utils;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    // Points to src/main/webapp/data/
    private static final String DATA_PATH = System.getProperty("user.dir")
            + "/src/main/webapp/data/";

    // Write a line to a file (append = true adds, false overwrites)
    public static void writeToFile(String filename, String line, boolean append) throws IOException {
        Path filePath = Paths.get(DATA_PATH + filename);
        Files.createDirectories(filePath.getParent());
        List<String> lines = new ArrayList<>();
        if (append && Files.exists(filePath)) {
            lines.addAll(Files.readAllLines(filePath));
        }
        lines.add(line);
        Files.write(filePath, lines);
    }

    // Read all lines from a file
    public static List<String> readFromFile(String filename) throws IOException {
        Path filePath = Paths.get(DATA_PATH + filename);
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        return Files.readAllLines(filePath);
    }

    // Delete a file (used for updates)
    public static boolean deleteFile(String filename) throws IOException {
        Path filePath = Paths.get(DATA_PATH + filename);
        return Files.deleteIfExists(filePath);
    }
}