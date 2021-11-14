package com.boba.bobabuddy.infrastructure;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mediatype.MessageResolver;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.DefaultCurieProvider;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.http.converter.json.SpringHandlerInstantiator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;

@Component
public class TsqBeanHandlerInstantiator extends SpringHandlerInstantiator {

    /** Logger. */
    protected static final Logger LOGGER = LoggerFactory.getLogger(TsqBeanHandlerInstantiator.class.getName());

    private final AutowireCapableBeanFactory beanFactory;

    private final EntityManager em;

    private List<Class<? extends EntityType>> tsqEntities = new ArrayList<>();

    /**
     * Create a new SpringHandlerInstantiator for the given BeanFactory.
     * @param beanFactory the target BeanFactory
     */
    public TsqBeanHandlerInstantiator(AutowireCapableBeanFactory beanFactory, EntityManager em) {
        super(beanFactory);
        this.beanFactory = beanFactory;
        this.em = em;
    }

    @PostConstruct
    public void init()
    {
        //We record all available entity for futur use.
        for (EntityType<?> entity : em.getMetamodel().getEntities()) {
            Class<? extends EntityType> entityClass = (Class<? extends EntityType>) entity.getJavaType();
            String className = entityClass.getName();
            tsqEntities.add(entityClass);
        }
    }

    @Override
    public JsonDeserializer<?> deserializerInstance(DeserializationConfig config, Annotated annotated, Class<?> implClass) {
        Class entityClass =  annotated.getRawType();
        LOGGER.debug("deserializerInstance : " + entityClass);
        return (JsonDeserializer<?>) this.beanFactory.createBean(implClass);
    }

    /** @since 4.3 */
    @Override
    public ObjectIdGenerator<?> objectIdGeneratorInstance(MapperConfig<?> config, Annotated annotated, Class<?> implClass) {
        Class entityClass =  annotated.getRawType();
        LOGGER.debug("objectIdGeneratorInstance : " + entityClass);
        return (ObjectIdGenerator<?>) this.beanFactory.createBean(implClass);
    }

    /** @since 4.3 */
    @Override
    public ObjectIdResolver resolverIdGeneratorInstance(MapperConfig<?> config, Annotated annotated, Class<?> implClass) {
        Class entityClass =  annotated.getRawType();
        if(!tsqEntities.contains(entityClass))
        {
            return super.resolverIdGeneratorInstance(config, annotated, implClass);
        }
        LOGGER.debug("resolverIdGeneratorInstance : " + entityClass);
        return new JpaEntityResolver(em, entityClass);
    }
}