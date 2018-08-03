package cn.itcast.service;

import cn.itcast.pojo.ResultModel;

/**
 * Created by on 2018/8/1.
 */
public interface ItemService {
    /**
     * 需求：根据关键词查询索引库商品数据
     * 参数：String queryString,String catelog_name,String price,String sort,Integer page,Integer rows
     * 返回值：ResultModel
     */
    public ResultModel queryProducts(String queryString, String catelog_name, String price, String sort, Integer page, Integer rows);
}
