package cn.itcast.dao;

import cn.itcast.pojo.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by on 2018/8/1.
 */
public interface ItemDao {


    /**
     * 需求：查询索引库方法
     * 参数：SolrQuery
     * 返回值：ResultModel
     */
    public ResultModel queryProducts(SolrQuery solrQuery);



}
