package cn.itcast.pojo;

import java.util.List;

/**
 * Created by on 2018/8/1.
 */
public class ResultModel {

    //总记录数
    private Integer totalCount;

    //总页码
    private Integer totalPages;

    //当前页
    private Integer curPage;

    //总记录
    private List<Item>  productList;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public List<Item> getProductList() {
        return productList;
    }

    public void setProductList(List<Item> productList) {
        this.productList = productList;
    }
}
