package com.sillyhat.springmvc.stripe.web;

import com.sillyhat.base.dto.MozatAJAX;
import com.sillyhat.springmvc.stripe.dto.PaymentDetailDTO;
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
@RequestMapping("/shop")
public class ShopController {

    private Logger logger = LoggerFactory.getLogger(ShopController.class);


    @ResponseBody
    @ApiOperation(value = "get bulletins", response = MozatAJAX.class, notes = "得到公告列表")
    @RequestMapping(value = "/bulletins", method = {RequestMethod.GET})
    public MozatAJAX bulletins() {
        List<String> result = new ArrayList<>();
        result.add("The card this token will represent. If you also pass in a customer, the card must be the ID of a card belonging to the customer. Otherwise, if you do not pass a customer, a Map containing a user's credit card details, with the options described below.");
        result.add("The customer (owned by the application's account) to create a token for. For use with Stripe Connect only; this can only be used with an OAuth access token or Stripe-Account header. For more details, see the shared customers documentation.");
        result.add("A set of key/value pairs that you can attach to a source object. It can be useful for storing additional information about the source in a structured format.");
        return new MozatAJAX(result);
    }

    @ResponseBody
    @ApiOperation(value = "get banners", response = MozatAJAX.class, notes = "得到横幅列表")
    @RequestMapping(value = "/banners", method = {RequestMethod.GET})
    public MozatAJAX banners() {
        List<String> pictureURLs = new ArrayList<>();
        pictureURLs.add("https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwj3uICFmtvVAhUEPY8KHUA3AucQjRwIBw&url=http%3A%2F%2Fwww.shenghuochn.com%2Fshishang%2F240.html&psig=AFQjCNHdPKobKGrW6u5svniu6c8qriiyIw&ust=1502953973450256");
        pictureURLs.add("https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwiQguiXmtvVAhVEL48KHSrwCHUQjRwIBw&url=https%3A%2F%2Fkknews.cc%2Ffashion%2Fmgpvqxz.html&psig=AFQjCNHdPKobKGrW6u5svniu6c8qriiyIw&ust=1502953973450256");
        pictureURLs.add("https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjvxvCdmtvVAhXEqY8KHYezCowQjRwIBw&url=http%3A%2F%2Fwww.nipic.com%2Fshow%2F3%2F64%2F4e2c8833accb12bf.html&psig=AFQjCNHdPKobKGrW6u5svniu6c8qriiyIw&ust=1502953973450256");
        pictureURLs.add("https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwi2vK2jmtvVAhWMQI8KHSB7DBgQjRwIBw&url=http%3A%2F%2Fwww.taopic.com%2Fpsd%2F201012%2F15031.html&psig=AFQjCNHdPKobKGrW6u5svniu6c8qriiyIw&ust=1502953973450256");
        pictureURLs.add("https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=&url=http%3A%2F%2Fwww.lanimg.com%2Fphoto%2F201510%2F693238.html&psig=AFQjCNHdPKobKGrW6u5svniu6c8qriiyIw&ust=1502953973450256");
        List<String> pictureLinks = new ArrayList<>();
        pictureLinks.add("http://172.28.0.149:8109/SillyHatSpringMVC/banners/h5/1");
        pictureLinks.add("http://172.28.0.149:8109/SillyHatSpringMVC/banners/h5/2");
        pictureLinks.add("http://172.28.0.149:8109/SillyHatSpringMVC/banners/h5/3");
        pictureLinks.add("http://172.28.0.149:8109/SillyHatSpringMVC/banners/h5/4");
        pictureLinks.add("http://172.28.0.149:8109/SillyHatSpringMVC/banners/h5/5");
        List<Map<String,Object>> result = new ArrayList<>();
        for (int i = 0; i < pictureURLs.size(); i++) {
            Map<String,Object> resultParams = new HashMap<>();
            resultParams.put("image_url",pictureURLs.get(i));
            resultParams.put("image_link",pictureLinks.get(i));
            result.add(resultParams);
        }
        return new MozatAJAX(result);
    }


