package com.sillyhat.base.core.method;

import com.sillyhat.base.utils.SpringUtils;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CurrencyMethod
 *
 * @author 徐士宽
 * @date 2017/3/30 15:47
 */
@Component("messageMethod")
public class MessageMethod implements TemplateMethodModelEx {

    public Object exec(List arguments) throws TemplateModelException {
        if (CollectionUtils.isNotEmpty(arguments)&& (arguments.get(0) != null)&& (StringUtils.isNotEmpty(arguments.get(0).toString()))) {
            String message = null;
            String argument = arguments.get(0).toString();
            if (arguments.size() > 1) {
                Object[] value = arguments.subList(1, arguments.size()).toArray();
                message = SpringUtils.getMessage(argument, value);
            } else {
                message = SpringUtils.getMessage(argument, new Object[0]);
            }
            return new SimpleScalar(message);
        }
        return null;
    }
}
