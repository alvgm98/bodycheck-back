package bodycheck_back.bodycheck.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import bodycheck_back.bodycheck.exceptions.ErrorResponse;
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
   @ExceptionHandler(DataIntegrityViolationException.class)
   public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
      log.error("Data integrity violation: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
   }

   /**
    * Maneja las excepciones de IllegalArgumentException, que se lanzan cuando se
    * pasa un argumento no válido a un método o función.
    * 
    * @param e la excepción IllegalArgumentException capturada
    * @return una ResponseEntity con el mensaje de error y el estado HTTP BAD
    *         REQUEST
    */
   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<ErrorResponse> handlerArgumentException(IllegalArgumentException e) {
      log.error("Illegal argument exception: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
   }

   /**
    * Maneja las excepciones de RuntimeException.
    * 
    * @param e la excepción RuntimeException capturada
    * @return una ResponseEntity vacía con el estado HTTP BAD GATEWAY
    */
   @ExceptionHandler(RuntimeException.class)
   public ResponseEntity<ErrorResponse> handlerRuntimeException(RuntimeException e) {
      log.error("Runtime exception: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorResponse(e.getMessage()));
   }

   /**
    * Maneja cualquier otra excepción no manejada específicamente.
    * 
    * @param e la excepción genérica capturada
    * @return una ResponseEntity vacía con el estado HTTP INTERNAL SERVER ERROR
    */
   @ExceptionHandler(Exception.class)
   public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
      log.error("General exception: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
   }

}
