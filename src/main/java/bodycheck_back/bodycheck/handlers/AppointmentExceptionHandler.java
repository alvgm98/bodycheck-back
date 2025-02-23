package bodycheck_back.bodycheck.handlers;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import bodycheck_back.bodycheck.exceptions.ErrorResponse;
import bodycheck_back.bodycheck.exceptions.appointment.AppointmentConflictException;
import bodycheck_back.bodycheck.exceptions.appointment.AppointmentCustomerExpectedException;
import bodycheck_back.bodycheck.exceptions.appointment.AppointmentNotFoundException;
import bodycheck_back.bodycheck.exceptions.appointment.UnauthorizedAppointmentAccessException;
import lombok.extern.slf4j.Slf4j;

@Order(1)
@ControllerAdvice
@Slf4j
public class AppointmentExceptionHandler {

   /**
    * Maneja las excepciones lanzadas cuando hay un conflicto de citas
    * (AppointmentConflictException),
    * es decir, cuando se intenta crear una cita que solapa con una ya programada.
    * <p>
    * Este método recibe una excepción de tipo AppointmentConflictException,
    * y devuelve una respuesta con el código de estado HTTP 409 (Conflict)
    * con un cuerpo que contiene el mensaje de error.
    * </p>
    * 
    * @param e la excepción AppointmentConflictException capturada
    * @return una ResponseEntity con estado HTTP 409 y el mensaje del error en el
    *         cuerpo
    */
   @ExceptionHandler(AppointmentConflictException.class)
   public ResponseEntity<ErrorResponse> handleAppointmentConflictException(AppointmentConflictException e) {
      log.error("Appointment conflict: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
   }

   /**
    * Maneja las excepciones personalizadas AppointmentCustomerExpectedException
    * lanzadas cuando se intenta dar de alta una cita en la que
    * no esta vinculado ningun Customer o ningun número de teléfono y nombre.
    *
    * @param e la excepción de AppointmentConflictException capturada
    * @return una ResponseEntity con estado HTTP 400 y el mensaje del error en el
    *         cuerpo
    */
   @ExceptionHandler(AppointmentCustomerExpectedException.class)
   public ResponseEntity<ErrorResponse> handleAppointmentCustomerExpectedException(
         AppointmentCustomerExpectedException e) {
      log.error("Appointment customer expected: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
   }

   /**
    * Maneja las excepciones de tipo UnauthorizedAppointmentAccessException lanzadas
    * cuando un Usuario intenta acceder a un Cliente que no le pertenece.
    * <p>
    * Este método crea una respuesta con un estado HTTP 403 (Forbidden) y un cuerpo
    * que contiene un mensaje de error detallado encapsulado en un objeto
    * ErrorResponse.
    * </p>
    * 
    * @param e la excepción UnauthorizedAppointmentAccessException capturada
    * @return una ResponseEntity con estado HTTP 403 y el mensaje del error en el
    *         cuerpo
    */
   @ExceptionHandler(UnauthorizedAppointmentAccessException.class)
   public ResponseEntity<ErrorResponse> handleUnauthorizedAccess(UnauthorizedAppointmentAccessException e) {
      log.error("Appointment Access Forbidden: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
   }

   /**
    * Maneja las excepciones personalizadas AppointmentNotFoundException
    * lanzadas cuando se intenta modificar una cita que no exista
    *
    * @param e la excepción de AppointmentNotFoundException capturada
    * @return una ResponseEntity con estado HTTP 404 y el mensaje del error en el
    *         cuerpo
    */
   @ExceptionHandler(AppointmentNotFoundException.class)
   public ResponseEntity<ErrorResponse> handleAppointmentNotFoundException(
         AppointmentNotFoundException e) {
      log.error("Appointment Not Found: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
   }
}
