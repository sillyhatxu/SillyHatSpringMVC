package com.sillyhat.springmvc.core.method;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * CurrencyMethod
 *
 * @author 徐士宽
 * @date 2017/3/30 15:47
 */
@Component("currencyMethod")
public class CurrencyMethod implements TemplateMethodModelEx {

    public Object exec(List list) throws TemplateModelException {
        if(CollectionUtils.isNotEmpty(list)&&(list.get(0)!=null)&& StringUtils.isNotEmpty(Objects.toString(list.get(0)))){
            boolean flag = false;
            boolean flag2 = false;
            if (list.size() == 2) {
                if (list.get(1) != null){
                    flag = Boolean.valueOf(Objects.toString(list.get(1)));
                }
            } else if (list.size() > 2) {
                if (list.get(1) != null){
                    flag = Boolean.valueOf(Objects.toString(list.get(1)));
                }
                if (list.get(2) != null){
                    flag2 = Boolean.valueOf(Objects.toString(list.get(2)));
                }
            }
            //数据应该从数据库或者缓存读取
            //下面代码需要变动

            //Setting setting = SettingUtils.get();
//            BigDecimal bd = new BigDecimal(list.get(0).toString());
//            String priceStr = setting.setPriceScale(bd).toString();
//            if (flag){
//                priceStr = setting.getCurrencySign() + priceStr;//currencySign货币符号
//            }
//            if (flag2){
//                priceStr = priceStr + setting.getCurrencyUnit();//currencyUnit货币单位
//            }
            BigDecimal bd = new BigDecimal(String.valueOf(list.get(0)));
            bd.setScale(2,BigDecimal.ROUND_DOWN);
            String realPrice = "";
            if (flag) {
                realPrice = "$" + bd.toString();//currencySign货币符号
//            }
                if (flag2) {
                    realPrice = realPrice + "元";//currencyUnit货币单位
                }
            }
            return new SimpleScalar(realPrice);
        }
        return null;
    }
}