package scopio.security;
import java.io.File;
import java.util.List;

import scopio.file.FileProcessor;

public class Sanitizer extends FileProcessor {
    private List<String> blockedFileExtensions;

    public Sanitizer() {
        super(new File("../../config/.fbl"));
    }

    
}