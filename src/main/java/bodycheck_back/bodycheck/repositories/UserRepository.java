package bodycheck_back.bodycheck.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bodycheck_back.bodycheck.models.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByUsername(String username);
}
