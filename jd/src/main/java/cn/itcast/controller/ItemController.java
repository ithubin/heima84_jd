package cn.itcast.controller;

import cn.itcast.pojo.ResultModel;
import cn.itcast.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by on 2018/8/1.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    //注入service对象
    @Autowired
    private ItemService itemService;

    /**
     * 需求：根据关键词查询索引库商品数据
     * 参数：String queryString,String catelog_name,String price,String sort,Integer page,Integer rows
     * 返回值：ResultModel
     * 业务:
     * 1,接受参数
     * 2,查询结果回显
     */
    @RequestMapping("/list")
    public String showList(String queryString,
                           String catelog_name,
                           String price,
                           @RequestParam(defaultValue ="1") String sort,
                           @RequestParam(defaultValue ="1") Integer page,
                           @RequestParam(defaultValue ="30") Integer rows,
                           Model model){
        //调用service服务方法，实现索引库搜索
        ResultModel rmodel = itemService.queryProducts(queryString, catelog_name, price, sort, page, rows);
        //页面回显
        model.addAttribute("queryString",queryString);
        //分类回显
        model.addAttribute("catalog_name",catelog_name);
        //价格回显
        model.addAttribute("price",price);
        //排序
        model.addAttribute("sort",sort);
        //当前页
        model.addAttribute("result",rmodel);


        return "product_list";
    }

}
