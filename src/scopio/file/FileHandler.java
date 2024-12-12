package scopio.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FileHandler {
    private String dir;
    private Path path;
    
    public FileHandler(String dir) {
        this.dir = dir;
        this.path = Path.of(dir);
    }

    public boolean fileExists(String name) {
        return new File(dir+name).exists();
    }

    public byte[] getFileContent(String name) {
        return new FileProcessor(new File(dir+name)).readb();
    }

    public void saveFileToDir(String name, byte[] data) {
        try {
            File f = new File(dir+name);
            f.createNewFile();
            new FileProcessor(f).writeb(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FileHandler("C:\\Users\\mhbau").saveFileToDir("sigma.txt", "HELLO".getBytes());
    }
}