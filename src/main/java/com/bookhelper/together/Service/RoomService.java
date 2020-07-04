package com.bookhelper.together.Service;

import com.bookhelper.together.domain.Room;
import com.bookhelper.together.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional(readOnly = false)
    public Long create(Room room){
        roomRepository.save(room);
        return room.getId();
    }

    public Room findRoom(Long roomId){
        return roomRepository.findOne(roomId);
    }

    public List<Room> findRooms(){
        return roomRepository.findAll();
    }

    public List<Room> findRoomByManager(String manager){
        return roomRepository.findByManager(manager);
    }

    public List<Room> findRoomsByTitle(String title){
        return roomRepository.findByTitleLike(title);
    }



}
