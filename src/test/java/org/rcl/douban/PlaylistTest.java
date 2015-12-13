package org.rcl.douban;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lin on 2015/6/19.
 */
public class PlaylistTest {
    @Test
    public void testDecodeJSON(){
        String jsonString = "{\"r\":0,\"is_show_quick_start\":0,\"song\":[{\"album\":\"\\/subject\\/1403514\\/\",\"picture\":\"http:\\/\\/img3.douban.com\\/lpic\\/s1412050.jpg\",\"ssid\":\"ad99\",\"artist\":\"孙楠\",\"url\":\"http:\\/\\/mr3.douban.com\\/201506191855\\/ef93269b179fa6d74be777636be9948e\\/view\\/song\\/small\\/p33924_128k.mp4\",\"company\":\"Sony\",\"title\":\"你快回来\",\"rating_avg\":3.96634,\"length\":266,\"alert_msg\":\"\",\"subtype\":\"\",\"public_time\":\"2005\",\"songlists_count\":9,\"status\":0,\"sid\":\"33924\",\"aid\":\"1403514\",\"sha256\":\"2ee71326add572cc4c4c87e21b4bedf35e7653d6dfc4f8c5ac417bc124443045\",\"kbps\":\"128\",\"albumtitle\":\"孙楠：楠得音乐盛典\",\"like\":1}]}";
        Playlist playlist = JSON.parseObject(jsonString, Playlist.class);
        assertEquals("你快回来", playlist.getSong().get(0).getTitle());
    }
}