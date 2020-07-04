package com.bookhelper.together.repository;

import com.bookhelper.together.domain.Manager;
import com.bookhelper.together.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepository {

    private final EntityManager em;

    public void save(Room room){
        em.persist(room);
    }

    public Room findOne(Long id){
        return em.find(Room.class, id);
    }

    public List<Room> findAll(){
        return em.createQuery("select r from Room r", Room.class)
                .getResultList();
    }

    public List<Room> findByManager(String manager){
        return em.createQuery("select r from Room r where r.manager = :manager")
                .setParameter("manager", manager)
                .getResultList();

    }

    public List<Room> findByTitleLike(String title){
        title = "%"+title+"%";
        return em.createQuery("select r from Room r where r.title like :title")
                .setParameter("title", title)
                .getResultList();

    }

}
