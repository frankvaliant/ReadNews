package com.readnews.chengwei.model;

import android.content.Context;

import com.readnews.chengwei.bean.News;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by chengwei on 16/4/11.
 */
public interface ReadNewsModel {

    public List<News> getListFile(Context context,String[] path) throws IOException;

    public List<String> getListTitleFile(Context context, String[] path) throws IOException;
}
