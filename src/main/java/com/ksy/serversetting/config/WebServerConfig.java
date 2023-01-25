package com.ksy.serversetting.config;

import com.ksy.serversetting.controller.converter.HotelRoomConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.util.UrlPathHelper;

import java.util.List;

@Configuration
public class WebServerConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true) // / 구
                .addPathPrefix("/v1", HandlerTypePredicate.forAnnotation(RestController.class, Controller.class))
                .setPathMatcher(new AntPathMatcher())
                .setUrlPathHelper(new UrlPathHelper());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // /ex?contentType=json
        configurer.parameterName("contentType")
                .ignoreAcceptHeader(true)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/html/**")
                .addResourceLocations("classpath:/static/html/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("www.ksy.co.kr")
                .allowedMethods("GET","POST","PUT")
                .allowedHeaders("*")
                .maxAge(24*60*60);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new HotelRoomConverter()); // 컨버터 등록
    }
}
