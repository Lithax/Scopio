package scopio.file;

import java.io.File;

public class Settings extends FileProcessor {
    public Settings() {
        super(new File("../../config/.settings"));
    }

    
}