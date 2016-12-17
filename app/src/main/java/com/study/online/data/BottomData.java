package com.study.online.data;


import com.study.online.R;
import com.study.online.bean.BottomBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 2016/12/16.
 * the data of app bottom
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
