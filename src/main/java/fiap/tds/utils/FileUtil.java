package fiap.tds.utils;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static void saveToFile(String data, String filename) throws Exception {
        Files.writeString(Path.of(filename), data);
    }
}
