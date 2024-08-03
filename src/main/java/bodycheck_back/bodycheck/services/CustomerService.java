package bodycheck_back.bodycheck.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bodycheck_back.bodycheck.auth.services.AuthService;
import bodycheck_back.bodycheck.models.dtos.AppointmentDTO;
import bodycheck_back.bodycheck.models.dtos.CustomerDTO;
import bodycheck_back.bodycheck.models.dtos.CustomerDetailedDTO;
import bodycheck_back.bodycheck.models.dtos.MeasurementDTO;
import bodycheck_back.bodycheck.models.entities.Customer;
import bodycheck_back.bodycheck.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

   private final CustomerRepository customerRepository;
   private final AuthService authService;

   public List<CustomerDTO> findAllByUser() {
      List<Customer> customers = customerRepository.findAllByUser(authService.getUserFromToken());

      return customers.stream().map(this::convertToDto).collect(Collectors.toList());
   }

   public Optional<Customer> findById(Long id) {
      return customerRepository.findById(id);
   }

   @Transactional
   public CustomerDTO create(Customer customer) {
      customer.setUser(authService.getUserFromToken());
      return convertToDto(customerRepository.save(customer));
   }

   @Transactional
   public CustomerDTO update(Long id, Customer customer) {
      customer.setId(id);
      customer.setUser(authService.getUserFromToken());
      return convertToDto(customerRepository.save(customer));
   }

   @Transactional
   public void delete(Long id) {
      customerRepository.deleteById(id);
   }

   /**
    * Importante devolver la entidad Customer como DTO.
    * Ya que al devolver la entidad salta error al tener el atributo User en LAZY.
    */
   public CustomerDTO convertToDto(Customer customer) {
      return CustomerDTO.builder()
            .id(customer.getId())
            .firstName(customer.getFirstName())
            .lastName(customer.getLastName())
            .email(customer.getEmail())
            .phone(customer.getPhone())
            .birthdate(customer.getBirthdate())
            .height(customer.getHeight())
            .gender(customer.getGender().toString())
            .ethnicity(customer.getEthnicity().toString())
            .observations(customer.getObservations())
            .build();
   }

   public CustomerDetailedDTO getCustomerDetailed(CustomerDTO customerDTO, List<MeasurementDTO> measurements, List<AppointmentDTO> lastAppointment) {      
      return CustomerDetailedDTO.builder()
            .id(customerDTO.getId())
            .firstName(customerDTO.getFirstName())
            .lastName(customerDTO.getLastName())
            .email(customerDTO.getEmail())
            .phone(customerDTO.getPhone())
            .birthdate(customerDTO.getBirthdate())
            .height(customerDTO.getHeight())
            .gender(customerDTO.getGender().toString())
            .ethnicity(customerDTO.getEthnicity().toString())
            .observations(customerDTO.getObservations())
            .measurements(measurements)
            .appointments(lastAppointment)
            .build();
   }

}
