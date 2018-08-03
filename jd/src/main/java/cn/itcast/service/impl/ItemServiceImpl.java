package cn.itcast.service.impl;

import cn.itcast.dao.ItemDao;
import cn.itcast.pojo.ResultModel;
import cn.itcast.service.ItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by on 2018/8/1.
 */
@Service
public class ItemServiceImpl implements ItemService {

    //注入dao层对象
    @Autowired
    private ItemDao itemDao;

    /**
     * 需求：根据关键词查询索引库商品数据
     * 参数：String queryString,String catelog_name,String price,String sort,Integer page,Integer rows
     * 返回值：ResultModel
     * 业务:
     * 1,参数封装
     * 2,页码计算
     */
    public ResultModel queryProducts(String queryString,
                                     String catelog_name,
                                     String price,
                                     String sort,
                                     Integer page,
                                     Integer rows) {
        //创建solrQuery对象封装所有查询参数
        SolrQuery solrQuery = new SolrQuery();
        //1,封装主查询条件参数
        if(queryString!=null && !"".equals(queryString)){
            solrQuery.setQuery(queryString);
        }else{
            solrQuery.setQuery("*:*");
        }

        //2,分类过滤查询
        if(catelog_name!=null && !"".equals(catelog_name)){
            solrQuery.addFilterQuery("product_catalog_name:"+catelog_name);
        }

        //3,价格过滤
        //参数值：0-910-1920-2930-3940-49 50-*
        if(price!=null && !"".equals(price)){
            //切分价格
            String[] prices = price.split("-");
            //设置过滤价格参数
            solrQuery.addFilterQuery("product_price:["+prices[0]+" TO "+prices[1]+"]");
        }

        //4,排序
        if("1".equals(sort)){
            solrQuery.setSort("product_price", SolrQuery.ORDER.asc);
        }else{
            solrQuery.setSort("product_price", SolrQuery.ORDER.desc);
        }

        //5，设置分页
        //计算起始页
        int startNo = (page-1)*rows;
        solrQuery.setStart(startNo);
        solrQuery.setRows(rows);

        //设置高亮
        //1) 开启高亮
        solrQuery.setHighlight(true);
        //2) 指定高亮字段
        solrQuery.addHighlightField("product_name");
        //3) 设置高亮前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        //4) 设置高亮后缀
        solrQuery.setHighlightSimplePost("</font>");

        //7，设置默认查询字段
        solrQuery.set("df","product_keywords");

        //调用dao方法实现查询
        ResultModel model = itemDao.queryProducts(solrQuery);

        //计算总页码
        //获取总记录数
        Integer totalCount = model.getTotalCount();
        Integer pages = totalCount/rows;
        if(totalCount%rows>0){
            pages++;
        }

        //把总页码添加到model
        model.setTotalPages(pages);
        model.setCurPage(page);


        return model;
    }
}
