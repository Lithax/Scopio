package scopio.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileProcessor {
    private File file;
    
    public FileProcessor(File file) {
        this.file = file;
    }

    public String read() {
        try {
            String raw = "";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            while(line != null) {
                raw+=line+"\n";
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            return raw;
        } catch (Exception e) {
            return "";
        }
    }

    public void write(String content) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            //nothing
        }
    }

    public File getFile() {
        return file;
    }
}
