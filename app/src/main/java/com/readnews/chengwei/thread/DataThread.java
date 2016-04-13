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
    ReadNewsModelImpl readNewsModel = ReadNewsModelImpl.getInstant();
    public DataThread(Context context, Handler handler){
       init(context,handler,true,0);
    }

    private void init(Context context,Handler handler,Boolean isTitle,int index) {
        this.handler = handler;
        this.context = context;
        this.isTitle = isTitle;
        this.index = index;
    }

    public DataThread(Context context,Handler handler,Boolean isTitle,int index){
        init(context,handler,isTitle,index);
    }
    @Override
    public void run() {

        Message msg = handler.obtainMessage();
        if (isTitle){
            try {
                msg.obj = readNewsModel.getListTitleFile(context, Path.PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                msg.obj = readNewsModel.getFile(context, Path.PATH[index]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        handler.sendMessage(msg);

    }


}
