package com.example.mybatisplus.web.controller;

import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.SecurityUtils;
import com.example.mybatisplus.common.utls.SessionUtils;
import com.example.mybatisplus.common.utls.TokenUtils;
import com.example.mybatisplus.mapper.ProductMapper;
import com.example.mybatisplus.model.domain.Product;
import com.example.mybatisplus.service.ProductService;
import com.example.mybatisplus.service.RedisService;
import com.google.common.base.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 *
 *  前端控制器
 *
 *
 * @author lxp
 * @since 2022-03-02
 * @version v1.0
 */
@Controller
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisService redisService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Product  product =  productService.getById(id);
        return JsonResponse.success(product);
    }

    /**
     * 描述：添加商品信息，包括图片和规格
     */
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addProduct(@RequestBody Product product) throws Exception {
        System.out.println(product);
        productService.save(product);
        return JsonResponse.success(product).setCode(0).setStatus(true);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/deleteByID", method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public JsonResponse deleteByID(@RequestParam Long id) throws Exception {
        if(productService.removeById(id)){
            return JsonResponse.success(id).setCode(0).setMessage("删除成功!");
        }else{
            return JsonResponse.success(null).setCode(10000).setMessage("删除失败！");
        }
    }


    /**
    * 描述：根据Id 更新
    *
    */
