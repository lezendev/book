package com.bookhelper.together.repository;

import com.bookhelper.together.domain.BannedTime;
import com.bookhelper.together.domain.BookingTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookingRepository {

    private final EntityManager em;

    public void save (BookingTime bookingTime){
        em.persist(bookingTime);
    }

    public BookingTime findOne(Long id){
        return em.find(BookingTime.class, id);
    }

    public List<BookingTime> findAll(){
        return em.createQuery("select t from BookingTime t", BookingTime.class)
                .getResultList();
    }

    // 무엇을 검색할것인지?
    public List<BookingTime> findByRoom(Long roomId){
        return em.createQuery("select t from BookingTime t where t.room.id = :room order by t.timetable.localDate, t.timetable.startTime desc", BookingTime.class)
                .setParameter("room", roomId)
                .getResultList();

    }

    public List<BookingTime> findByTitleLike(String title){
        title = "%"+title+"%";
        return em.createQuery("select t from BookingTime t where t.title like :title")
                .setParameter("title", title)
                .getResultList();

    }

    public Object countDuplicateTime(BookingTime bookingTime){

        LocalDate localDate = bookingTime.getTimetable().getLocalDate();
        String startTime = bookingTime.getTimetable().getStartTime();
        String endTime = bookingTime.getTimetable().getEndTime();
        Long roomId = bookingTime.getRoom().getId();

        Object cnt = em.createQuery("select count(t) from BookingTime t where t.timetable.localDate = :localDate " +
                "and t.timetable.startTime < :endTime " +
                "and t.timetable.endTime > : startTime " +
                "and t.room.id = :roomId")
                .setParameter("localDate", localDate)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .setParameter("roomId", roomId)
                .getSingleResult();

        return cnt;

    }

    public void remove(BookingTime bookingTime){
        em.remove(bookingTime);
    }


}
