package com.study.online.config;

import com.google.gson.internal.Streams;
import com.study.online.bean.SlideMenuItem;
import com.study.online.view.button.ProcessButton;

/**
 * Created by roy on 2017/1/6.
 * 接口名
 */

public class Config {
    //主URL
    private static String URL = "http://115.159.100.155:8080/courseteach";

    //注册接口
    public static String RIGISTER = URL + "/user/rigister";

    //登录接口
    public static String LOGIN = URL + "/user/login";

    //话题列表
    public static String TOPIC_LIST = URL + "/topic/list";

    //话题详情+ID
    public static String TOPIC = URL + "/topic/";

    //增加话题
    public static String ADD_TOPIC = URL + "/topic/add";

    //话题评论
    public static String ADD_COMMENT_TOPIC = URL + "/comment/add";

    //课程介绍／教育课程／计算机课程
    public static String COURSE_LIST = URL + "/course/list";

    //资源
    public static String LINK_LIST = URL + "/link/list";

    //图片上传
    public static String UP_FILE = URL + "/upload ";

    //个人信息修改
    public static String UP_PERSON_MESSAGE = URL + "/user/update";

    //我的发帖
    public static String MY_WRITE = URL + "/topic/list";

    //与我相关
    public static String MY_COMMINT = URL + "/topic/join";

    public static String STUDY_DESC = URL + "/typedesc";
}
