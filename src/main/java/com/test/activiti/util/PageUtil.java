package com.test.activiti.util;

/**
 * 分页工具类
 * @author aaa
 */
public class PageUtil {
    //页数
    private Integer page;
    //页面大小
    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
