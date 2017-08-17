package com.sillyhat.springmvc.stripe.web;

import com.sillyhat.base.dto.MozatAJAX;
import com.sillyhat.base.utils.Constants;
import com.sillyhat.springmvc.stripe.dto.ProductDTO;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LearningWordController
 *
 * @author 徐士宽
 * @date 2017/8/3 19:59
 */
@Controller
@RequestMapping("/shopping_bag")
public class ShoppingBagController {

    private Logger logger = LoggerFactory.getLogger(ShoppingBagController.class);


    @ResponseBody
    @ApiOperation(value = "add to bag", response = MozatAJAX.class, notes = "添加购物车，校验product当前库存；返回添加成功或失败code and msg;product_id:product主键ID")
    @RequestMapping(method = {RequestMethod.POST})
    public MozatAJAX addToBag(@RequestParam("uid")Long uid,@RequestParam("sig")Long sig,@RequestBody ProductDTO product) {
        String msg = "Add shopping bag success.";
        if(uid == 1l){
            msg = "Insufficient Stock";
        }
        return new MozatAJAX(Constants.MOZAT_CREATED_SUCCESS,msg);
    }

    @ResponseBody
    @ApiOperation(value = "get shopping bag", response = MozatAJAX.class, notes = "得到当前登录人shopping bag中的products")
    @RequestMapping(method = {RequestMethod.GET})
    public MozatAJAX shoppingBag(@RequestParam("uid")Long uid) {
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> resultParams = new HashMap<>();
        resultParams.put("brand_id","11001");
        resultParams.put("brand_name","The Closert Lover");
        List<Map<String,Object>> productList = new ArrayList<>();
        Map<String,Object> productParams = new HashMap<>();
        productParams.put("shopping_bag_item_id","15555501");
        productParams.put("shop_item_id","1010");
        productParams.put("shop_item_name","HAHA");
        productParams.put("brand_name","Silly Hat");
        productParams.put("current_price",9999999);
        productParams.put("original_price",100000099);
        productParams.put("cashback",8030);
        productParams.put("color","red");
        productParams.put("currency","SGD");
        productParams.put("quantity",1);
        productParams.put("size","XL");
        productParams.put("shop_item_group_id",80);
        productParams.put("is_out_of_stock",false);
        productParams.put("image","https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjjvs3ysdvVAhXJQI8KHZrzDjgQjRwIBw&url=https%3A%2F%2Fkenengba.com%2Fpost%2F369.html&psig=AFQjCNEazuEfrqNobuvimoJm_WzSwh-YRw&ust=1502960292018086");
        productList.add(productParams);
        productParams = new HashMap<>();
        productParams.put("shopping_bag_item_id","15555501");
        productParams.put("shop_item_id","1010");
        productParams.put("shop_item_name","HAHA");
        productParams.put("brand_name","Silly Hat");
        productParams.put("current_price",9999999);
        productParams.put("original_price",100000099);
        productParams.put("cashback",8030);
        productParams.put("color","red");
        productParams.put("currency","SGD");
        productParams.put("quantity",2);
        productParams.put("size","3XL");
        productParams.put("shop_item_group_id",80);
        productParams.put("is_out_of_stock",true);
        productParams.put("image","https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjjvs3ysdvVAhXJQI8KHZrzDjgQjRwIBw&url=https%3A%2F%2Fkenengba.com%2Fpost%2F369.html&psig=AFQjCNEazuEfrqNobuvimoJm_WzSwh-YRw&ust=1502960292018086");
        productList.add(productParams);
        resultParams.put("pruducts",productList);
        result.add(resultParams);

        resultParams = new HashMap<>();
        resultParams.put("brand_id","11002");
        resultParams.put("brand_name","Purpur");
        productList = new ArrayList<>();
        productParams = new HashMap<>();
        productParams.put("shopping_bag_item_id","15555501");
        productParams.put("shop_item_id","1010");
        productParams.put("shop_item_name","Lace Top");
        productParams.put("brand_name","Purpur");
        productParams.put("current_price",2000);
        productParams.put("original_price",3050);
        productParams.put("cashback",100);
        productParams.put("color","black");
        productParams.put("currency","SGD");
        productParams.put("quantity",1);
        productParams.put("size","XXL");
        productParams.put("shop_item_group_id",80);
        productParams.put("is_out_of_stock",true);
        productParams.put("image","https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjjvs3ysdvVAhXJQI8KHZrzDjgQjRwIBw&url=https%3A%2F%2Fkenengba.com%2Fpost%2F369.html&psig=AFQjCNEazuEfrqNobuvimoJm_WzSwh-YRw&ust=1502960292018086");
        productList.add(productParams);
        resultParams.put("pruducts",productList);
        result.add(resultParams);
        return new MozatAJAX(result);
    }

    @ResponseBody
    @ApiOperation(value = "remove to shopping bag", response = MozatAJAX.class, notes = "该商品从shopping bag中移除;shopping_bag_id:shopping bag的主键ID")
    @DeleteMapping(value = "/delete/{shopping_bag_item_id}")
    public MozatAJAX deleteShopItem(@RequestParam("uid")Long uid,@PathVariable Long shopping_bag_item_id) {
        String msg = "Remove to shopping bag success";
        if(uid == 1l){
            msg = "Remove to shopping bag failure";
        }
        return new MozatAJAX(Constants.MOZAT_DELTE_SUCCESS,msg);
    }

//    @ResponseBody
//    @ApiOperation(value = "move to favourtes", response = MozatAJAX.class, notes = "该商品从shopping bag中移除,同时该商品添加到当前登录人的Favourites中;shopping_bag_id:shopping bag的主键ID")
//    @RequestMapping(value = "/move_to_Favourtes{shopping_bag_id}", method = {RequestMethod.GET})
//    public MozatAJAX moveToFavourtes(@RequestParam("uid")Long uid,@PathVariable Long shopping_bag_product_id) {
//        return new MozatAJAX(null);
//    }


//    @ResponseBody
//    @ApiOperation(value = "check out", response = MozatAJAX.class, notes = "如用户未登录，则返回提示登录信息；如已登录，则汇总生成订单")
//    @RequestMapping(value = "/check_out", method = {RequestMethod.POST})
//    public MozatAJAX checkOut() {
//        return new MozatAJAX(null);
//    }


}
