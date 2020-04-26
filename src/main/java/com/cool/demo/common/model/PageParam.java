package com.cool.demo.common.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页参数
 * Created by vincent on 2020-04-10
 */
public class PageParam {

    @ApiModelProperty(value="分页索引",required=true)
    private int page = 1;

    @ApiModelProperty(value="单页数量",required=true)
    private int size = 10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
