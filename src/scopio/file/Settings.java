package scopio.file;

import java.io.File;
import java.util.HashMap;

public class Settings extends FileProcessor {
    private HashMap<String, HashMap<String, String>> settings = new HashMap<>();

    public Settings() {
        super(new File("../../config/.settings"));
        String raw = read();
        String[] split = raw.split("[$]");
        for(String settingGroup : split) {
            String header = settingGroup.substring(0, settingGroup.indexOf('{'));
            settings.put(header, new HashMap<>());
        }
    }
}