import java.io.File;
import java.io.FileInputStream;

public class Filedata {
    private String name;
    private File file;
    public Filedata(String name) {
        this.name = name;
        this.file = new File(name);
    }

    public File getFile() {
        return file;
    }

}
