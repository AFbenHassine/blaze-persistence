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
import com.blazebit.persistence.testsuite.base.jpa.category.NoDatanucleus;
import com.blazebit.persistence.testsuite.base.jpa.category.NoEclipselink;
import com.blazebit.persistence.testsuite.base.jpa.category.NoOpenJPA;
import com.blazebit.persistence.testsuite.base.jpa.category.NoOracle;
import com.blazebit.persistence.testsuite.entity.IdClassEntity;
import com.blazebit.persistence.testsuite.entity.IntIdEntity;
import com.blazebit.persistence.testsuite.tx.TxVoidWork;
import com.blazebit.persistence.testsuite.tx.TxWork;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Christian Beikov
 * @since 1.3.0
 */
public class IdClassReturningTest extends AbstractCoreTest {

    @Entity
    @Table(name = "pseudo_id_class_entity")
    class PseudoIdClassEntity implements Serializable {
        private static final long serialVersionUID = 2L;


        private Integer pseudoKey1;
        private String pseudoKey2;
        private Integer pseudoValue;
        private Set<PseudoIdClassEntity> children = new HashSet<>();

        public PseudoIdClassEntity() {
        }

        public PseudoIdClassEntity(Integer key1, String key2, Integer value) {
            this.pseudoKey1 = key1;
            this.pseudoKey2 = key2;
            this.pseudoValue = value;
        }

        @Id
        @Column(name = "pseudoKey1", nullable = false)
        public Integer getPseudoKey1() {
            return pseudoKey1;
        }

        public void setPseudoKey1(Integer pseudoKey1) {
            this.pseudoKey1 = pseudoKey1;
        }

        @Column(name = "pseudoKey2", nullable = false)
        public String getPseudoKey2() {
            return pseudoKey2;
        }

        public void setPseudoKey2(String pseudoKey2) {
            this.pseudoKey2 = pseudoKey2;
        }

        @Basic(optional = false)
        @Column(name = "pseudoValue", nullable = false)
        public Integer getPseudoValue() {
            return pseudoValue;
        }

        public void setPseudoValue(Integer pseudoValue) {
            this.pseudoValue = pseudoValue;
        }

        @ManyToMany
        @JoinTable(name = "pseudo_id_class_entity_children", joinColumns = {
                @JoinColumn(name = "child_pseudo_key1", nullable = false, referencedColumnName = "pseudoKey1"),
        }, inverseJoinColumns = {
                @JoinColumn(name = "parent_pseudo_key1", nullable = false, referencedColumnName = "pseudoKey1"),
        })
        public Set<PseudoIdClassEntity> getChildren() {
            return children;
        }

        public void setChildren(Set<PseudoIdClassEntity> children) {
            this.children = children;
        }
    }

    @Override
    protected Class<?>[] getEntityClasses() {
        return new Class<?>[]{
                IdClassEntity.class,
                IdClassReturningTest.PseudoIdClassEntity.class
        };
    }

    @Override
    public void setUpOnce() {
        cleanDatabase();
        transactional(new TxVoidWork() {
            @Override
            public void work(EntityManager em) {
                PseudoIdClassEntity pe1 = new PseudoIdClassEntity(4,"c",4);
                em.persist(pe1);
                em.flush();

                PseudoIdClassEntity pe2 = new PseudoIdClassEntity(5,"d",5);
                em.persist(pe2);
            }
        });
    }

    @Test
    @Category({ NoOracle.class, NoDatanucleus.class, NoEclipselink.class, NoOpenJPA.class })
    public void testReturningWithInsert() {
        ReturningResult<Integer> result = transactional(new TxWork<ReturningResult<Integer>>() {
            @Override
            public ReturningResult<Integer> work(EntityManager em) {
                final InsertCriteriaBuilder<IdClassEntity> cb3 = cbf.insert(em,IdClassEntity.class);
                cb3.from(PseudoIdClassEntity.class,"pe");
                cb3.bind("key1").select("pe.pseudoKey1");
                cb3.bind("key2").select("pe.pseudoKey2");
                cb3.bind("value").select("pe.pseudoValue");
                cb3.orderByAsc("pe.pseudoKey1");

                return cb3.executeWithReturning("key1", Integer.class);
            }
        });
//        Assert.assertEquals(5,result.getLastResult().intValue());
        Assert.assertEquals(2,result.getUpdateCount());
    }
}

