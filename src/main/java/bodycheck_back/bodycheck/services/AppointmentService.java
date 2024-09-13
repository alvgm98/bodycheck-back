package bodycheck_back.bodycheck.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bodycheck_back.bodycheck.auth.services.AuthService;
import bodycheck_back.bodycheck.exceptions.AppointmentConflictException;
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

   public List<AppointmentDTO> findAllByCustomer(Long customerId) {
      List<Appointment> appointments = appointmentRepository.findAllByUserAndCustomerId(authService.getUserFromToken(),
            customerId);

      return appointments.stream().map(this::convertToDto).collect(Collectors.toList());
   }

   @Transactional
   public AppointmentDTO create(Appointment appointment) {
      appointment.setUser(authService.getUserFromToken());
      // IMPORTANTE! sumo 1s al startTime para que no encuentre conflicto cuando una cita empieza justo cuando otra acaba.
      appointment.setStartTime(appointment.getStartTime().plusSeconds(1));

      // Comprobamos que no exista ninguna cita solapada.
      List<Appointment> conflictingAppointments = findConflictingAppointments(appointment);

      if (!conflictingAppointments.isEmpty()) {
         throw new AppointmentConflictException();
      }
      return convertToDto(appointmentRepository.save(appointment));
   }

   public AppointmentDTO convertToDto(Appointment appointment) {
      // Accedemos al Customer relaccionado con el apointment si existe. Si no, lo mantenemos como null.
      Customer customer = appointment.getCustomer() != null
            ? customerService.findById(appointment.getCustomer().getId()).orElseThrow()
            : null;

      return AppointmentDTO.builder()
            .id(appointment.getId())
            .customer(customer != null ? customerService.convertToDto(customer) : null)
            .customerName(appointment.getCustomerName())
            .customerPhone(appointment.getCustomerPhone())
            .date(appointment.getDate())
            .startTime(appointment.getStartTime())
            .endTime(appointment.getEndTime())
            .reason(appointment.getReason())
            .observations(appointment.getObservations())
            .build();
   }

   private List<Appointment> findConflictingAppointments(Appointment appointment) {
      return appointmentRepository.findConflictingAppointments(
            appointment.getUser().getId(),
            appointment.getDate(),
            appointment.getStartTime(),
            appointment.getEndTime());
   }
}
