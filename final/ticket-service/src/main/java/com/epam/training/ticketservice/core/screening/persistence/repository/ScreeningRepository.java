package com.epam.training.ticketservice.core.screening.persistence.repository;

import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Integer> {
    Optional<Screening> getScreeningByMovieIdAndRoomIdAndStartTime(Integer movieId, Integer roomId, LocalDateTime startTime);
    long deleteScreeningByMovieIdAndRoomIdAndStartTime(Integer movieId, Integer roomId, LocalDateTime startTime);
    Optional<Screening> getFirstByStartTimeIsLessThanEqualAndRoomIdEquals(LocalDateTime startTime, Integer roomId);
    Optional<Screening> getFirstByStartTimeIsGreaterThanEqualAndRoomIdEquals(LocalDateTime startTime, Integer roomId);
}
