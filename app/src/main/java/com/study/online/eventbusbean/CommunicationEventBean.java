package com.study.online.eventbusbean;

import com.study.online.bean.TopicBean;

/**
 * Created by roy on 2016/12/23.
 */

public class CommunicationEventBean {
    TopicBean bean;
    String msg;
    public CommunicationEventBean(TopicBean bean,String msg) {
        this.bean = bean;
        this.msg=msg;
    }

    public TopicBean getBean() {
        return bean;
    }

    public String getMsg() {
        return msg;
    }
}
