package com.wkw.hot.model;

/**
 * Created by wukewei on 16/12/2.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public class PopularModel {

    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;


    @Override public String toString() {
        return "PopularModel{" +
            "ctime='" + ctime + '\'' +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", picUrl='" + picUrl + '\'' +
            ", url='" + url + '\'' +
            '}';
    }


    public String getCtime() {
        return ctime;
    }


    public void setCtime(String ctime) {
        this.ctime = ctime;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getPicUrl() {
        return picUrl;
    }


    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }
}
