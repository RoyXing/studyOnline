package com.study.online.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by roy on 2016/12/24.
 * 嵌套在scrollview中使用
 */

public class ExpandListview extends ListView {
    public ExpandListview(Context context) {
        super(context);
    }

    public ExpandListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
