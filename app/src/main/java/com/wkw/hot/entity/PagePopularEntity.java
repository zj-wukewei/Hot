package com.wkw.hot.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by wukewei on 17/5/8.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public class PagePopularEntity {
    @SerializedName("ret_code")
    private int retCode;
    @SerializedName("pagebean")
    private  Pagebean pagebean;


    public int getRetCode() {
        return retCode;
    }


    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }


    public Pagebean getPagebean() {
        return pagebean;
    }


    public void setPagebean(Pagebean pagebean) {
        this.pagebean = pagebean;
    }


    public static class Pagebean {
        @SerializedName("allPages")
        private int allPages;
        @SerializedName("contentlist")
        private List<PopularEntity> contentlist;
        @SerializedName("currentPage")
        private int currentPage;
        @SerializedName("allNum")
        private int allNum;
        @SerializedName("maxResult")
        private int maxResult;


        public int getMaxResult() {
            return maxResult;
        }


        public void setMaxResult(int maxResult) {
            this.maxResult = maxResult;
        }


        public int getAllPages() {
            return allPages;
        }


        public void setAllPages(int allPages) {
            this.allPages = allPages;
        }


        public List<PopularEntity> getContentlist() {
            return contentlist;
        }


        public void setContentlist(List<PopularEntity> contentlist) {
            this.contentlist = contentlist;
        }


        public int getCurrentPage() {
            return currentPage;
        }


        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }


        public int getAllNum() {
            return allNum;
        }


        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }
    }
}
