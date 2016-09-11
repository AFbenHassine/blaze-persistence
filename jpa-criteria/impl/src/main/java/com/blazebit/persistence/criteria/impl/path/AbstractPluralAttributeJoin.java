package com.blazebit.persistence.criteria.impl.path;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.PluralJoin;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.Type;

import com.blazebit.persistence.criteria.impl.BlazeCriteriaBuilderImpl;

/**
 *
 * @author Christian Beikov
 * @since 1.2.0
 */
public abstract class AbstractPluralAttributeJoin<O, C, E> extends AbstractJoin<O, E> implements PluralJoin<O, C, E> {

    private static final long serialVersionUID = 1L;

    public AbstractPluralAttributeJoin(BlazeCriteriaBuilderImpl criteriaBuilder, Class<E> javaType, AbstractPath<O> pathSource, Attribute<? super O, ?> joinAttribute, JoinType joinType) {
        super(criteriaBuilder, javaType, pathSource, joinAttribute, joinType);
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public PluralAttribute<? super O, C, E> getAttribute() {
        return (PluralAttribute<? super O, C, E>) super.getAttribute();
    }

    public PluralAttribute<? super O, C, E> getModel() {
        return getAttribute();
    }

    @Override
    protected ManagedType<E> getManagedType() {
        return isBasicCollection() ? null : (ManagedType<E>) getAttribute().getElementType();
    }

    public boolean isBasicCollection() {
        return Type.PersistenceType.BASIC.equals(getAttribute().getElementType().getPersistenceType());
    }

    @Override
    protected boolean isDereferencable() {
        return !isBasicCollection();
    }

    @Override
    protected boolean isJoinAllowed() {
        return !isBasicCollection();
    }
}