/*
 * Copyright 2014 Blazebit.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blazebit.persistence.impl;

import com.blazebit.persistence.SubqueryBuilder;
import com.blazebit.persistence.impl.expression.ExpressionFactory;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;

/**
 *
 * @author ccbem
 */
public class SubqueryBuilderImpl<T> extends AbstractBaseQueryBuilder<Tuple, SubqueryBuilder<T>> implements SubqueryBuilder<T> {
    private final T result;
    private final SubqueryBuilderListener listener;
    

    public SubqueryBuilderImpl(CriteriaBuilderFactoryImpl cbf, EntityManager em, Class<?> fromClazz, String alias, T result, ParameterManager parameterManager, AliasManager aliasManager, SubqueryBuilderListener listener, ExpressionFactory expressionFactory) {
        super(cbf, em, Tuple.class, fromClazz, alias, parameterManager, aliasManager, expressionFactory);
        this.result = result;
        this.listener = listener;
    }
    
    @Override
    public T end() {
        if(selectManager.getSelectInfoCount() == 0){
            throw new IllegalStateException("A subquery without a select clause is not allowed");
        }
        listener.onBuilderEnded(this);
        return result;
    }

    @Override
    public SubqueryBuilder<T> select(String expression) {
        return (SubqueryBuilder<T>) super.select(expression);
    }

    @Override
    public SubqueryBuilder<T> select(String expression, String alias) {
        return (SubqueryBuilder<T>) super.select(expression, alias);
    }

    @Override
    public T getResult() {
        return result;
    }
}