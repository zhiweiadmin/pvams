package com.goodpower.pvams.common;

public class Page {

    Integer page;

    Integer pageSize;

    Long count;

    public Page(){

    }

    public Page(int page, int pageSize, Long count){
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
