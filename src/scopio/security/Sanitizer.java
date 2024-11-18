package scopio.security;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import scopio.file.FileProcessor;

public class Sanitizer extends FileProcessor {
    private List<String> flaggedExtensions = new ArrayList<>();

    public Sanitizer() {
        super(new File("../../config/.flagged"));
        String raw = read();
        String[] split = raw.split("[$]");
        for(String extension : split) {
            flaggedExtensions.add(extension);
        }
    }

    public boolean isExtensionFlagged(String extension) {
        return flaggedExtensions.contains(extension);
    }

    public void addFlaggedExtension(String extension) {
        if(!flaggedExtensions.contains(extension)) {
            flaggedExtensions.add(extension);
        }
    }

    public void removeFlaggedExtension(String extension) {
        flaggedExtensions.remove(extension);
    }

    public List<String> getFlaggedExtensions() {
        return flaggedExtensions;
    }

    public void update() {
        String str = "";
        for(String extension : flaggedExtensions) {
           str+=extension+"$"; 
        }
        write(str);
    }
}