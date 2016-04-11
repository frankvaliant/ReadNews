package com.readnews.chengwei.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.readnews.chengwei.Impl.ReadNewsModelImpl;
import com.readnews.chengwei.app.R;
import com.readnews.chengwei.contants.Path;

import java.io.IOException;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * Created by chengwei on 16/4/11.
 */
public class ListFragment extends Fragment {

    OnSelcetTitleListener listener;
    RecyclerView recyclerView;
    List<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list,null);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            list = setData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyAdapter adapter = new MyAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View parent, int position) {
                listener.selectTitle(position);

            }
        });
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        recyclerView.setAdapter(alphaAdapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL));
        return view;
    }

    private List<String> setData() throws IOException {
        ReadNewsModelImpl readNewsModelImpl = new ReadNewsModelImpl();
        return readNewsModelImpl.getListTitleFile(getActivity(), Path.PATH);
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

}
