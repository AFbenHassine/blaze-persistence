/*
 * Copyright 2014 - 2018 Blazebit.
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

package com.blazebit.persistence.testsuite;

import com.blazebit.persistence.*;
import com.blazebit.persistence.testsuite.entity.IdClassEntity;
import com.blazebit.persistence.testsuite.tx.TxVoidWork;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Christian Beikov
 * @since 1.3.0
 */
public class IdClassReturningTest extends AbstractCoreTest {

    @Override
    protected Class<?>[] getEntityClasses() {
        return new Class<?>[]{
                IdClassEntity.class
        };
    }

    @Override
    public void setUpOnce() {
        cleanDatabase();
        transactional(new TxVoidWork() {
            @Override
            public void work(EntityManager em) {
                IdClassEntity e1 = new IdClassEntity(1,"a",  1);
                IdClassEntity e2 = new IdClassEntity(2,"b",  2);
                IdClassEntity e3 = new IdClassEntity(3,"b",  3);

                em.persist(e1);
                em.persist(e2);
                em.persist(e3);

            }
        });
    }



    @Test
    public void testReturning() {
//        IdClassEntity e2 = new IdClassEntity(2, "2", 4);
//        IdClassEntity e3 = new IdClassEntity(3, "3", 4);
//        IdClassEntity e4 = new IdClassEntity(4, "4", 4);
//        IdClassEntity e5 = new IdClassEntity(5, "5", 5);
//
//        List<IdClassEntity> entities = new ArrayList<>();
//        entities.add(e2);
//        entities.add(e3);
//        entities.add(e4);
//        entities.add(e5);
//
//        InsertCriteriaBuilder<IdClassEntity> cb = cbf.insert(em, IdClassEntity.class)
//                .fromIdentifiableValues(IdClassEntity.class, "entity", entities)
//                .bind("key1").select("entity.key1").where("entity.key1").isNotNull()
//                .bind("key2").select("entity.key2").where("entity.key2").isNotNull()
//                .bind("value").select("entity.value").where("entity.value").isNotNull();
//
//        ReturningResult<IdClassEntity> result = cb2.executeWithReturning(new ReturningObjectBuilder<IdClassEntity>() {
//            @Override
//            public void applyReturning(SimpleReturningBuilder returningBuilder) {
//                returningBuilder.returning("key1");
//
//            }
//
//            @Override
//            public IdClassEntity build(Object[] tuple) {
//                return new IdClassEntity();
//            }
//
//            @Override
//            public List<IdClassEntity> buildList(List<IdClassEntity> list) {
//                return list;
//            }
//        });
//
//        List<IdClassEntity> createdIdClassEntities = result.getResultList();

        DeleteCriteriaBuilder<IdClassEntity> cb2 = cbf.delete(em, IdClassEntity.class,"entity")
                //Why can I not use .eqExpression instead of .like().value("b").noEscape?
                .where("entity.key2").like().value("b").noEscape();
        ReturningResult<String> result = cb2.executeWithReturning("key2",String.class);
        List<String> names = result.getResultList();




        Assert.assertTrue(names.size()==2);
        Assert.assertTrue(names.contains("b"));

    }


        /**
         * Code below works for the single Id case.
         */
//        int i = 0;
//        CriteriaBuilder<Integer> cb = (CriteriaBuilder<Integer>) cbf.create(em, IdClassEntity.class)
//                .fromIdentifiableValues(IdClassEntity.class,"myValue",entities)
//                .select("myValue.key1");
//        TypedQuery<Integer> typedQuery = cb.getQuery();
//        List<Integer> idClassEntities = typedQuery.getResultList();
//
//        for (Integer a : idClassEntities) {
//            i = i+a;
//        }
//
//        for (Integer b : idClassEntities) {
//            string = string + b;
//        }
//
//        Assert.assertTrue(i==15);
//
//    }
}
