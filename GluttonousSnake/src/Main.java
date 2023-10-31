import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        RandomGenerator.init();
        Properties properties = new Properties();
        FileInputStream settingFile;
        try {
            settingFile = new FileInputStream("./setting.properties");
            properties.load(settingFile);
            Constant.WIDTH = Integer.parseInt(properties.getProperty("width", "30"));
            Constant.HEIGHT = Integer.parseInt(properties.getProperty("height", "30"));
            Constant.SIZE = Integer.parseInt(properties.getProperty("size", "30"));
            Constant.primateLength = Integer.parseInt(properties.getProperty("primateLength", "3"));
            Constant.FPS = Integer.parseInt(properties.getProperty("FPS", "10"));
        } catch (IOException e) {e.printStackTrace();}

        new Drawing();
    }

}