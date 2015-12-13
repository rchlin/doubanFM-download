package org.rcl.util;

import java.io.File;

/**
 * Independent class with main method.
 * Created by Lin on 2015/6/21.
 */
public class ExtensionHelper {
    public int convertFile(String toFile, String fromFile){
        File fromF = new File(fromFile);
        if (!fromF.exists()){
            return -1;
        }
        File fileT = new File(toFile);
        if (fromF.renameTo(fileT)){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * rename files under the dir directory with "*."+fromFormat to "*."+toFormat
     * @param toFormat to format extension
     * @param fromFormat from format extension
     * @param dir directory
     * @return the number of files converted
     */
    public int convertDir(String toFormat, String fromFormat, String dir){
        File directory = new File(dir);
        if (!directory.exists() || !directory.isDirectory()){
            return -1;
        }
        int count = 0;
        String[] filelist = directory.list();
        for(int i=0; i<filelist.length; i++){
            int digitIndex = filelist[i].lastIndexOf(".");
            String fileFormat = filelist[i].substring(digitIndex+1);
            if (toFormat.equals(fileFormat) || !fromFormat.equals(fileFormat))
                continue;
            String fromFile = dir+File.separator+filelist[i];
            String toFile = dir+File.separator+filelist[i].substring(0, digitIndex)+"."+toFormat;
            if (convertFile(toFile, fromFile)==1)
                count++;
        }
        return count;
    }
    public static void main(String[] args){
        ExtensionHelper helper = new ExtensionHelper();
        int count = helper.convertDir("m4a", "mp3", "f:\\doubanFM");
        System.out.println(count+" files converted!");
    }
}
