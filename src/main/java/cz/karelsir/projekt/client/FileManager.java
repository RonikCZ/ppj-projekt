package cz.karelsir.projekt.client;

/**
 * Created by Ronik on 21. 5. 2017.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {

    public static FileManager get() throws IOException {
        return new FileManager();
    }

    private Path targetDir_ = Paths.get("images");

    private FileManager() throws IOException {
        if (!Files.exists(targetDir_)) {
            Files.createDirectories(targetDir_);
        }
    }

    // Private helper method for resolving gift file paths
    private Path getImagePath(String filename) {
        assert (filename != null);
        return targetDir_.resolve(filename + ".jpg");
    }

    public boolean imageExists(String filename) {
        Path source = getImagePath(filename);
        return Files.exists(source);
    }

    public void copyImageData(String filename, OutputStream out) throws IOException {
        Path source = getImagePath(filename);
        if (!Files.exists(source)) {
            throw new FileNotFoundException("Unable to find the referenced file:" + filename);
        }
        Files.copy(source, out);
    }

    public void saveImageData(String filename, InputStream giftData) throws IOException {
        assert (giftData != null);
        Path target = getImagePath(filename);
        Files.copy(giftData, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
