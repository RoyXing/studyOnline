package com.study.online.config;

/**
 * Created by roy on 2017/1/6.
 * 接口名
 */

public class Config {
    //主URL
    public static String URL = "http://115.159.100.155:8080/courseteach";

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

    public static String COURSE_LIST = URL + "/course/list";
}
