package com.study.online.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by roy on 2017/1/6.
 */

public class TopicBean implements Serializable {
    private String id;
    private String topicId;
    private String content;
    private String createTime;
    private String commentNum;
    private String userName;
    private String icon;
    private String status;
    private String userId;
    private List<TopicCommitBean> commentList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<TopicCommitBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<TopicCommitBean> commentList) {
        this.commentList = commentList;
    }
}
