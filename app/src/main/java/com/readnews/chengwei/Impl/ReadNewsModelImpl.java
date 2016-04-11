package com.readnews.chengwei.Impl;

import android.content.Context;

import com.readnews.chengwei.bean.News;
import com.readnews.chengwei.model.ReadNewsModel;
import com.readnews.chengwei.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengwei on 16/4/11.
 */
public class ReadNewsModelImpl implements ReadNewsModel{

    @Override
    public List<News> getListFile(Context context,String[] path) throws IOException {
        List<News> list = new ArrayList<News>();
        for(int i = 0;i<path.length;i++){
            list.add(FileUtils.readAssetsFile(context.getAssets().open(path[i]),false)) ;
        }
        return list;
    }

    @Override
    public List<String> getListTitleFile(Context context,String[] path) throws IOException {
        List<String> list = new ArrayList<String>();
        for(int i = 0;i<path.length;i++){
           list.add(FileUtils.readAssetsFileTitle(context.getAssets().open(path[i]))) ;
        }
        return list;
    }

    public String getTitleFile(Context context,String path) throws IOException{
        return FileUtils.readAssetsFileTitle(context.getAssets().open(path));
    }

    public News getFile(Context context,String path) throws IOException{
        return FileUtils.readAssetsFile(context.getAssets().open(path),false);
    }
}
