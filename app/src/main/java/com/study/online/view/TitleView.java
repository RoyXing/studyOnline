package com.study.online.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.online.R;


/**
 * Created by roy on 2016/12/17.
 */

public class TitleView extends Toolbar {

    ImageView leftImage, rightImage;
    TextView title;
    View view;

    public TitleView(Context context) {
        super(context);
        init(context);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTitle("");
        setContentInsetsRelative(10, 10);
        view = LayoutInflater.from(context).inflate(R.layout.layout_title, null);
        leftImage = (ImageView) view.findViewById(R.id.title_back);
        rightImage = (ImageView) view.findViewById(R.id.title_right);
        title = (TextView) view.findViewById(R.id.title_title);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        addView(view, lp);
    }

    public void setCustomTitle(String s) {
        if (title != null) {
            title.setText(s);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        removeView(view);
        super.setTitle(title);
    }

    public void setLeftImage(int image) {
        leftImage.setImageResource(image);
    }

    public void setRightImage(int image) {
        this.rightImage.setImageResource(image);
    }

    public void setLeftImageOnClickListener(OnClickListener onClickListener) {
        leftImage.setOnClickListener(onClickListener);
    }

    public void setRightImageOnClickListener(OnClickListener onClickListener) {
        rightImage.setOnClickListener(onClickListener);
    }

    public void isShowLeftImage(boolean isShow) {
        if (isShow)
            leftImage.setVisibility(View.VISIBLE);
        else
            leftImage.setVisibility(View.GONE);
    }

    public void isShowRightImage(boolean isShow) {
        if (isShow) {
            rightImage.setVisibility(View.VISIBLE);
        } else {
            rightImage.setVisibility(View.GONE);
        }
    }

}
