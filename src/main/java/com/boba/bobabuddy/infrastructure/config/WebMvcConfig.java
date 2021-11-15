package com.boba.bobabuddy.infrastructure.config;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.infrastructure.TsqBeanHandlerInstantiator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.DefaultCurieProvider;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.logging.Handler;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
        return objectMapper;
    }

//    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(@Autowired ObjectMapper objectMapper, @Autowired TsqBeanHandlerInstantiator tsqBeanHandlerInstantiator){
//        MappingJackson2HttpMessageConverter bean = new MappingJackson2HttpMessageConverter();
//        bean.setObjectMapper(objectMapper);
//        bean.registerObjectMappersForType(Object.class, m -> m.put(MediaType.APPLICATION_JSON, tsqObjectMapper(tsqBeanHandlerInstantiator)));
//        return bean;
//    }

}