package com.sillyhat.base.core.restapi;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author xushikuan
 * @version 1.0
 * @project SillyHatProject
 * @create 2016-12-27 11:08
 **/
@Configuration
@EnableSwagger
@EnableWebMvc
@ComponentScan(value = "com.sillyhat")
public class SwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(".*?");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("SillyHat API", "提供功能供第三方调用", "嘿咻傻帽的 Restful服务平台",
                "xushikuan1@mozat.com", "BSD", "SillyHat framework API License URL");
        return apiInfo;
    }
}