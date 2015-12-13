package org.rcl.douban;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * Created by Lin on 2015/6/19.
 */
public class User {
    private static Logger logger = LogManager.getLogger();
    private BasicCookieStore basicCookieStore;
    private CloseableHttpClient closeableHttpClient;
    private long liked;
    private long played;

    public User(){
        basicCookieStore = new BasicCookieStore();
        closeableHttpClient = HttpClients.custom()
                .setDefaultCookieStore(basicCookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
    }
    public BasicCookieStore getBasicCookieStore(){
        return this.basicCookieStore;
    }
    public CloseableHttpClient getCloseableHttpClient(){
        return this.closeableHttpClient;
    }
    public long getLiked(){
        return this.liked;
    }

    /**
     * user login
     * @param name username
     * @param passwd password
     * @param captchaDir directory to save captcha picture
     * @return if login succeed return 1, else return -1
     */
    public int login(String name, String passwd, String captchaDir){
        try{
            HttpGet httpGet = new HttpGet("http://douban.fm/j/misc/login_form");
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
            EntityUtils.consume(closeableHttpResponse.getEntity());
            closeableHttpResponse.close();

            String[] captchaId = getCaptchaId(captchaDir);
            if (captchaId==null ){
                // System.out.println("captcha is null or empty");
                logger.entry();
                logger.warn("captcha is null or empty");
                logger.exit();
                return -1;
            }
            HttpUriRequest login = RequestBuilder.post()
                    .setUri("http://douban.fm/j/login")
                    .addParameter("source", "radio")
                    .addParameter("alias", name)
                    .addParameter("form_password", passwd)
                    .addParameter("captcha_solution", captchaId[0])
                    .addParameter("captcha_id", captchaId[1])
                    .addParameter("remember", "on")
                    .addParameter("task", "sync_channel_list")
                    .build();
            closeableHttpResponse = closeableHttpClient.execute(login);
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            String loginInfo = EntityUtils.toString(httpEntity);

            // System.out.println(loginInfo);
            initUserInfo(loginInfo);
            logger.entry();
            logger.info(loginInfo);
            logger.info("liked: "+this.liked);
            logger.exit();

            closeableHttpResponse.close();
        }catch (Exception exception){
            exception.printStackTrace();
            return -1;
        }
        return 1;
    }

    private String[] getCaptchaId(String captchaPath){
        String[] captchaId = new String[2];
        try {
            HttpGet httpGet = new HttpGet("http://douban.fm/j/new_captcha");
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
            String id = EntityUtils.toString(closeableHttpResponse.getEntity());
            closeableHttpResponse.close();

            id = id.substring(1, id.length()-1);
            captchaId[1] = id;
            URIBuilder uriBuilder = new URIBuilder("http://douban.fm/misc/captcha")
                    .addParameter("size", "m")
                    .addParameter("id", id);
            httpGet = new HttpGet(uriBuilder.build());
            // System.out.println(uriBuilder.build().toString()+"\n"+id);
            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            int count;
            byte[] bytes = new byte[1024];
            InputStream inputStream = closeableHttpResponse.getEntity().getContent();
            FileOutputStream fileOutputStream = new FileOutputStream(captchaPath+ File.separator+"captcha.jpg");
            while ((count=inputStream.read(bytes))!=-1 ){
                fileOutputStream.write(bytes, 0, count);
            }
            fileOutputStream.close();
            System.out.println("please input captcha:");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String captcha = bufferedReader.readLine();
            captchaId[0] = captcha;
            bufferedReader.close();
            closeableHttpResponse.close();
        }catch (Exception exception){
            exception.printStackTrace();
            captchaId = null;
        }
        return captchaId;
    }
    private void initUserInfo(String loginInfo){
        Info info = JSON.parseObject(loginInfo, Info.class);
        this.liked = info.getUserInfo().getPlay_record().getLiked();
        this.played = info.getUserInfo().getPlay_record().getPlayed();
    }
}
