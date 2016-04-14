package com.readnews.chengwei.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.readnews.chengwei.Impl.ReadNewsModelImpl;
import com.readnews.chengwei.app.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * Created by chengwei on 16/4/11.
 */
public class ListFragment extends Fragment {

    OnSelcetTitleListener listener;
    RecyclerView recyclerView;
    List<String> list;
    boolean flag = true;
    ReadNewsModelImpl readNewsModelImpl;
    MyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list,null);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<String>();
        list.add("");
        adapter = new MyAdapter(list);
        MyHandler myhandler = new MyHandler(getActivity(),list,adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View parent, int position) {
                listener.selectTitle(position);
            }
        });
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        recyclerView.setAdapter(alphaAdapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL));

        try {
            readNewsModelImpl = ReadNewsModelImpl.getInstant();
            readNewsModelImpl.setThreadFlag(flag);
            readNewsModelImpl.setData(getActivity(),myhandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            listener = (OnSelcetTitleListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSelcetTitleListener");
        }

    }

    public interface OnSelcetTitleListener{
        void selectTitle(int position);
    }

    public interface OnItemClickListener{
        void onItemClick(View parent,int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        OnItemClickListener mOnItemClicklistener;
        List<String> list;
        public MyAdapter(List<String> list){
            this.list = list;
        }

        public void setList(List<String> list){
            this.list = list;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list_item,null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv.setText(list.get(position));
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClicklistener !=null){
                        mOnItemClicklistener.onItemClick(v,position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.mOnItemClicklistener = listener;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tv;
            public View view;
            public MyViewHolder(View itemView) {
                 super(itemView);
                view = itemView;
                tv = (TextView) itemView.findViewById(R.id.tv);
             }

        }
    }


    static class MyHandler extends  Handler{
        WeakReference<Activity > mActivityReference;
        List<String> list;
        MyAdapter adapter;
        MyHandler(Activity activity,List<String> list,MyAdapter adapter) {
            mActivityReference= new WeakReference<Activity>(activity);
            this.list = list;
            this.adapter = adapter;
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = mActivityReference.get();
            if(activity!=null){
                list = (List<String>)msg.obj;
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        flag = false;
        readNewsModelImpl.setThreadFlag(flag);
    }

    @Override
    public void onResume() {
        super.onResume();
        flag = true;
        readNewsModelImpl.setThreadFlag(flag);
    }
}
