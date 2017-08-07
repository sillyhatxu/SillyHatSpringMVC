package com.sillyhat.stripe.utils;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * JunitTestSupport
 *
 * @author 徐士宽
 * @date 2017/3/29 10:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        locations = {
                "classpath:applicationContext.xml",
                "classpath:applicationContext-mvc.xml",
        }
)
public class JunitTestSupport {

}