    @ResponseBody
    @ApiOperation(value = "get products", response = MozatAJAX.class, notes = "page:加载页;page_size:当前页加载记录数，默认20;category:分类")
    @RequestMapping(value = "/products", method = {RequestMethod.GET})
    public MozatAJAX products(@RequestParam("uid")Long uid,@RequestParam("page") Long page,@RequestParam("page_size") Long page_size,@RequestParam("category")Long category) {
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> resultParams = new HashMap<>();
        resultParams.put("shop_item_id",1008);
        resultParams.put("shop_item_name","Velle Ruffed");
        resultParams.put("brand_name","The Closet Lover");
        resultParams.put("shop_item_group_id",80);
        resultParams.put("current_price",3390);
        resultParams.put("color","red");
        resultParams.put("image","https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjv8rjFsdvVAhWJuY8KHUUkAP4QjRwIBw&url=https%3A%2F%2Fsites.google.com%2Fsite%2F5d18charlottema&psig=AFQjCNEazuEfrqNobuvimoJm_WzSwh-YRw&ust=1502960292018086");
        resultParams.put("recommend_reason","HOT");
        result.add(resultParams);
        resultParams = new HashMap<>();
        resultParams.put("shop_item_id",1009);
        resultParams.put("shop_item_name","HAHA");
        resultParams.put("brand_name","Silly Hat");
        resultParams.put("shop_item_group_id",81);
        resultParams.put("current_price",9999999);
        resultParams.put("color","orange");
        resultParams.put("image","https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjjvs3ysdvVAhXJQI8KHZrzDjgQjRwIBw&url=https%3A%2F%2Fkenengba.com%2Fpost%2F369.html&psig=AFQjCNEazuEfrqNobuvimoJm_WzSwh-YRw&ust=1502960292018086");
        resultParams.put("recommend_reason","NOW Ashenme");
        result.add(resultParams);
        return new MozatAJAX(result);
    }

    @ResponseBody
    @ApiOperation(value = "get product", response = MozatAJAX.class, notes = "product_id:product主键ID")
    @RequestMapping(value = "/product/{product_id}/shop_item_group_id/{shop_item_group_id}", method = {RequestMethod.GET})
    public MozatAJAX product(@RequestParam("uid")Long uid,@PathVariable Long product_id,@PathVariable Long shop_item_group_id) {
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> products = new ArrayList<>();
        result.put("shop_item_group_id","80");

        List<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("orange");
        colors.add("yellow");
        result.put("colors",colors);

        List<String> sizes = new ArrayList<>();
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");
        sizes.add("XL");
        sizes.add("XXL");
        sizes.add("3XL");
        sizes.add("4XL");
        sizes.add("5XL");
        sizes.add("6XL");
        result.put("sizes",sizes);

        List<Map<String,Object>> colorSizes = new ArrayList<>();
        for (int i = 0; i < colors.size(); i++) {
            for (int j = 0; j < sizes.size(); j++) {
                Map<String,Object> colorSize = new HashMap<>();
                colorSize.put("shop_item_id",1000 + i);
                colorSize.put("color",colors.get(i));
                colorSize.put("size",sizes.get(j));
                colorSize.put("quatity",i);
                colorSizes.add(colorSize);
            }
        }
        result.put("inventory",colorSizes);


        List<String> pictureURLs = new ArrayList<>();
        pictureURLs.add("https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwj3uICFmtvVAhUEPY8KHUA3AucQjRwIBw&url=http%3A%2F%2Fwww.shenghuochn.com%2Fshishang%2F240.html&psig=AFQjCNHdPKobKGrW6u5svniu6c8qriiyIw&ust=1502953973450256");
        pictureURLs.add("https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwiQguiXmtvVAhVEL48KHSrwCHUQjRwIBw&url=https%3A%2F%2Fkknews.cc%2Ffashion%2Fmgpvqxz.html&psig=AFQjCNHdPKobKGrW6u5svniu6c8qriiyIw&ust=1502953973450256");
        pictureURLs.add("https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjvxvCdmtvVAhXEqY8KHYezCowQjRwIBw&url=http%3A%2F%2Fwww.nipic.com%2Fshow%2F3%2F6æ4%2F4e2c8833accb12bf.html&psig=AFQjCNHdPKobKGrW6u5svniu6c8qriiyIw&ust=1502953973450256");
        pictureURLs.add("https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwi2vK2jmtvVAhWMQI8KHSB7DBgQjRwIBw&url=http%3A%2F%2Fwww.taopic.com%2Fpsd%2F201012%2F15031.html&psig=AFQjCNHdPKobKGrW6u5svniu6c8qriiyIw&ust=1502953973450256");
        pictureURLs.add("https://www.google.com.sg/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=&url=http%3A%2F%2Fwww.lanimg.com%2Fphoto%2F201510%2F693238.html&psig=AFQjCNHdPKobKGrW6u5svniu6c8qriiyIw&ust=1502953973450256");

        Map<String,Object> productParams = new HashMap<>();
        productParams.put("shop_item_id",1009);
        productParams.put("shop_item_name","HAHA");
        productParams.put("brand_name","Silly Hat");
        productParams.put("current_price",9999999);
        productParams.put("original_price",100000099);
        productParams.put("cashback",8030);
        productParams.put("color","orange");
        productParams.put("images",pictureURLs);
        productParams.put("group_id",80);
        productParams.put("currency","SGD");
        productParams.put("recommend_reason","NOW Ashenme");
        products.add(productParams);
        result.put("products",products);
        productParams = new HashMap<>();
        productParams.put("shop_item__id",1010);
        productParams.put("shop_item_name","HAHA");
        productParams.put("brand_name","Silly Hat");
        productParams.put("current_price",9999999);
        productParams.put("original_price",100000099);
        productParams.put("cashback",8030);
        productParams.put("color","red");
        productParams.put("images",pictureURLs);
        productParams.put("group_id",80);
        productParams.put("currency","SGD");
        productParams.put("recommend_reason","NOW Ashenme");
        products.add(productParams);
        result.put("products",products);
        productParams = new HashMap<>();
        productParams.put("shop_item_id","1011");
        productParams.put("shop_item_name","HEIHEI");
        productParams.put("brand_name","LALA LALA");
        productParams.put("current_price",88888.99);
        productParams.put("original_price",99999.99);
        productParams.put("cashback",100);
        productParams.put("color","yellow");
        productParams.put("images",pictureURLs);
        productParams.put("group_id",81);
        productParams.put("currency","SGD");
        productParams.put("recommend_reason","NOW Ashenme");
        products.add(productParams);
        result.put("products",products);
        return new MozatAJAX(result);
    }

