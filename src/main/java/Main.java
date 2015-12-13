import org.rcl.douban.User;
import org.rcl.music.DoubanFM;
import org.rcl.music.Download;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by Lin on 2015/6/19.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("please input username:");
        String name = bufferedReader.readLine();
        System.out.println("please input password:");
        String passwd = bufferedReader.readLine();
        User user = new User();
        if (user.login(name, passwd, "f:")==-1){
            System.out.println("douban login fail!!");
            return;
        }
        DoubanFM doubanFM = new DoubanFM(user);
        int songs = doubanFM.saveAll("F:"+ File.separator+"doubanFM");
        System.out.println(songs+" downloaded.");
    }
}
