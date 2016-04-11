package com.readnews.chengwei.utils;

import com.readnews.chengwei.bean.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by chengwei on 16/4/11.
 */
public class FileUtils {

    public static String readAssetsFileTitle(InputStream inputStream) throws IOException {
        return readAssetsFile(inputStream,true).getTitle();
    }

    public static News readAssetsFile(InputStream inputStream,boolean isTitle) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        StringBuffer sb = new StringBuffer();
        boolean flag = true;
        String line = "";
        News news = new News();
        while((line = br.readLine())!=null){
            if(flag){
                news.setTitle(line);
                if(isTitle) return news;
            } else {
                sb.append(line);
            }
        }

        news.setBody(sb.toString());
        return news;
    }
}
