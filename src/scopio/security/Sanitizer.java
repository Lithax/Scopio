package scopio.security;
import java.io.File;
import java.util.List;

import scopio.file.FileProcessor;

public class Sanitizer extends FileProcessor {
    private List<String> flaggedExtensions;

    public Sanitizer() {
        super(new File("../../config/.fbl"));
        String raw = read();
        raw.split("$");
    }

    public boolean isExtensionFlagged(String extension) {
        return flaggedExtensions.contains(extension);
    }
}