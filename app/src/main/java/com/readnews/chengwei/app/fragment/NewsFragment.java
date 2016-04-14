package com.readnews.chengwei.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.readnews.chengwei.Impl.ReadNewsModelImpl;
import com.readnews.chengwei.app.R;
import com.readnews.chengwei.bean.News;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by chengwei on 16/4/11.
 */
public class NewsFragment extends Fragment {
    private TextView wv_Title;
    private TextView wv_Body;
    private News news;
    ReadNewsModelImpl readNewsModel;
    boolean flag = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int index =  getArguments().getInt("index");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news,null);
        wv_Title = (TextView) view.findViewById(R.id.news_wv_title);
        wv_Body = (TextView) view.findViewById(R.id.news_wv_body);
        try {
            news = new News();
            MyHandler handler = new MyHandler(getActivity(),news,wv_Title,wv_Body);
            readNewsModel = ReadNewsModelImpl.getInstant();
            readNewsModel.setThreadFlag(flag);
            readNewsModel.setData(getActivity(),handler,index);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    static class MyHandler extends  Handler{
        WeakReference<Activity > mActivityReference;
        private News news;
        private TextView wv_Title;
        private TextView wv_Body;
        MyHandler(Activity activity,News news,TextView wv_Title,TextView wv_Body) {
            mActivityReference= new WeakReference<Activity>(activity);
            this.news = news;
            this.wv_Title = wv_Title;
            this.wv_Body = wv_Body;
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                news = (News)msg.obj;
                wv_Title.setText(news.getTitle());
                wv_Body.setText(news.getBody());
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        flag = false;
        readNewsModel.setThreadFlag(flag);
    }

    @Override
    public void onResume() {
        super.onResume();
        flag = true;
        readNewsModel.setThreadFlag(flag);
    }
}
