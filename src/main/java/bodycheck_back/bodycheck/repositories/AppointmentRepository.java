package bodycheck_back.bodycheck.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bodycheck_back.bodycheck.models.entities.Appointment;
import bodycheck_back.bodycheck.models.entities.User;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
   List<Appointment> findAllByUserAndDate(User user, LocalDate date);

   List<Appointment> findAllByUserAndCustomerId(User user, Long customerId);

   @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND a.date = :date AND " +
         "((:startTime BETWEEN a.startTime AND a.endTime) OR " +
         "(:endTime BETWEEN a.startTime AND a.endTime) OR " +
         "(a.startTime BETWEEN :startTime AND :endTime) OR " +
         "(a.endTime BETWEEN :startTime AND :endTime))")
   List<Appointment> findConflictingAppointments(
         @Param("userId") Long userId,
         @Param("date") LocalDate date,
         @Param("startTime") LocalDateTime startTime,
         @Param("endTime") LocalDateTime endTime);
}
