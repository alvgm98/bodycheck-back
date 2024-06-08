package bodycheck_back.bodycheck.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bodycheck_back.bodycheck.models.entities.Customer;
import bodycheck_back.bodycheck.models.entities.User;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
   List<Customer> findAllByUser(User user);
}
