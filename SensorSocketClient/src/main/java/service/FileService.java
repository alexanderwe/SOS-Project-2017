package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class for working with files
 */
public class FileService {

    /**
     * Read the content of a file
     *
     * @param filepath
     * @return
     * @throws IOException
     */
    public static String readFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }

}
