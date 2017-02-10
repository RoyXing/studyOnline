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

import com.study.online.R;
import com.study.online.activity.SourceActivity;
import com.study.online.adapter.SourceAdapter;
import com.study.online.bean.Source;
import com.study.online.config.Config;
import com.study.online.utils.JsonToBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by roy on 2017/2/6.
 */

public class NetFragment extends BaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private View mView;
    private ListView source_listView;
    private SourceAdapter mAdapter;
    private View footView;
    private SwipeRefreshLayout resource_refresh;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_article, null);
        initView();
        initEvent();
        return mView;
    }

    private void initView() {
        source_listView = (ListView) mView.findViewById(R.id.source_listView);
        resource_refresh = (SwipeRefreshLayout) mView.findViewById(R.id.resource_refresh);
        mAdapter = new SourceAdapter(getActivity(), new ArrayList<Source>());
        footView = LayoutInflater.from(getActivity()).inflate(R.layout.footview, null);
        source_listView.addFooterView(footView);
    }

    private void initEvent() {
        source_listView.setAdapter(mAdapter);
        source_listView.setOnItemClickListener(this);
        resource_refresh.setOnRefreshListener(this);
        footView.setOnClickListener(this);
        getData();
    }

    public void getData() {

        OkHttpUtils
                .get()
                .url(Config.LINK_LIST)
                .addParams("page", page + "")
                .addParams("type", "学习网站")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000 && jsonObject.optString("info").equals("success")) {
                                source_listView.removeFooterView(footView);
                                List<Source> sourceList = JsonToBean.getBeans(jsonObject.optString("response").toString(), Source.class);
                                if (page == 0)
                                    mAdapter.getData().clear();
                                if (sourceList.size() >= 10) {
                                    page++;
                                    source_listView.addFooterView(footView);
                                } else {
                                    source_listView.removeFooterView(footView);
                                }
                                mAdapter.setData(sourceList);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        resource_refresh.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), SourceActivity.class);
        intent.putExtra("name", mAdapter.getData().get(position).getName());
        intent.putExtra("url", mAdapter.getData().get(position).getLink());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        page = 0;
        source_listView.addFooterView(footView);
        getData();
    }

    @Override
    public void onClick(View v) {
        getData();
    }
}
