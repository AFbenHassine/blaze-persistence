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

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.PaginatedCriteriaBuilder;
import com.blazebit.persistence.testsuite.entity.IdClassEntity;
import com.blazebit.persistence.testsuite.entity.IdClassEntityId;
import com.blazebit.persistence.testsuite.tx.TxVoidWork;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.IdClass;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.googlecode.catchexception.CatchException.verifyException;
import static com.sun.tools.internal.xjc.reader.Ring.add;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Christian Beikov
 * @since 1.3.0
 */
public class IdClassValueQueryTest extends AbstractCoreTest {

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
                IdClassEntity e1 = new IdClassEntity(1,"1",  1);
                IdClassEntity e2 = new IdClassEntity(2,"2",  2);
                IdClassEntity e3 = new IdClassEntity(3,"3",  3);
                IdClassEntity e4 = new IdClassEntity(4,"4",  4);
                IdClassEntity e5 = new IdClassEntity(5,"5",  5);

                em.persist(e1);
                em.persist(e2);
                em.persist(e3);
                em.persist(e4);
                em.persist(e5);
            }
        });
    }



    @Test
    public void testValueQuery() {
        IdClassEntity e1 = new IdClassEntity(1,"1",  1);
        IdClassEntity e2 = new IdClassEntity(2,"2",  2);
        IdClassEntity e3 = new IdClassEntity(3,"3",  3);
        IdClassEntity e4 = new IdClassEntity(4,"4",  4);
        IdClassEntity e5 = new IdClassEntity(5,"5",  5);

        List entities = new ArrayList();
        entities.add(e1);
        entities.add(e2);
        entities.add(e3);
        entities.add(e4);
        entities.add(e5);

        int i = 0;
        String string = "";
//        CriteriaBuilder<IdClassEntityId> cb = (CriteriaBuilder<IdClassEntityId>) cbf.create(em, IdClassEntity.class)
//                .fromIdentifiableValues(IdClassEntity.class,"myValue",entities)
//                .select("myValue.key1");
//        TypedQuery<IdClassEntityId> typedQuery = cb.getQuery();
//        List<IdClassEntityId> idClassEntities = typedQuery.getResultList();
//        for (IdClassEntityId a : idClassEntities) {
//            i = i+a.getKey1();
//        }
//
//        for (IdClassEntityId b : idClassEntities) {
//            string = string + b.getKey2();
//        }




        /**
         * Code below works for the single Id case.
         */
//        int i = 0;
        CriteriaBuilder<Integer> cb = (CriteriaBuilder<Integer>) cbf.create(em, IdClassEntity.class)
                .fromIdentifiableValues(IdClassEntity.class,"myValue",entities)
                .select("myValue.key1");
        TypedQuery<Integer> typedQuery = cb.getQuery();
        List<Integer> idClassEntities = typedQuery.getResultList();

        for (Integer a : idClassEntities) {
            i = i+a;
        }

        for (Integer b : idClassEntities) {
            string = string + b;
        }

        Assert.assertTrue(i==15);

    }
}
