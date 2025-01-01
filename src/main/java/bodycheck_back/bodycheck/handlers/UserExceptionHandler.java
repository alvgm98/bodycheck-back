package bodycheck_back.bodycheck.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import bodycheck_back.bodycheck.exceptions.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class UserExceptionHandler {

   /**
    * Maneja las excepciones de tipo BadCredentialsException
    * lanzadas cuando las credenciales proporcionadas
    * (como el correo electrónico y la contraseña) son incorrectas.
    * <p>
    * Este método recibe una excepción de tipo BadCredentialsException,
    * y devuelve una respuesta con el código de estado HTTP 401 (Unauthorized)
    * con un cuerpo que contiene el mensaje: "Email o Contraseña incorrectos."
    * </p>
    * 
    * @param e la excepción BadCredentialsException capturada
    * @return una ResponseEntity con estado HTTP 401 (Unauthorized)
    *         y el mensaje de error: "Email o Contraseña incorrectos."
    */
   @ExceptionHandler(BadCredentialsException.class)
   public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
      log.error("Bad credentials: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Email o Contraseña incorrectos."));
   }

   /**
    * Maneja las excepciones de tipo UsernameNotFoundException 
    * lanzadas cuando el nombre de usuario no se encuentra en el sistema.
    * <p>
    * Este método recibe una excepción de tipo BadCredentialsException,
    * y devuelve una respuesta con el código de estado HTTP 401 (Unauthorized)
    * con un cuerpo que contiene el mensaje: "No se encuentra al usuario."
    * </p>
    * 
    * @param e la excepción UsernameNotFoundException capturada
    * @return una ResponseEntity con estado HTTP 401 (Unauthorized)
    *         y el mensaje de error: "No se encuentra al usuario."
    */
   @ExceptionHandler(UsernameNotFoundException.class)
   public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
      log.error("User not found: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("No se encuentra al usuario."));
   }
}
