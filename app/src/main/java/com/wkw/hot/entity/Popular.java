package com.wkw.hot.entity;

import java.io.Serializable;

/**
 * Created by wukewei on 16/5/26.
 */
public class Popular implements Serializable{


    @Override
    public String toString() {
        return "Popular{" +
                "ctime='" + ctime + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    /**
     * ctime : 2016-05-25
     * title : 李仁港怎么注入ROCK范儿？井宝如何撒野？《盗墓笔记》的方方面面请看这里！
     * description : 南都全娱乐
     * picUrl : http://zxpic.gtimg.com/infonew/0/wechat_pics_-5707820.jpg/640
     * url : http://mp.weixin.qq.com/s?__biz=MjM5MTE1ODI2MA==&idx=2&mid=2651787931&sn=009a0d9df1accebef2adba14cc61ac9d
     */

    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
