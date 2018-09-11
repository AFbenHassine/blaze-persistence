package com.blazebit.persistence.view.testsuite.IdClass;


import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.testsuite.entity.IdClassEntity;
import com.blazebit.persistence.testsuite.entity.IdClassEntityId;
import com.blazebit.persistence.testsuite.tx.TxVoidWork;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.testsuite.AbstractEntityViewTest;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

public class IdClassTest extends AbstractEntityViewTest {

    @EntityView(IdClassEntity.class)
    public interface IdClassEntityView {
        @IdMapping("key1")
        Integer getKey1();
        @IdMapping("key2")
        String getKey2();
    }

    @Override
    protected Class<?>[] getEntityClasses() {
        return new Class<?>[]{
                IdClassEntity.class};
    }

    @Override
    public void setUpOnce(){
        cleanDatabase();
        transactional(new TxVoidWork() {
            @Override
            public void work(EntityManager em) {
                IdClassEntityId id1 = new IdClassEntityId(4,"c");
                IdClassEntity e1 = new IdClassEntity(id1,4);


                em.persist(e1);
                em.flush();
                IdClassEntityId id2 = new IdClassEntityId(5,"d");
                IdClassEntity e2 = new IdClassEntity(id2,5);
                em.persist(e2);

            }
        });

//        List<IdClassEntityView> list = evm
//                .applySetting(setting,cb)
//                .getResultList();

    }

    @Test
    public void idClassFindTest(){
        EntityViewSetting<IdClassEntityView, CriteriaBuilder<IdClassEntityView>> setting = EntityViewSetting.create(IdClassEntityView.class);
        IdClassEntityId id1 = new IdClassEntityId(4,"c");
        IdClassEntity e1 = new IdClassEntity(id1,4);
        IdClassEntity idClassEntity = em.find(IdClassEntity.class, id1);
        evm.find(em,setting,id1);
    }

}
