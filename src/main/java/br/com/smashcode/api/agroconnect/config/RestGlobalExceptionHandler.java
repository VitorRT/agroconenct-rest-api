package br.com.smashcode.api.agroconnect.config;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.smashcode.api.agroconnect.exception.RestValidationException;
import br.com.smashcode.api.agroconnect.exception.dto.ValidationException;
import br.com.smashcode.api.agroconnect.exception.rest.RestException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestGlobalExceptionHandler {

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
}
