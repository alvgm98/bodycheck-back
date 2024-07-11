package bodycheck_back.bodycheck.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bodycheck_back.bodycheck.models.entities.Appointment;
import bodycheck_back.bodycheck.models.entities.Customer;
import bodycheck_back.bodycheck.models.entities.User;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
   List<Appointment> findAllByUserAndDate(User user, LocalDate date);

   List<Appointment> findAllByUserAndCustomer(User user, Customer customer);
}
