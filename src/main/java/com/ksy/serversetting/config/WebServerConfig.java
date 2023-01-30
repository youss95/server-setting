package com.ksy.serversetting.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksy.serversetting.controller.converter.HotelRoomNumberConverter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterRegistration;
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
        registry.addConverter(new HotelRoomNumberConverter()); // 컨버터 등록
    }

    @Bean(value = "localResolver")
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter()); //json 처리
        converters.add(new MappingJackson2XmlHttpMessageConverter());
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY); //리스트 값이 있는 속성만 직렬화
        return objectMapper;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("localw");
        registry.addInterceptor(localeChangeInterceptor).excludePathPatterns("/favicon.ico").addPathPatterns("/**");
    }

    //서블릿 필터 빈으로 등록
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> defaultCharacterEncodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("utf-8");
        encodingFilter.setForceEncoding(true); //utf-8로 인코딩

        FilterRegistrationBean<CharacterEncodingFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(encodingFilter);
        filterBean.addInitParameter("paramName","paramValue");
        filterBean.addUrlPatterns("*");
        filterBean.setOrder(1);
        return filterBean;
    }
}
