package scopio.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileProcessor {
    private File file;
    
    public FileProcessor(File file) {
        this.file = file;
    }

    protected byte[] readb() {
        try {
            byte[] buffer = new byte[(int)file.length()];
            FileInputStream stream = new FileInputStream(file);
            stream.read(buffer);
            stream.close();
            return buffer;
        } catch (Exception e) {
            return null;
        }
    }

    protected void writeb(byte[] bytes) {
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(bytes);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            // nothing
        }
    }

    protected String read() {
        return new String(readb());
    }

    protected void write(String content) {
        writeb(content.getBytes());
    }

    protected void appendWrite(String content) {
        write(read()+content);
    }

    public File getFile() {
        return file;
    }
}