package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.domain.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository
{
    List<Room> findAll();
    Optional<Room> findById(Long id);
    Room save(Room user);
    void deleteById(Long id);
}
