package org.rcl.music;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Lin on 2015/6/19.
 */
public class Download {
    private BasicCookieStore cookieStore;
    private CloseableHttpClient httpClient;
    private void init(){
        cookieStore = new BasicCookieStore();
        httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
    }
    public Download(){
        init();
    }

    /**
     * download and save link content
     * @param href the link
     * @param name save name
     * @return if error return -1
     */
    public int save(String href, String name){
        HttpGet httpGet = new HttpGet(href);
        try {
            CloseableHttpResponse response =httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            int count;
            byte[] bytes = new byte[1024];
            InputStream inputStream = entity.getContent();
            FileOutputStream fileOutputStream = new FileOutputStream(name);
            while ((count=inputStream.read(bytes))!=-1){
                 fileOutputStream.write(bytes,0,count);
            }
            fileOutputStream.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public void initWeb(String href){
        HttpGet httpGet = new HttpGet(href);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            response.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
