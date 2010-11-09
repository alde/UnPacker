/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;

/**
 *
 * @author alde
 */
public class Main {

        public static void main(String[] args) throws IOException, InterruptedException {
                LoadConfig lc = new LoadConfig();
                Unpacker unp = new Unpacker();
                lc.doLoad();
                unp.setConfig(lc);
                unp.init();
        }

}
