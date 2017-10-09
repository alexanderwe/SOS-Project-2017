package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileService {

    public static String readFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }

}
