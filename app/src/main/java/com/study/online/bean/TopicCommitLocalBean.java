package com.study.online.bean;

/**
 * Created by roy on 2017/1/6.
 * 评论实体类，本地上传数据使用
 */

public class TopicCommitLocalBean {
    private String parentId;
    private String content;
    private String topicId;
    private String userId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
