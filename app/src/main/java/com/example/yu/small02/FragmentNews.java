package com.example.yu.small02;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yu.small02.model.NewBean;
import com.example.yu.small02.presenter.IpresenterImpl;
import com.example.yu.small02.view.IView;

import java.util.List;

import me.maxwin.view.XListView;

public class FragmentNews extends Fragment implements IView {

    private XListView xListView;
    IpresenterImpl ipresenter;
    private String str="http://www.xieast.com/api/news/news.php?page=";
    private int mpage=1;
    ListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new ListAdapter(getActivity());
        xListView=view.findViewById(R.id.xlistview);
        ipresenter=new IpresenterImpl(this);
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setAdapter(adapter);
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                mpage=1;
                requestData();
            }

            @Override
            public void onLoadMore() {
                mpage++;
                requestData();
            }
        });
        requestData();

    }
    public void requestData(){
        ipresenter.startRequest(str+mpage,NewBean.class);
    }

    @Override
    public void setData(Object data) {
        Log.i("TEST",data.toString()+"setData");
        NewBean newBean= (NewBean) data;
        List<NewBean.DataBean> list=newBean.getData();
        Log.i("TEST",list.size()+"");
        if(mpage==1){
            adapter.setList(newBean.getData());
            xListView.stopRefresh();
        }else{
            adapter.addList(newBean.getData());
            xListView.stopLoadMore();
        }
    }
}
