package br.com.smashcode.api.agroconnect.config;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import br.com.smashcode.api.agroconnect.exception.RestValidationException;
import br.com.smashcode.api.agroconnect.exception.dto.BadRequestException;
import br.com.smashcode.api.agroconnect.exception.dto.ValidationException;
import br.com.smashcode.api.agroconnect.exception.rest.RestException;
import br.com.smashcode.api.agroconnect.exception.rest.RestSwearWordException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

     @ExceptionHandler(DataIntegrityViolationException.class)
     public ResponseEntity<RestException> DataIntegrityViolationExceptionHandler() {
        return ResponseEntity.badRequest().body(
          new RestException(HttpStatus.BAD_REQUEST.value(),"Erro de violação de integridade", "o email informado já esta cadastrado.")
        );
     }

     @ExceptionHandler(ConstraintViolationException.class)
     public ResponseEntity<RestValidationException> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
          List<ValidationException> errorsList = e.getConstraintViolations().stream().map(
               (err) -> new ValidationException(err.getPropertyPath().toString(), err.getMessage())
          ).toList();

          return ResponseEntity.badRequest().body(
               new RestValidationException(HttpStatus.BAD_REQUEST.value(), "Erro de validação.", errorsList)
          );
     }    

     @ExceptionHandler(BadRequestException.class)
     public ResponseEntity<RestException> BadRequestExceptionHandler(BadRequestException e) {
          return ResponseEntity.badRequest().body(new RestException(HttpStatus.BAD_REQUEST.value(), "not found.",e.getMessage()));
     }

     @ExceptionHandler(JWTDecodeException.class)
     public ResponseEntity<RestException> JWTDecodeExceptionHandler(JWTDecodeException e) {
          return ResponseEntity.badRequest().body(new RestException(HttpStatus.BAD_REQUEST.value(), "jwt token",e.getMessage()));
     }

     @ExceptionHandler(JWTVerificationException.class)
     public ResponseEntity<RestException> JWTVerificationExceptionHandler(JWTVerificationException e) {
          return ResponseEntity.badRequest().body(new RestException(HttpStatus.BAD_REQUEST.value(), "jwt token",e.getMessage()));
     }

     @ExceptionHandler(TokenExpiredException.class)
     public ResponseEntity<RestException> TokenExpiredExceptionHandler(TokenExpiredException e) {
          return ResponseEntity.badRequest().body(new RestException(HttpStatus.BAD_REQUEST.value(), "jwt token",e.getMessage()));
     }

     @ExceptionHandler(RestSwearWordException.class) 
     public ResponseEntity<RestException> RestSwearWordExceptionHandler(RestSwearWordException e) {
          return ResponseEntity.badRequest().body(new RestException(HttpStatus.BAD_REQUEST.value(), "palavras inapropriadas.",e.getMessage()));
     }

}