    @ResponseBody
    @ApiOperation(value = "get product detail", response = MozatAJAX.class, notes = "product_id:product主键ID")
    @RequestMapping(value = "/product/{product_id}/detail", method = {RequestMethod.GET})
    public MozatAJAX product(@RequestParam("uid")Long uid,@PathVariable String product_id) {
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> resultParams = new HashMap<>();
        resultParams.put("description","Stripe users in the United States, Canada, Europe, Hong Kong, and Singapore can process card payments that require authentication with 3D Secure using Sources—a single integration path for creating payments using any supported method. Users in other countries that Stripe supports can request an invite.");
        resultParams.put("composition_and_care","Within the scope of Sources, 3D Secure card payments are are a pull-based, single-use and synchronous method of payment. This means that your integration takes action to debit the amount from the customer’s card and there is immediate confirmation about the success or failure of a payment.");
        resultParams.put("size_guide","3D Secure provides a layer of protection against fraudulent payments that is supported by most card-issuing banks. Unlike regular card payments, 3D Secure requires cardholders to complete an additional verification step with the issuer. Users are covered by a liability shift against fraudulent payments that have been authenticated with 3D Secure as the card issuer assumes full responsibility.\n");
        resultParams.put("delivery_return","While 3D Secure protects you from fraud, it requires your customers to complete additional steps during the payment process that could impact their checkout experience. For instance, if a customer does not know their 3D Secure information, they might not be able to complete the payment.");
        resultParams.put("coupons","When considering the use of 3D Secure, you might find the right balance is to use it only in situations where there is an increased risk of fraud, or if the customer’s card would be declined without it.");
        result.add(resultParams);
        return new MozatAJAX(result);
    }

//
//    @ResponseBody
//    @ApiOperation(value = "like product", response = MozatAJAX.class, notes = "product_id:product主键ID")
//    @RequestMapping(value = "/like/{product_id}", method = {RequestMethod.GET})
//    public MozatAJAX like(@PathVariable String product_id) {
//        return new MozatAJAX(result);
//    }


}
