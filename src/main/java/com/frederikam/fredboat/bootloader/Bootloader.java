package com.frederikam.fredboat.bootloader;

import java.io.File;
import java.io.IOException;

public class Bootloader {

    public static void main(String[] args) throws IOException, InterruptedException {
        while (true) {
            Process process = boot();
            process.waitFor();
            
            if (process.exitValue() == ExitCodes.EXIT_CODE_UPDATE) {
                update();
            } else {
                break;
            }
        }
    }

    public static Process boot() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("java -jar FredBoat-1.0.jar")
                .inheritIO();
        Process process = pb.start();
        return process;
    }
    
    public static void update(){
        //The main program has already prepared the shaded jar. We just need to replace the jars.
        File oldJar = new File("./FredBoat-1.0.jar");
        oldJar.delete();
        File newJar = new File("./update/target/FredBoat-1.0.jar");
        newJar.renameTo(oldJar);
        
        //Now clean up the workspace
        new File("./update").delete();
    }

}