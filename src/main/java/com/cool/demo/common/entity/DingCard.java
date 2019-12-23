package com.cool.demo.common.entity;

import java.util.List;

/**
 * Created by vincent on 2019-12-20
 */
public class DingCard {

    // 标题，最长64个字符
    private String title;

    // 消息内容
    private String markdown;

    // 按钮排列方式 竖直排列(0)，横向排列(1)；必须与btn_json_list同时设置
    private Integer btnOrientation;

    // 按钮列表
    private List<BtnJsonList> btnJsonList;

    public static class BtnJsonList {

        // 按钮标题
        private String title;

        // 按钮超链接
        private String actionUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public Integer getBtnOrientation() {
        return btnOrientation;
    }

    public void setBtnOrientation(Integer btnOrientation) {
        this.btnOrientation = btnOrientation;
    }

    public List<BtnJsonList> getBtnJsonList() {
        return btnJsonList;
    }

    public void setBtnJsonList(List<BtnJsonList> btnJsonList) {
        this.btnJsonList = btnJsonList;
    }
}