//    @RequestMapping(value = "", method = RequestMethod.PUT)
//    @ResponseBody
//    public JsonResponse updateProduct(Product  product) throws Exception {
//        productService.updateById(product);
//        return JsonResponse.success(null);
//    }
    @RequestMapping(value = "/updateProduct",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse updateProduct(@RequestBody Product product){
        System.out.println(product);
        productService.updateById(product);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Product
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Product  product) throws Exception {
        productService.save(product);
        return JsonResponse.success(null);
    }


    /**
     * 描述:模糊搜索
     *
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse search(String s){
        List<Product> search = productService.search(s);
        return JsonResponse.success(search);
    }


    /**
     * 描述:显示商品信息
     *
     */
    @RequestMapping("/")
    @ResponseBody
    public JsonResponse showProduct(){
        List<Product> show = productService.list();
        return JsonResponse.success(show);
    }


    /**
     * 描述:获取所有的商品种类数据
     *
     */
    @RequestMapping(value = "/getKillProductInfoByID", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getKillProductInfoByID(@RequestParam Long id){
        Product product=productMapper.getKillProductInfoByID(id);
        System.out.println("product:====="+product);
        return JsonResponse.success(product).setCode(0);
    }
    /**
     * 描述:获取所有的商品种类数据
     *
     */
    @RequestMapping(value = "/getProductInfoByID", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getProductInfoByID(@RequestParam Long id){
        Product product=productMapper.getProductInfoByID(id);
        System.out.println("product:====="+product);
        return JsonResponse.success(product).setCode(0);
    }


    /**
     * 描述:商家根据自己的商家id查询本店铺的商品
     *
     */
    @RequestMapping("/getAllGoodsBySid")
    @ResponseBody
    public JsonResponse selectBySellerId(){
        SessionUtils.saveCurrentSellerInfo(SecurityUtils.getSellerInfo());
        Long id = SessionUtils.getCurrentSellerInfo().getId();
        List<Map<String,Object>> map = productService.selectBySellerId(id);
        System.out.println(map);
        return JsonResponse.success(map);
    }

    /**
     * 描述:获取某个种类的所有的商品数据
     *
     */
    @RequestMapping(value = "/getProductInfoByCategoryID", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getProductInfoByCategoryID(@RequestParam Long id){
//        for(int i=0;i<10;i++)
//            redisService.set(Integer.toString(i),i);
        List<Map<String,Object>> productList;
        if (id==-1){//查询所有
            productList=productMapper.getAllProductInfo();
        }else {
            productList=productMapper.getProductInfoByCategoryID(id);
        }
        System.out.println(productList);
        return JsonResponse.success(productList);
    }
    /**
     * 描述:判断该商品是否被收藏
     *
     */
    @RequestMapping(value = "/hasStored", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse hasStored(@RequestParam Long userid,@RequestParam Long pid,@RequestParam String token){
        System.err.println(TokenUtils.verify(token));
        if(TokenUtils.verify(token)) {
            Boolean hasStored = productService.hasStored(userid, pid);
            return JsonResponse.success(hasStored).setCode(0);
        }
        else
            return JsonResponse.failure(null);
    }
    /**
     * 描述：收藏商品
     *
     */
    @RequestMapping(value = "/store",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addStore(@RequestBody Map requestmap) throws Exception {
        System.out.println(requestmap);
        System.out.println("收藏商品");
        Long userid = Long.parseLong( requestmap.get("userid").toString());
        Long pid = Long.parseLong( requestmap.get("pid").toString());
        System.out.println(userid +","+pid);
        productService.addStore(userid,pid);
        return JsonResponse.success(null).setCode(0);
    }
    /**
     * 描述：取消收藏
     *
     */
    @RequestMapping(value = "/cancelStore",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cancelStore(@RequestBody Map requestmap) throws Exception {
        System.out.println(requestmap);
        System.out.println("取消收藏");
        Long userid = Long.parseLong( requestmap.get("userid").toString());
        Long pid = Long.parseLong( requestmap.get("pid").toString());
        System.out.println(userid +","+pid);
        productService.cancelStore(userid,pid);
        return JsonResponse.success(null).setCode(0);
    }
    /**
     * 描述：获取某个商家的某个种类的所有商品
     *
     */
    @RequestMapping(value = "/getAllProductByCidAndSid", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getProductListByCidAndSid(@RequestBody Map<String,Object> data){
        System.out.println(data);
        List<Map<String,Object>> productList=productMapper.getProductListByCidAndSid(data);
        System.out.println(productList);
        return JsonResponse.success(productList).setCode(0);
    }
    /**
     * 描述：模糊查询用户信息
     * */
    @RequestMapping(value = "/searchProductByKeyWord", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse searchProductByKeyWord(@RequestParam String keyword){
        System.out.println(keyword);
        List<Map<String,Object>> productList=productMapper.searchProductByKeyWord(keyword);
        System.out.println(productList);
        return JsonResponse.success(productList).setCode(0);
    }
//    public JsonResponse searchProductByKeyWord(@RequestParam String keyword){
//        QueryWrapper<Product> wrapper=new QueryWrapper<>();
//        wrapper.like("name",keyword);
//        wrapper.orderByDesc("createtime","updatetime");
//        List<Product> list=productService.list(wrapper);
//        System.out.println(list);
//        return JsonResponse.success(list).setCode(0);
//    }
    //显示商品详细信息
    @RequestMapping(value = "/getProductDetailByID", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getProductDetailByID(@RequestParam Long id){
        return JsonResponse.success(productMapper.getProductDetailByID(id));
    }
    /**
     * 描述:获取热度商品
     *
     */
    @RequestMapping(value = "/getfavoriteGoodsList", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getfavoriteGoodsList(){
        return JsonResponse.success(productMapper.getfavoriteGoodsList());
    }
    /**
     * 描述:获取新品
     *
     */
    @RequestMapping(value = "/getNewGoodsList", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getNewGoodsList(){
        return JsonResponse.success(productMapper.getNewGoodsList());
    }


    /**
     * 描述:获取某个种类的所有的商品数据
     *
     */
    @RequestMapping(value = "/getGoodsListBybid", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getGoodsListBybid(@RequestParam Long id){
        List<Map<String,Object>> productList=productService.getGoodsListBybid(id);
        System.out.println(productList);
        return JsonResponse.success(productList).setCode(0);
    }
    @RequestMapping(value = "/getProductStock", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public JsonResponse getProductStock(@RequestParam Long id){
        return JsonResponse.success(productMapper.getProductStock(id));
    }

}