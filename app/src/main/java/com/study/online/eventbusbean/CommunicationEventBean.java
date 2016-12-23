package com.study.online.eventbusbean;

import com.study.online.bean.CommunicationBean;

/**
 * Created by roy on 2016/12/23.
 */

public class CommunicationEventBean {
    CommunicationBean bean;

    public CommunicationEventBean(CommunicationBean bean) {
        this.bean = bean;
    }

    public CommunicationBean getBean() {
        return bean;
    }
}
