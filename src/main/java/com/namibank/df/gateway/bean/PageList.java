package com.namibank.df.gateway.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 分页
 * 
 * <p>要得到总页数请使用 toPaginator().getTotalPages();</p>
 * 使用这个解决了直接继承ArrayList反序列化失败的问题
 * @author CliveYuan
 */
public class PageList<E> implements Serializable {
    
    private static final long serialVersionUID = 7291073537433266668L;
    
    /**
     * 数据列表
     */
    private List<E> dataList = new ArrayList<>();
    
    /**
     * 总页数
     */
    private Integer totalPage;
    
    /**
     * 总记录数
     */
    private Integer totalCount;
    

    public PageList() {
        this.totalPage = 0;
        this.totalCount = 0;
    }

    public void add(E e) {
        dataList.add(e);
    }

    public List<E> getDataList() {
        return dataList;
    }
    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "PageList [dataList=" + dataList + ", totalPage=" + totalPage + ", totalCount=" + totalCount + "]";
    }
    
}

