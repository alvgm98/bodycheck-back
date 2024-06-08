package bodycheck_back.bodycheck.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bodycheck_back.bodycheck.models.dtos.CustomerDTO;
import bodycheck_back.bodycheck.models.entities.Customer;
import bodycheck_back.bodycheck.models.entities.User;
import bodycheck_back.bodycheck.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

   private final CustomerRepository customerRepository;

   public List<CustomerDTO> findAllByUser(User user) {
      List<Customer> customers = customerRepository.findAllByUser(user);

      return customers.stream().map(this::convertToDto).collect(Collectors.toList());
   }

   public Optional<Customer> findById(Long id) {
      return customerRepository.findById(id);
   }

   public CustomerDTO save(Customer customer) {
      return convertToDto(customerRepository.save(customer));
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
            .gender(customer.getGender().toString())
            .height(customer.getHeight())
            .observations(customer.getObservations())
            .build();
   }

   public void delete(Long id) {
      customerRepository.deleteById(id);
   }
}
