package scopio.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileHandler {
    private String dir;
    
    public FileHandler(String dir) {
        this.dir = dir;
    }

    public boolean fileExists(String name) {
        return new File(dir, name).exists();
    }

    public byte[] getFileContent(String name) {
        return new FileProcessor(new File(dir, name)).readb();
    }

    public void saveFileToDir(String name, byte[] data) {
        try {
            File f = new File(dir, name);
            boolean created = f.createNewFile();
            if (created) {
                new FileProcessor(f).writeb(data);
            } else {
                System.out.println("File already exists or could not be created.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File[] getFiles() {
        return new File(dir).listFiles();
    }

    public HashMap<String, HashMap<String, Integer>> getFileTable() {
        HashMap<String, HashMap<String, Integer>> s = new HashMap<String, HashMap<String, Integer>>();
        for (File file : getFiles()) {
            s.put(file.getName(), new HashMap<String, Integer>());
        }
        return s;
    }
    
    public String getDir() {
        return dir;
    }

    public static void main(String[] args) {
        System.out.println(new FileHandler("C:\\Users\\mhbau\\OneDrive\\Dokumente").fileExists("sigma.dir"));
    }
}