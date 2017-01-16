package com.study.online.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by roy on 2017/1/10.
 */

public class KnowledgeBean implements Serializable{


    /**
     * code : 10000
     * info : success
     * response : [{"id":1,"courseId":"f6ef9a3bf6b54845b630efdfb1f47e44","type":"课程介绍","name":"计算机操作系统","directory":"系统目录","author":"张健","publishing":"清华出版社","desc":"很好的操作系统书籍","content":"很好的内容","images":"http://a.hiphotos.baidu.com/image/pic/item/e7cd7b899e510fb3a78c787fdd33c895d0430c44.jpg"},{"id":2,"courseId":"ccf623c4129c4800983c5d16754e9b87","type":"教育课程","name":"三国演义","directory":"三国目录","author":"吴承恩","publishing":"北京大学出版社","desc":"很好的小说","content":"很想时，让人活而不忘","images":"http://pic31.nipic.com/20130630/5892523_003233711151_2.jpg"},{"id":3,"courseId":"d88a987202ca43d2845efee7a22f75ed","type":"计算机课程","name":"汇编语言","directory":"汇编目录","author":"初一","publishing":"西北邮电出版社","desc":"垃圾书记","content":"垃圾的呢绒，睡觉","images":"http://i8.download.fd.pchome.net/t_960x600/g1/M00/09/17/oYYBAFO-Bw6INpH0AALtmMxxgZsAABu2wKc9bAAAu2w098.jpg"}]
     */

    private int code;
    private String info;
    private List<ResponseBean> response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean implements Serializable{
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

        @Override
        public String toString() {
            return "ResponseBean{" +
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
                    '}';
        }
    }

    @Override
    public String toString() {
        return "KnowledgeBean{" +
                "code=" + code +
                ", info='" + info + '\'' +
                ", response=" + response +
                '}';
    }
}
