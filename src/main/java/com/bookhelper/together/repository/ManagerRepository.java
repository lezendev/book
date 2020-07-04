package com.bookhelper.together.repository;


import com.bookhelper.together.domain.Invitation;
import com.bookhelper.together.domain.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ManagerRepository {

    private final EntityManager em;

    public void save (Manager manager){
        em.persist(manager);
    }

    public Manager findOne(Long id){
        return em.find(Manager.class, id);
    }

    public List<Manager> findAll(){
        return em.createQuery("select m from Manager m", Manager.class)
                .getResultList();
    }

    //뭘로 검색하지???
    /*public List<Manager> findByCode(String code){
        return em.createQuery("select m from Manager m where m.code = :code", Manager.class)
                .setParameter("code", code)
                .getResultList();
    }*/
}
