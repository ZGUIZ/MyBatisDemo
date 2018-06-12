package com.zguiz.bean;

public class Pager {
    private int currentPage=1;
    private int pageSize=10;
    private int total;

    public Pager() {
    }

    public Pager(int total){
        this.total=total;
    }

    public Pager(int currentPage, int pageSize) {
        setPageSize(pageSize);
        setCurrentPage(currentPage);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage>0) {
            this.currentPage = currentPage;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize>0) {
            this.pageSize = pageSize;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        if(total>=0) {
            this.total = total;
        }
    }

    public int getStartIndex(){
        return (currentPage-1)*pageSize;
    }

    /**
     * 获取总页数
     * @return
     */
    public int getPages(){
        int pages=total%pageSize==0?total/pageSize:total/pageSize+1;
        return pages;
    }

    /**
     * 上一页
     * @return
     */
    public int getPrePage(){
        if(currentPage>1){
            return currentPage-1;
        }
        return currentPage;
    }

    /**
     * 下一页
     * @return
     */
    public int getNextPage(){
        if(currentPage<getPages()){
            return currentPage+1;
        }
        return getPages();
    }
}
