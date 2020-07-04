package com.bookhelper.together.repository;

import com.bookhelper.together.domain.Invitation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InvitationRepository {

    private final EntityManager em;

    public void save (Invitation invitation){
        em.persist(invitation);
    }

    public Invitation findOne(Long id){
        return em.find(Invitation.class, id);
    }

    public List<Invitation> findAll(){
        return em.createQuery("select i from Invitation i", Invitation.class)
                .getResultList();
    }

    public List<Invitation> findByCode(String code){
        return em.createQuery("select i from Invitation i where i.code = :code", Invitation.class)
                .setParameter("code", code)
                .getResultList();
    }


}
