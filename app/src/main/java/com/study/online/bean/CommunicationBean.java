package com.study.online.bean;

import java.io.Serializable;

/**
 * Created by roy on 2016/12/21.
 * 社区帖子类
 */

public class CommunicationBean implements Serializable {
    private String id;//社区id
    private String userId;//发帖者的id
    private String headurl;//发帖者头像
    private String userName;//发帖者昵称
    private String time;//发帖时间
    private String commit;//评论数
    private String title;//帖子的標題
    private String content;//帖子的內容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
