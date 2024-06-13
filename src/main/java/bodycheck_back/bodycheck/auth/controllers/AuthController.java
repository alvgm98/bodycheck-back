package bodycheck_back.bodycheck.auth.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bodycheck_back.bodycheck.auth.models.AuthResponse;
import bodycheck_back.bodycheck.auth.models.LoginRequest;
import bodycheck_back.bodycheck.auth.models.RegisterRequest;
import bodycheck_back.bodycheck.auth.services.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AuthController {

   private final AuthService authService;

   @PostMapping("/login")
   public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
      return ResponseEntity.ok(authService.login(request));
   }

   @PostMapping("/register")
   public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
      return ResponseEntity.ok(authService.register(request));
   }

}
