package bodycheck_back.bodycheck.auth.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bodycheck_back.bodycheck.auth.models.AuthResponse;
import bodycheck_back.bodycheck.auth.models.LoginRequest;
import bodycheck_back.bodycheck.auth.models.RegisterRequest;
import bodycheck_back.bodycheck.models.entities.User;
import bodycheck_back.bodycheck.models.enums.Role;
import bodycheck_back.bodycheck.repositories.UserRepository;
import bodycheck_back.bodycheck.services.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

   private final UserRepository userRepository;
   private final JwtService jwtService;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authenticationManager;
   private final UserService userService;

   public AuthResponse login(LoginRequest request) {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
      UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
      String token = jwtService.getToken(user);
      return AuthResponse.builder()
            .token(token)
            .user(userService.convertToDto((User) user))
            .build();
   }

   public AuthResponse register(RegisterRequest request) {
      User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .phone(request.getPhone())
            .role(Role.USER)
            .build();

      userRepository.save(user);

      return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .user(userService.convertToDto(user))
            .build();
   }

   // Obtiene el Usuario que ha realizado la petición
   public User getUserFromToken() {
      return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   }

}
