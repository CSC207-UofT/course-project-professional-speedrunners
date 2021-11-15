package com.boba.bobabuddy.infrastructure;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.UUID;

/**
 * @author fta on 20.12.15.
 */

// not used
public class JpaEntityResolver extends SimpleObjectIdResolver {

    private EntityManager em;

    private Class<? extends EntityType> entityClass;

    public JpaEntityResolver() {

    }

    public JpaEntityResolver(EntityManager em, Class<? extends EntityType> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey id) {
        Object resolved = super.resolveId(id);
        if (resolved == null) {
            resolved = findEntityById(id);
            bindItem(id, resolved);
        }
        return resolved;
    }

    private Object findEntityById(ObjectIdGenerator.IdKey idKey) {
        UUID id = (UUID) idKey.key;
        return em.find(entityClass, id);
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        return new JpaEntityResolver(this.em, this.entityClass);
    }
}