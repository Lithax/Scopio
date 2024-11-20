package scopio.file;

import java.io.File;
import java.util.HashMap;

public class Settings extends FileProcessor {
    private HashMap<String, HashMap<String, String>> settings = new HashMap<>();

    public Settings() {
        super(new File("../../config/.settings"));
        readFromSettings();
    }

    public void readFromSettings() {
        String raw = read();
        String[] split = raw.split("[$]");
        for(String settingGroup : split) {
            String header = settingGroup.substring(0, settingGroup.indexOf('{')).replaceAll("[$]", "");
            HashMap<String, String> group = new HashMap<>();
            for(String line : settingGroup.substring(settingGroup.indexOf('{')+1, settingGroup.indexOf('}')).split("[;]")) {
                String tag = line.substring(0, line.indexOf(':'));
                String value = line.substring(line.indexOf(':')+1);
                group.put(tag, value);
            }
            settings.put(header, group);
        }
    }

    public String getValueFromSetting(String group, String setting) {
        try {
            return settings.get(group).get(setting);
        } catch (Exception e) {
            return "\0";
        }
    }

    public void setValueFromSetting(String group, String setting, String newValue) {
        try {
            if(settings.containsKey(group) && settings.get(group).containsKey(setting)) {
                HashMap<String, String> hash = new HashMap<>();
                hash.put(setting, newValue);
                settings.put(group, hash);
            }
        } catch (Exception e) {
            //nothing
        }
    }

    public void update() {
        String total = "";
        for(String key : settings.keySet()) {
            String gr = key+"{";
            for(String subkey : settings.get(key).keySet()) {
                gr += subkey+ ":" +settings.get(key).get(subkey)+";";
            }
            gr+="}$";
            total += gr;
        }
        write(total);
    }

    public HashMap<String, HashMap<String, String>> getSettings() {
        return settings;
    }
}