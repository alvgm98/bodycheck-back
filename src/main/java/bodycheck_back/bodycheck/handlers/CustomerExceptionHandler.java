package bodycheck_back.bodycheck.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import bodycheck_back.bodycheck.exceptions.ErrorResponse;
import bodycheck_back.bodycheck.exceptions.customer.CustomerNotFoundException;
import bodycheck_back.bodycheck.exceptions.customer.UnauthorizedCustomerAccessException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CustomerExceptionHandler {

   /**
    * Maneja las excepciones de tipo CustomerNotFoundException lanzadas cuando
    * no se encuentra un Cliente en la base de datos.
    * <p>
    * Este método crea una respuesta con un estado HTTP 404 (Not Found) y un cuerpo
    * que contiene un mensaje de error detallado encapsulado en un objeto
    * ErrorResponse.
    * </p>
    * 
    * @param e la excepción CustomerNotFoundException capturada
    * @return una ResponseEntity con estado HTTP 404 y el mensaje del error en el cuerpo
    */
   @ExceptionHandler(CustomerNotFoundException.class)
   public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
   }

   /**
    * Maneja las excepciones de tipo UnauthorizedCustomerAccessException lanzadas
    * cuando un Usuario intenta acceder a un Cliente que no le pertenece.
    * <p>
    * Este método crea una respuesta con un estado HTTP 403 (Forbidden) y un cuerpo
    * que contiene un mensaje de error detallado encapsulado en un objeto
    * ErrorResponse.
    * </p>
    * 
    * @param e la excepción UnauthorizedCustomerAccessException capturada
    * @return una ResponseEntity con estado HTTP 403 y el mensaje del error en el cuerpo
    */
   @ExceptionHandler(UnauthorizedCustomerAccessException.class)
   public ResponseEntity<ErrorResponse> handleUnauthorizedAccess(UnauthorizedCustomerAccessException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
   }
}
