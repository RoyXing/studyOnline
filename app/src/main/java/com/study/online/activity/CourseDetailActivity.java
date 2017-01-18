package com.study.online.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.online.R;
import com.study.online.bean.KnowledgeBean;
import com.study.online.view.TitleView;

/**
 * Created by roy on 2017/1/16.
 */

public class CourseDetailActivity extends Activity implements View.OnClickListener {

    private ImageView image_course;
    private TextView course_author;
    private TextView course_publishing;
    private TextView course_desc;
    private TextView course_content;
    private TitleView toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_course_detail);
        initView();
        initEvent();
    }

    private void initView() {
        image_course = (ImageView) findViewById(R.id.image_course);
        course_author = (TextView) findViewById(R.id.course_author);
        course_publishing = (TextView) findViewById(R.id.course_publishing);
        course_desc = (TextView) findViewById(R.id.course_desc);
        course_content = (TextView) findViewById(R.id.course_content);
        toolbar = (TitleView) findViewById(R.id.toolbar);
    }

    private void initEvent() {
        toolbar.isShowLeftImage(true);
        toolbar.setLeftImageOnClickListener(this);
        KnowledgeBean course = (KnowledgeBean) getIntent().getSerializableExtra("course");
        Picasso.with(this).load(course.getImages()).into(image_course);
        toolbar.setCustomTitle(course.getName());
        course_author.setText("作者：" + course.getAuthor());
        course_publishing.setText("出版社：" + course.getPublishing());
        course_desc.setText("描述：" + course.getDesc());
        course_content.setText("内容：" + course.getContent());
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
