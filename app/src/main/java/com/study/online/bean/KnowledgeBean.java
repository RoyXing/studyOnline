package com.study.online.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by roy on 2017/1/10.
 */

public class KnowledgeBean implements Serializable {

    /**
     * id : 1
     * courseId : f6ef9a3bf6b54845b630efdfb1f47e44
     * type : 课程介绍
     * name : 计算机操作系统
     * directory : 系统目录
     * author : 张健
     * publishing : 清华出版社
     * desc : 很好的操作系统书籍
     * content : 很好的内容
     * images : http://a.hiphotos.baidu.com/image/pic/item/e7cd7b899e510fb3a78c787fdd33c895d0430c44.jpg
     */

    private int id;
    private String courseId;
    private String type;
    private String name;
    private String directory;
    private String author;
    private String publishing;
    private String desc;
    private String content;
    private String images;
    private String video;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "KnowledgeBean{" +
                "id=" + id +
                ", courseId='" + courseId + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", directory='" + directory + '\'' +
                ", author='" + author + '\'' +
                ", publishing='" + publishing + '\'' +
                ", desc='" + desc + '\'' +
                ", content='" + content + '\'' +
                ", images='" + images + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
