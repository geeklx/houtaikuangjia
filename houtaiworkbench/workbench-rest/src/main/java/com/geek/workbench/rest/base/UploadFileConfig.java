package com.geek.workbench.rest.base;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @Description
 * @Author gaojian
 * @Date 2021/10/26 15:23
 * @Version V1.0
 */
@Configuration
public class UploadFileConfig {

//    @Bean
//    public MultipartConfigElement multipartConfigElement(){
//        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
//        multipartConfigFactory.setMaxFileSize( 1024 * 1024 * 1024);
//        multipartConfigFactory.setMaxRequestSize( 1024 * 1024 * 1024);
////        String location = System.getProperty("user.dir") + "/data/tmp";
////        File file = new File(location);
////        if(!file.exists()){
////            file.mkdirs();
////        }
////        multipartConfigFactory.setLocation(location);
//        return multipartConfigFactory.createMultipartConfig();
//    }
}
