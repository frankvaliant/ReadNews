package com.readnews.chengwei.Impl;

import android.content.Context;
import android.os.Handler;

import com.readnews.chengwei.bean.News;
import com.readnews.chengwei.model.ReadNewsModel;
import com.readnews.chengwei.thread.DataThread;
import com.readnews.chengwei.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengwei on 16/4/11.
 */
public class ReadNewsModelImpl implements ReadNewsModel{
    private boolean flag;
   static ReadNewsModelImpl readNewsModel;
    private DataThread thread;
    synchronized public static ReadNewsModelImpl getInstant(){
        if(readNewsModel==null){
            readNewsModel= new ReadNewsModelImpl();
        }

        return readNewsModel;
    }

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

    public void setData(Context context, Handler handler) throws IOException {
        thread = new DataThread(context,handler,flag);
        if(flag){
            thread.start();
        }else {
            thread.interrupt();
            thread = null;
        }
    }

    public void setData(Context context, Handler handler,int index) throws IOException {

        thread = new DataThread(context,handler,false,index,flag);
        if(flag){
            thread.start();
        }else {
            thread.interrupt();
            thread = null;
        }

    }

    public void setThreadFlag(boolean flag){
        this.flag = flag;
    }
}
