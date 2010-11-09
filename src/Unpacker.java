/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alde
 */
class Unpacker {

        private LoadConfig lc;
        private String sourcedir;
        private String targetdir;

        void setConfig(LoadConfig lc) {
                this.lc = lc;
        }

        void init() throws IOException, InterruptedException {
                sourcedir = lc.getSourceDir();
                targetdir = lc.getTargetDir();
                List<String> finisheddownloads = new ArrayList<String>();
                String cmd = "ls " + sourcedir + " | grep .finished";
                BufferedReader br = startCommand(cmd);

                String line;

                while ((line = br.readLine()) != null) {
                        finisheddownloads.add(line);;
                }
                unpack(finisheddownloads);
        }

        private void unpack(List<String> finisheddownloads) throws IOException {
                for (String current : finisheddownloads) {
                        current = current.replace(" ", "\\ ");
                        String rartype = checkRarType(current);
                        String cmd = "unrar -y -r x " + sourcedir + current + "/" + rartype + " " + targetdir;
                        BufferedReader br = startCommand(cmd);
                        String line;
                        int success = 0;
                        System.out.print("\033[37m" + current + ": ");
                        while ((line = br.readLine()) != null) {
                                if (line.contains("Extracting from ")) {
                                        System.out.print("\033[33m#\033[37m");
                                } else if (line.contains("All OK")) {
                                        System.out.print(" \033[32mDone.\033[37m");
                                        success = 1;
                                } else if (line.contains("ERROR")) {
                                        System.out.println("\n\033[31m" + line + "\033[37m");
                                }
                        }

                        System.out.println("\033[37m");
                        if (success != 0) {
                                deleteCurrent(current);
                        }
                }

        }

        private BufferedReader startCommand(String cmd) throws IOException {
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd).redirectErrorStream(true);
                Process process = pb.start();
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                return br;
        }

        private String checkRarType(String current) throws IOException {
                String cmd = "ls " + sourcedir + current + "/*.rar";
                BufferedReader br = startCommand(cmd);
                String line;
                while ((line = br.readLine()) != null) {
                        if (line.contains(".part01.rar")) {
                                return "*.part01.rar";
                        } else {
                                return "*.rar";
                        }
                }
                return "*.rar";
        }

        private void deleteCurrent(String current) throws IOException {
                String cmd = "rm " + sourcedir + current;
                Process process = new ProcessBuilder("bash", "-c", cmd).start();
                System.out.println("\033[37m" + current + " deleted.");
        }
}
