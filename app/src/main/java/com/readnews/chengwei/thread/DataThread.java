package com.readnews.chengwei.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.readnews.chengwei.Impl.ReadNewsModelImpl;
import com.readnews.chengwei.contants.Path;

import java.io.IOException;

/**
 * Created by chengwei on 16/4/12.
 */
public class DataThread extends Thread {
    private Handler handler;
    private Context context;
    private boolean isTitle;
    private int index;
    private boolean flag;
    ReadNewsModelImpl readNewsModel = ReadNewsModelImpl.getInstant();
    public DataThread(Context context, Handler handler,boolean flag){
       init(context,handler,true,0,flag);
    }

    private void init(Context context,Handler handler,Boolean isTitle,int index,boolean flag) {
        this.handler = handler;
        this.context = context;
        this.isTitle = isTitle;
        this.index = index;
        this.flag = flag;
    }

    public DataThread(Context context,Handler handler,Boolean isTitle,int index,boolean flag){
        init(context,handler,isTitle,index,flag);
    }
    @Override
    public void run() {
        if (flag) {
            Message msg = handler.obtainMessage();
            if (isTitle) {
                try {
                    msg.obj = readNewsModel.getListTitleFile(context, Path.PATH);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    msg.obj = readNewsModel.getFile(context, Path.PATH[index]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            handler.sendMessage(msg);
        }

    }

}
