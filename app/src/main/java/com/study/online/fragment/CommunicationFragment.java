package com.study.online.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.study.online.R;
import com.study.online.activity.CommunicationDatialsActivity;
import com.study.online.adapter.CommunicationAdapter;
import com.study.online.bean.CommunicationBean;
import com.study.online.eventbusbean.CommunicationEventBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by roy on 2016/12/16.
 * 社区
 */

public class CommunicationFragment extends BaseFragment {

    private View mView;
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private View footview;
    private List<CommunicationBean>list;
    private CommunicationAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_commincation, null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.refresh);
        listView=(ListView) view.findViewById(R.id.listview_communitcation);
        footview=LayoutInflater.from(getActivity()).inflate(R.layout.footview,null);
        list=new ArrayList<>();
        for (int i=0;i<10;i++){
            CommunicationBean bean=new CommunicationBean();
            bean.setId("1");
            bean.setCommit("評論：23"+i);
            bean.setUserName("李剛是我兒子"+i);
            bean.setUserId("3");
            bean.setContent("我是一隻小小鳥我是一隻小小鳥我是一隻小小鳥我是一隻小小鳥我是一隻小小鳥我是一隻小小鳥我是一隻小小鳥我是一隻小小鳥我是一隻小小鳥");
            bean.setTitle("這裡怎麼有一隻鳥");
            bean.setTime("2016-12-12 12:1"+i);
            //bean.setHeadurl("http://image57.360doc.com/DownloadImg/2012/12/1116/28840930_8"+i+".jpg");
            bean.setHeadurl("http://cdnq.duitang.com/uploads/item/201312/05/20131205172450_dzvYv.thumb.700_0.jpeg");
            list.add(bean);
        }
        adapter=new CommunicationAdapter(getActivity(),0,list);
        listView.setAdapter(adapter);
        listView.addFooterView(footview);
        listener();
    }
    private void listener(){
        //刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(),"刷新了",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
        //加载更多
        footview.findViewById(R.id.cardview_foot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"加载下一页数据",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
