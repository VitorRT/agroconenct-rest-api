package br.com.smashcode.api.agroconnect.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.smashcode.api.agroconnect.exception.dto.ValidationException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public record RestValidationException(Integer status, String error, List<ValidationException> list_errors) {
    
}
