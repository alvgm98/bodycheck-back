package bodycheck_back.bodycheck.services;

import org.springframework.stereotype.Service;

import bodycheck_back.bodycheck.models.dtos.UserDTO;
import bodycheck_back.bodycheck.models.entities.User;
import bodycheck_back.bodycheck.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

   private final UserRepository userRepository;

   public UserDTO updateUser(User user, UserDTO userDTO) {
      return convertToDto(userRepository.save(
            User.builder()
                  .id(user.getId())
                  .password(user.getPassword())
                  .role(user.getRole())
                  .username(userDTO.getUsername())
                  .firstName(userDTO.getFirstName())
                  .lastName(userDTO.getLastName())
                  .phone(userDTO.getPhone())
                  .build()));
   }

   public UserDTO convertToDto(User user) {
      return UserDTO.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .username(user.getUsername())
            .phone(user.getPhone())
            .build();
   }
}
