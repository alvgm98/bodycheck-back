package bodycheck_back.bodycheck.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import bodycheck_back.bodycheck.exceptions.AppointmentConflictException;
import bodycheck_back.bodycheck.exceptions.AppointmentCustomerExpectedException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

   /**
    * Maneja las excepciones de ConstraintViolationException lanzadas cuando fallan
    * las validaciones a nivel de modelo.
    * 
    * @param e la excepción de ConstraintViolationException capturada que contiene
    *          detalles sobre las violaciones de las restricciones
    * @return una ResponseEntity que contiene un mapa con los nombres de los campos
    *         y sus respectivos mensajes de error,
    *         y un estado HTTP BAD REQUEST
    */
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(ConstraintViolationException.class)
   public ResponseEntity<Map<String, String>> handleConstraintViolationExceptions(ConstraintViolationException e) {
      Map<String, String> errors = new HashMap<>();
      e.getConstraintViolations().forEach((violation) -> {
         String fieldName = violation.getPropertyPath().toString();
         String errorMessage = violation.getMessage();
         errors.put(fieldName, errorMessage);
      });
      log.error("Constraint violations: {}", errors);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
   }

   /**
    * Maneja las excepciones de IllegalArgumentException.
    * 
    * @param e la excepción IllegalArgumentException capturada
    * @return una ResponseEntity con el mensaje de error y el estado HTTP BAD
    *         REQUEST
    */
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<String> handlerArgumentException(IllegalArgumentException e) {
      log.error("Illegal argument exception: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
   }

   // Manejador para excepciones de autenticación - BadCredentialsException
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   @ExceptionHandler(BadCredentialsException.class)
   public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
      log.error("Bad credentials: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
   }

   // Manejador para excepciones de autenticación - UsernameNotFoundException
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   @ExceptionHandler(UsernameNotFoundException.class)
   public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
      log.error("User not found: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
   }

   /**
    * Maneja las excepciones de DataIntegrityViolationException lanzadas cuando
    * fallan las restricciones de integridad de la base de datos.
    * 
    * <h5>
    * Por ahora esta excepción unicamente saltará al violar la restriccion
    * de campo unico de username en la tabla users
    * </h5>
    *
    * @param e la excepción de DataIntegrityViolationException capturada
    * @return una ResponseEntity vacía con el estado HTTP CONFLICT
    */
   @ResponseStatus(HttpStatus.CONFLICT)
   @ExceptionHandler(DataIntegrityViolationException.class)
   public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
      log.error("Data integrity violation: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
   }

   /********** CUSTOM APPOINTMENT ERRORS **********/
   /**
    * Maneja las excepciones personalizadas AppointmentConflictException lanzadas cuando
    * se intenta dar de alta una cita que solapa con alguna otra cita.
    *
    * @param e la excepción de AppointmentConflictException capturada
    * @return una ResponseEntity con estado HTTP CONFLICT y el mensaje de error: "Ya hay una cita programada en este horario."
    */
   @ResponseStatus(HttpStatus.CONFLICT)
   @ExceptionHandler(AppointmentConflictException.class)
   public ResponseEntity<String> handleAppointmentConflictException(AppointmentConflictException e) {
      log.error("Appointment conflict: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
   }

   /**
    * Maneja las excepciones personalizadas AppointmentCustomerExpectedException lanzadas cuando
    * se intenta dar de alta una cita en la que no esta vinculado ningun Customer o ningun número de teléfono y nombre.
    *
    * @param e la excepción de AppointmentConflictException capturada
    * @return una ResponseEntity con estado HTTP CONFLICT y el mensaje de error: "Es necesario vincular la cita a un cliente o a un nombre y teléfono."
    */
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(AppointmentCustomerExpectedException.class)
   public ResponseEntity<String> handleAppointmentCustomerExpectedException(AppointmentCustomerExpectedException e) {
      log.error("Appointment customer expected: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
   }

   /**
    * Maneja las excepciones de RuntimeException.
    * 
    * @param e la excepción RuntimeException capturada
    * @return una ResponseEntity vacía con el estado HTTP BAD GATEWAY
    */
   @ResponseStatus(HttpStatus.BAD_GATEWAY)
   @ExceptionHandler(RuntimeException.class)
   public ResponseEntity<String> handlerRuntimeException(RuntimeException e) {
      log.error("Runtime exception: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
   }

   /**
    * Maneja cualquier otra excepción no manejada específicamente.
    * 
    * @param e la excepción genérica capturada
    * @return una ResponseEntity vacía con el estado HTTP INTERNAL SERVER ERROR
    */
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler(Exception.class)
   public ResponseEntity<String> handleGeneralException(Exception e) {
      log.error("General exception: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
   }

}
