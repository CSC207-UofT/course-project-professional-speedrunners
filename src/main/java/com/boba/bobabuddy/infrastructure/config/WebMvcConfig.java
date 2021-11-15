package com.boba.bobabuddy.infrastructure.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public ObjectMapper objectMapper() {
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