package bodycheck_back.bodycheck.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bodycheck_back.bodycheck.auth.services.AuthService;
import bodycheck_back.bodycheck.models.dtos.UserDTO;
import bodycheck_back.bodycheck.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class UserController {

   private final UserService userService;
   private final AuthService authService;

   @GetMapping()
   public ResponseEntity<UserDTO> getUser() {
      return ResponseEntity.status(HttpStatus.OK).body(userService.convertToDto(authService.getUserFromToken()));
   }

   @PutMapping()
   public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
      return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(authService.getUserFromToken(), userDTO));
   }

}
