package com.readnews.chengwei.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.readnews.chengwei.Impl.ReadNewsModelImpl;
import com.readnews.chengwei.app.R;
import com.readnews.chengwei.bean.News;
import com.readnews.chengwei.contants.Path;
import com.readnews.chengwei.thread.DataThread;

import java.io.IOException;

/**
 * Created by chengwei on 16/4/11.
 */
public class NewsFragment extends Fragment {
    private TextView tv_Title;
    private TextView tv_Body;
    private News news;
    DataThread thread;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int index =  getArguments().getInt("index");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news,null);
        tv_Title = (TextView)view.findViewById(R.id.news_tv_title);
        tv_Body = (TextView)view.findViewById(R.id.news_tv_body);
        try {
            ReadNewsModelImpl.getInstant().setData(getActivity(),handler,index);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            news = (News)msg.obj;
            tv_Title.setText(news.getTitle());
            tv_Body.setText(news.getBody());
        }
    };



}
