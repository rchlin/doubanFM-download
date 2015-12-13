package org.rcl.util;

import java.io.File;

/**
 * Created by Lin on 2015/6/20.
 */
public class Path {
    /**
     * check whether file or dir exist by the path name
     * @param path file or dir name
     * @return if exist return true
     */
    public static boolean isFileExist(String path){
        if (null==path || path.equals("")){
            return false;
        }
        File file = new File(path);
        return file.exists();
    }

    /**
     * get the number of files under the directory
     * @param dir directory name
     * @return child number
     */
    public static int childNumber(String dir){
        if (!isFileExist(dir)){
            return -1;
        }
        File file = new File(dir);
        String[] list = file.list();
        return list.length;
    }
}
