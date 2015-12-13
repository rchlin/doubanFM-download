package org.rcl.music;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rcl.douban.Playlist;
import org.rcl.douban.Song;
import org.rcl.douban.User;
import org.rcl.util.Path;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

/**
 * Created by Lin on 2015/6/19.
 */
public class DoubanFM {
    private static Logger logger = LogManager.getLogger();
    private CloseableHttpClient closeableHttpClient;
    private BasicCookieStore basicCookieStore;
    private static final int SEED = 1<<20;
    private long liked;
    public static final String FORMAT = ".m4a";
    public DoubanFM(User user){
        this.closeableHttpClient = user.getCloseableHttpClient();
        this.basicCookieStore = user.getBasicCookieStore();
        this.liked = user.getLiked();
    }

    /**
     * save all the songs in the directory
     * @param dir the directory
     * @return the number of songs saved
     */
    public int saveAll(String dir){
        if (!checkDir(dir)){
            return -1;
        }
        logger.entry();
        logger.trace("checkDir return true.");
        int saveCount = 0;
        Download download = new Download();
        Random random = new Random();
        String sid = String.valueOf(random.nextInt(SEED));
        String jsonString = getPlaylist("s", sid);
        logger.trace("sid: "+sid);
        while (true){
            Playlist playlist = JSON.parseObject(jsonString, Playlist.class);
            List<Song> songs = playlist.getSong();
            for (Song song:songs){
                String title = song.getTitle();
                title = validateTitle(title);
                String url = song.getUrl();
                String filePath = dir+ File.separator+title+FORMAT;
                if (Path.isFileExist(filePath)){
                    continue;
                }
                if (download.save(url, filePath)==1){
                    saveCount++;
                }
                /*if (isAll(dir))
                    break;*/
            }
            if (isAll(dir)){
                break;
            }
            sid = String.valueOf(random.nextInt(SEED));
            logger.trace("sid "+sid);
            jsonString = getPlaylist("s", sid);
        }
        logger.exit();
        return saveCount;
    }
    private String getPlaylist(String type, String sid){
        String playlist = "";
        try {
            URIBuilder uriBuilder = new URIBuilder("http://douban.fm/j/mine/playlist")
                    .addParameter("type", type)
                    .addParameter("sid", sid)
                    .addParameter("pt", "")
                    .addParameter("channel", "-3")
                    .addParameter("pb", "128")
                    .addParameter("from", "mainsite")
                    .addParameter("r", "");
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
            playlist = EntityUtils.toString(closeableHttpResponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playlist;
    }
    private boolean isAll(String dir){
        int count = Path.childNumber(dir);
        // in case of bigger count, not just equal
        if (count>=liked){
            return true;
        }else {
            return false;
        }
    }
    private boolean checkDir(String dir){
        File file = new File(dir);
        if (!file.exists()){
            return file.mkdirs();
        }
        return true;
    }
    private String validateTitle(String title){
        if (title.contains(":")){
            String[] strings = title.split(":");
            if (strings[0].length()>0)
                return strings[0];
            else return strings[1];
        }
        return title;
    }
}
