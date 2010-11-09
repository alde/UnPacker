/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author alde
 */
public class LoadConfig {

        private final String filename = System.getProperty("user.home")+"/.unpack.rc";
        private String sourcedir;
        private String targetdir;

        public void doLoad() {
                Properties p = new Properties();
                try {
                        p.load(new FileInputStream(filename));
                } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
                sourcedir = p.get("SOURCEDIR").toString();
                targetdir = p.get("TARGETDIR").toString();
                System.out.println(sourcedir + " >> " + targetdir);
        }

        public String getSourceDir() {
                return sourcedir;
        }
        public String getTargetDir() {
                return targetdir;
        }
}
