package com.study.online.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.online.R;
import com.study.online.activity.CourseDetailActivity;
import com.study.online.adapter.CourseRecyclerAdapter;
import com.study.online.bean.KnowledgeBean;
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
 * Created by roy on 2016/12/16.
 */

public class EducationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, CourseRecyclerAdapter.RecyclerViewListener {

    private View mView;
    private RecyclerView education_recyclerview;
    private SwipeRefreshLayout education_refresh;
    private CourseRecyclerAdapter mAdapter;
    private int currentPage = 0;
    private TextView load;
    private List<KnowledgeBean> listbean;
    private boolean loadMore = false;//是否加载更多
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_education, null);
        initView();
        initEvent();
        return mView;
    }

    private void initView() {
        education_recyclerview = (RecyclerView) mView.findViewById(R.id.education_recyclerview);
        education_refresh = (SwipeRefreshLayout) mView.findViewById(R.id.education_refresh);
        load = (TextView) mView.findViewById(R.id.load);
        load.setVisibility(View.GONE);
    }

    private void initEvent() {
        mAdapter = new CourseRecyclerAdapter(getActivity(), new ArrayList<KnowledgeBean>());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        education_recyclerview.setLayoutManager(layoutManager);
        education_recyclerview.setAdapter(mAdapter);
        education_recyclerview.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        education_refresh.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        listbean=new ArrayList<>();
        getData();
        education_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView) && loadMore) {
                    load.setVisibility(View.VISIBLE);
                    getData();
                } else {
                    load.setVisibility(View.GONE);
                }

            }
        });
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
    private void getData() {
        OkHttpUtils.post().url(Config.COURSE_LIST)
                .addParams("type", "教育课程")
                .addParams("page", currentPage + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        load.setVisibility(View.GONE);
                        education_refresh.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000 && jsonObject.optString("info").equals("success")) {
                                List<KnowledgeBean> list = JsonToBean.getBeans(jsonObject.optString("response"), KnowledgeBean.class);
                                if (currentPage==0)
                                    listbean.clear();
                                if (list.size()>=10){
                                    currentPage++;
                                    loadMore=true;
                                }else {
                                    loadMore=false;
                                }
                                listbean.addAll(list);
                                mAdapter.setData(listbean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        load.setVisibility(View.GONE);
                        education_refresh.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        currentPage=0;
        getData();
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent = new Intent(getContext(), CourseDetailActivity.class);
        intent.putExtra("course", mAdapter.getData().get(position));
        startActivity(intent);
    }
}
