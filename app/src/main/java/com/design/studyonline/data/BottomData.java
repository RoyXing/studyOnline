package com.design.studyonline.data;

import com.design.studyonline.R;
import com.design.studyonline.bean.BottomBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 2016/12/16.
 */

public class BottomData {

    public static List<BottomBean> getBottoms() {
        List<BottomBean> beanList = new ArrayList<>();
        beanList.add(new BottomBean(R.drawable.study_normal, R.drawable.study_press, "学习"));
        beanList.add(new BottomBean(R.drawable.resource_normal, R.drawable.resource_press, "资源"));
        beanList.add(new BottomBean(R.drawable.communication_normal, R.drawable.communication_press, "社区"));
        return beanList;
    }
}
