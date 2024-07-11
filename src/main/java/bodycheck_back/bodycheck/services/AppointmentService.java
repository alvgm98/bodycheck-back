package bodycheck_back.bodycheck.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bodycheck_back.bodycheck.auth.services.AuthService;
import bodycheck_back.bodycheck.models.dtos.AppointmentDTO;
import bodycheck_back.bodycheck.models.entities.Appointment;
import bodycheck_back.bodycheck.models.entities.Customer;
import bodycheck_back.bodycheck.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

   private final AppointmentRepository appointmentRepository;
   private final AuthService authService;
   private final CustomerService customerService;

   public List<AppointmentDTO> findAllByDate(LocalDate date) {
      List<Appointment> appointments = appointmentRepository.findAllByUserAndDate(authService.getUserFromToken(), date);

      return appointments.stream().map(this::convertToDto).collect(Collectors.toList());
   }

   public List<AppointmentDTO> findAllByCustomer(Customer customer) {
      List<Appointment> appointments = appointmentRepository.findAllByUserAndCustomer(authService.getUserFromToken(),
            customer);

      return appointments.stream().map(this::convertToDto).collect(Collectors.toList());
   }

   @Transactional
   public AppointmentDTO create(Appointment appointment) {
      appointment.setUser(authService.getUserFromToken());

      return convertToDto(appointmentRepository.save(appointment));
   }

   public AppointmentDTO convertToDto(Appointment appointment) {
      // Tengo que acceder al Customer mediante el Service ya que el FetchType.EAGER no me hace ni PUTO CASO :)
      Customer customer = customerService.findById(appointment.getCustomer().getId()).orElseThrow();

      return AppointmentDTO.builder()
            .id(appointment.getId())
            .customer(customerService.convertToDto(customer))
            .dateTime(LocalDateTime.of(appointment.getDate(), appointment.getTime()))
            .duration(appointment.getDuration())
            .reason(appointment.getReason())
            .observations(appointment.getObservations())
            .build();
   }
}
