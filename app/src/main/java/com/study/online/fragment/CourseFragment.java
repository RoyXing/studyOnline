package com.study.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.online.R;
import com.study.online.adapter.CourseRecyclerAdapter;
import com.study.online.bean.KnowledgeBean;
import com.study.online.config.Config;
import com.study.online.utils.JsonToBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by roy on 2016/12/16.
 */

public class CourseFragment extends BaseFragment {

    private View mView;
    private RecyclerView course_recyclerview;
    private CourseRecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_course, null);
        initView(mView);
        initEvent();
        return mView;
    }

    private void initView(View mView) {
        course_recyclerview = (RecyclerView) mView.findViewById(R.id.course_recyclerview);
    }

    private void initEvent() {
        mAdapter = new CourseRecyclerAdapter(getActivity(), new ArrayList<KnowledgeBean.ResponseBean>());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        course_recyclerview.setLayoutManager(layoutManager);
//        course_recyclerview.setAutoMeasureEnabled(true);
        course_recyclerview.setAdapter(mAdapter);
        getData();

    }

    public void getData() {
        OkHttpUtils.post().url(Config.COURSE_LIST).addParams("type", "课程介绍").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                KnowledgeBean bean = JsonToBean.getBean(response, KnowledgeBean.class);
                List<KnowledgeBean.ResponseBean> list = bean.getResponse();
                mAdapter.setData(list);
            }
        });
    }

}
