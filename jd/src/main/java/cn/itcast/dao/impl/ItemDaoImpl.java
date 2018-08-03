package cn.itcast.dao.impl;

import cn.itcast.dao.ItemDao;
import cn.itcast.pojo.Item;
import cn.itcast.pojo.ResultModel;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by on 2018/8/1.
 */
@Repository
public class ItemDaoImpl implements ItemDao {

    //注入solrJ服务对象
    @Autowired
    private SolrServer solrServer;

    /**
     * 需求：查询索引库方法
     * 参数：SolrQuery
     * 返回值：ResultModel
     */
    public ResultModel queryProducts(SolrQuery solrQuery) {

        //创建返回结果包装类对象
        ResultModel model = new ResultModel();
        //创建集合对象，封装从索引库查询商品数据
        List<Item> itemList = new ArrayList<>();

        try {
            //使用solr服务查询索引库
            QueryResponse response = solrServer.query(solrQuery);

            //获取查询文档集合
            SolrDocumentList results = response.getResults();

            //获取命中总记录数
            Long numFound = results.getNumFound();

            model.setTotalCount(numFound.intValue());

            //循环文档集合
            for (SolrDocument doc : results) {

                //创建商品对象item
                Item item = new Item();

                //获取id
                String id = (String) doc.get("id");
                item.setPid(id);
                //商品标题
                String product_name = (String) doc.get("product_name");

                //获取高亮
                Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
                //第一个map的key就是文档id
                Map<String, List<String>> stringListMap = highlighting.get(id);
                //第二个map的key就是高亮字段
                List<String> hList = stringListMap.get("product_name");

                //判断高亮是否存在
                if (hList != null && hList.size() > 0) {
                    product_name = hList.get(0);
                }

                item.setName(product_name);


                //商品价格
                Float product_price = (Float) doc.get("product_price");
                item.setPrice(12f);


                //商品图片
                String product_picture = (String) doc.get("product_picture");
                item.setPicture(product_picture);

                itemList.add(item);


            }

            //把集合放入包装类对象
            model.setProductList(itemList);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return model;
    }
}
